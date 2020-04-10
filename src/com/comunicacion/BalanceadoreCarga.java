package com.comunicacion;
import java.util.Random;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.negocio.Pais;

public class BalanceadoreCarga
{
	/*
	 * https://stackoverflow.com/questions/10131377/socket-programming-multiple-
	 * client-to-one-server
	 */
	private Thread balanceadorAuto;
	private Thread serverAuto;
	private final static int puertoServidor = 4445;
	public static void inciarBalanceador(List<Pais> pPaises, int numMaquinas)
	{
		int numMaqActual = 0;
		Socket s = null;
		ServerSocket ss2 = null;
		System.out.println("Server Listening......");
		try
		{
			//Apertura del servidor
			ss2 = new ServerSocket(puertoServidor); 

		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Server error");
		}
		
		/*Repartir paises:*/
		Random random = new Random();//Para la distribucion de los paises
		int numPaises = pPaises.size();
		int aux = numPaises;
		int randomInt = 0;
		List<Integer> paisesXMaqu = new ArrayList<Integer>();
		int paisesRandom[] = new int[numPaises];
		for (int i = 0; i < numMaquinas; i++)
		{
			if(i == numMaquinas-1)
			{
				paisesXMaqu.add(aux);
				break;
			}
			randomInt = random.nextInt(numPaises);
			paisesXMaqu.add(randomInt);
			aux = aux - randomInt;	
		}
		paisesRandom[0] = (int)(Math.random()*(5)); 
		for(int i = 1; i < 5; i++)
		{
			paisesRandom[i] = (int)(Math.random()*(5));
			for (int j = 0; j < i; j++)
			{
				if(paisesRandom[i] == paisesRandom[j])
				{
					i--;
					System.out.println("MM: " + paisesRandom[i]);
				}
			}
		}
//		for (int i = 0; i < paisesRandom.length; i++) 
//		{
//			System.out.println(paisesRandom[i] + " ");
//		}
		int indexIni = 0, indexFin = 0;
		while (numMaqActual < numMaquinas)
		{
			try
			{
				int cantPaisesMAct = paisesXMaqu.get(numMaqActual);
				indexFin = indexIni + cantPaisesMAct;
				List<Integer> paisesCorrespondientes = new ArrayList<Integer>();
				for(int i = indexIni;i < indexFin;i++)
				{
					paisesCorrespondientes.add(paisesRandom[i]);
				}
				System.out.println("Paises que el corresponden a la instancia: " + numMaqActual + " " + paisesCorrespondientes.toString());
				s = ss2.accept();
				System.out.println("Conexion establecida");
				hiloBalanceador st = new hiloBalanceador(s, numMaquinas, paisesCorrespondientes);
				st.start();
				numMaqActual++;
				indexIni = indexFin;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Connection Error");
			}
		}
		
		/*while (true)
		{
			try
			{
				s = ss2.accept();
				System.out.println("connection Established");
				hiloBalanceador st = new hiloBalanceador(s, pPaises, numMaquinas);
				st.start();
				contMaquinas++;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Connection Error");
			}
		}*/

	}

	public void analizarCarga()
	{
		/* Solicitar el reporte de los monitores de forma permanente */

		this.balanceadorAuto = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while (true)
					{
						/* Cada 5 segundos iniciar el proceso de pedir informes a los Monitores */
						Thread.sleep(5000);

					}
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		this.balanceadorAuto.start();
	}
}
