package com.comunicacion;
import java.net.*;

import java.io.*;
public class Servidor
{
	private Thread serverAuto;
	public void testSerVidor()throws IOException
	{
		/*this.serverAuto = new Thread(new Runnable() {
			public void run() {
				try {
					
					ServerSocket ss = new ServerSocket(4999);
					System.out.println("Bandera Servidor");
					
					Socket sc = ss.accept(); 
					System.out.println("Un cliente se ha conectado :)!");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		this.serverAuto.start();*/ 
		/*Creacion del socket del servidor*/
		ServerSocket ss = new ServerSocket(4999);
		/*Aceptando solicitud del cliente*/
		Socket sc = ss.accept(); 
		System.out.println("Un cliente se ha conectado !");
	}
}
