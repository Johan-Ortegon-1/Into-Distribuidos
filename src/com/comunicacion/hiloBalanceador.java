package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.negocio.Pais;

public class hiloBalanceador extends Thread
{
	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;
	List<Pais> paises;
	int numMaquinas;
	public hiloBalanceador(Socket s, List<Pais> paisesA, int pNumMaquinas)
	{
		this.s=s;
		this.paises = paisesA;
		this.numMaquinas = pNumMaquinas;
	}

	public void run()
	{
		try
		{
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e)
		{
			System.out.println("IO error in server thread");
		}

		try
		{
			/*Asignar a cada maquina los paises correspondientes*/
			
			/*Cada 5 segudos pedir el informe a los monitores*/
			while (true)
			{
				Thread.sleep(5000);
				os.println("informar");
				os.flush();
				System.out.println("Enviando peticion de informe");
				line = is.readLine();
				System.out.println("Cantidad de operaciones:  " + line + " en la instancia: " + s.getInetAddress().toString()); 
			}
		} catch (IOException e)
		{
			line = this.getName(); // reused String line for getting thread name
			System.out.println("IO Error/ Client " + line + " terminated abruptly");
		} catch (NullPointerException e)
		{
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client " + line + " Closed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally
		{
			try
			{
				System.out.println("Connection Closing..");
				if (is != null)
				{
					is.close();
					System.out.println(" Socket Input Stream Closed");
				}

				if (os != null)
				{
					os.close();
					System.out.println("Socket Out Closed");
				}
				if (s != null)
				{
					s.close();
					System.out.println("Socket Closed");
				}

			} catch (IOException ie)
			{
				System.out.println("Socket Close Error");
			}
		} // end finally
	}
}
