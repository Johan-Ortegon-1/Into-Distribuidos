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
		Random random = new Random();//Para la distribucion de los paises
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
		int numPaises = pPaises.size();
		int randomInt = 0;
		List<Integer> paisesXMaqu = new ArrayList<Integer>();
		int paisesRandom[] = new int[numPaises];
		for (int i = 0; i < numMaquinas; i++)
		{
			if(i == numMaquinas)
			{
				paisesXMaqu.add(numPaises);
				break;
			}
			randomInt = random.nextInt(numPaises);
			paisesXMaqu.add(randomInt);
			numPaises = numPaises - randomInt;	
		}
		System.out.println("Random 1: " + paisesXMaqu.toString());
		paisesRandom[0] = (int)Math.random()*numPaises; 
		for(int i = 1; i < numPaises; i++)
		{
			paisesRandom[i] = (int)Math.random()*numPaises;
			for (int j = 0; j < 1; j++)
			{
				if(paisesRandom[i] == paisesRandom[j])
					i--;
			}
		}
		System.out.println("Random 2: " + paisesRandom.toString());
		
		while (numMaqActual < numMaquinas)
		{
			try
			{
				s = ss2.accept();
				System.out.println("connection Established");
				hiloBalanceador st = new hiloBalanceador(s, pPaises, numMaquinas);
				st.start();
				numMaqActual++;
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

	public void testSerVidor() throws IOException
	{
		/*
		 * this.serverAuto = new Thread(new Runnable() { public void run() { try {
		 * 
		 * ServerSocket ss = new ServerSocket(4999);
		 * System.out.println("Bandera Servidor");
		 * 
		 * Socket sc = ss.accept();
		 * System.out.println("Un cliente se ha conectado :)!"); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } } });
		 * this.serverAuto.start();
		 */
		/* Creacion del socket del servidor */
		ServerSocket ss = new ServerSocket(4999);
		/* Aceptando solicitud del cliente */
		Socket sc = ss.accept();
		System.out.println("Un cliente se ha conectado !");
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
