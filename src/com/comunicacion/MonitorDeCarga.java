package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MonitorDeCarga
{
	int operacionesActuales = 0;

	public static void iniciarMonitor() throws IOException
	{
		InetAddress direccionBalanceador = InetAddress.getByName("192.168.1.63");
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;

		try
		{
			s1 = new Socket(direccionBalanceador, 4445); // You can use static final constant PORT_NUM
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.print("IO Exception");
		}

		System.out.println("Client Address : " + direccionBalanceador);
		System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

		String response = null;
		try
		{
			line = br.readLine();
			while (line.compareTo("QUIT") != 0)
			{
				os.println(line);
				os.flush();
				response = is.readLine();
				System.out.println("Server Response : " + response);
				line = br.readLine();

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
}
