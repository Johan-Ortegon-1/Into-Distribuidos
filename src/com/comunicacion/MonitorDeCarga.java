package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.negocio.Pais;

public class MonitorDeCarga
{
	int operacionesActuales = 0;
	private List<Pais> paises = new ArrayList<Pais>();
	private Thread autoARP;
	private int j = 0;

	public void iniciarMonitor() throws IOException
	{
		/* Elementos de conectividad */
		InetAddress direccionBalanceador = InetAddress.getByName("192.168.1.63");
		InetAddress direccionAgente = InetAddress.getLocalHost();
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;

		/* Elementos del informe */
		int totalOperaciones = 0;

		try
		{
			s1 = new Socket(direccionBalanceador, 4445);
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.print("IO Exception");
		}

		System.out.println("DIRECCION DEL Monitor: " + direccionAgente);
		/* poner a trabajar a los paises en hilos diferentes */

		for (int i = 0; i < paises.size(); i++)
		{
			hiloPais nuevoPais = new hiloPais(paises.get(i));
			nuevoPais.start();
			System.out.println("Salio del hilo-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
		}

		/*for (j = 0; j < paises.size(); j++)
		{
			this.autoARP = new Thread(new Runnable()
			{
				public void run()
				{
					paises.get(0).experimentacion();
				}
			});
			this.autoARP.start();
		}*/

		String response = null;
		try
		{
			while (true)
			{
				response = is.readLine();
				if(response.equals("distribucion"))
				{
					
				}
				if (response.equals("informar"))
				{
					System.out.println("Llego la hora de informar");
					for (int i = 0; i < paises.size(); i++)
					{
						System.out.println("Poblacion del pais: " + paises.get(i).getPoblacionTotal());
						totalOperaciones = totalOperaciones + paises.get(i).getContOperacionesRealizadas();
					}
					if (totalOperaciones != 0)
						os.println(totalOperaciones);
					else
						os.println("No hay paises activos");// Enviarle informacion al servidor
					os.flush();
					// System.out.println("Server Response : " + response);
					totalOperaciones = 0;
					// line = br.readLine();
					response = "";
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Socket read Error");
		} finally
		{

			is.close();
			os.close();
			br.close();
			s1.close();
			System.out.println("Connection Closed");

		}
	}

	public int getOperacionesActuales()
	{
		return operacionesActuales;
	}

	public void setOperacionesActuales(int operacionesActuales)
	{
		this.operacionesActuales = operacionesActuales;
	}

	public List<Pais> getPaises()
	{
		return paises;
	}

	public void setPaises(List<Pais> paises)
	{
		this.paises = paises;
	}

}
