package com.comunicacion;
import java.util.Random;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private int numMaquinasSistema = 0;
	private List<hiloBalanceador> misHilos = new ArrayList<hiloBalanceador>();
	
	public void inciarBalanceador(List<Pais> pPaises, int numMaquinas)
	{
		int numMaqActual = 0;
		Socket s = null;
		ServerSocket ss2 = null;
		System.out.println("Server Listening......");
		numMaquinasSistema = numMaquinas;
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
		while (numMaqActual < numMaquinas)/*Esperar tantas conexiones como haya especificado el usuario*/
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
				//System.out.println("Paises que el corresponden a la instancia: " + numMaqActual + " " + paisesCorrespondientes.toString());
				s = ss2.accept();
				System.out.println("Conexion establecida");
				hiloBalanceador st = new hiloBalanceador(s, paisesCorrespondientes);
				misHilos.add(st);
				st.start();
				numMaqActual++;
				indexIni = indexFin;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Error en la conexion");
			}
		}
		/*Empezar a analizar carga de ahora en adelante*/
		this.analizarCarga();
		
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
		long sumTotal = 0;
		float promedio = 0;
		/* Solicitar el reporte de los monitores de forma permanente */
		while (true)
		{
			try
			{
				Thread.sleep(5000);
				for (int i = 0; i < misHilos.size(); i++)//Actualiza los valores de carga en cada hilo
				{
					if(misHilos.get(i).datInformePeridodico() == false)
					{
						System.out.println("Cliente desconectado - Matando su hilo");
						this.arreglarDesconexion();
						misHilos.get(i).matarConexiones();
						misHilos.remove(i);
						if(misHilos.size() == 0)
						{
							System.out.println("Ya no hay maquinas en el sistema");
							break;
						}
					}
				}
				for (int i = 0; i < misHilos.size(); i++)
				{
					sumTotal =+ misHilos.get(i).getCargaDeMaquina();
				}
				promedio = sumTotal/numMaquinasSistema;
				System.out.println("Promedio de las poblaciones manejadas por cada maquina: " + promedio);
				Collections.sort(misHilos, new comparadorCargasHilos());
				System.out.println("Hilos y sus cargas depues de ordenamiento");
				for (int i = 0; i < misHilos.size(); i++)
				{
					System.out.println(misHilos.get(i).toString());
				}
				sumTotal = 0;
				
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	public void arreglarDesconexion()
	{
		
	}
}
