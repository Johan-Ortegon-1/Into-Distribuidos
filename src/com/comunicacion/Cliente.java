package com.comunicacion;
import java.net.*;
import java.io.*;

public class Cliente
{
	private Thread serverAuto;
	public void testCliente()throws IOException
	{
		/*this.serverAuto = new Thread(new Runnable() {
			public void run() {
				try {
¿					System.out.println("Bandera Cliente");
					Socket s = new Socket("localhost",4999);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		this.serverAuto.start(); */
		/*Creacion del socket del servidor*/
		//System.out.println("Bandera Cliente");
		InetAddress direccionServidor = InetAddress.getByName("192.168.1.63");
		Socket s = new Socket(direccionServidor,4999);
	}
}
