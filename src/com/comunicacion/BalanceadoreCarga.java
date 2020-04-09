package com.comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BalanceadoreCarga
{
	/*https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server*/
	private Thread balanceadorAuto;
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
	public void analizarCarga()
	{
		/* Solicitar el reporte de los monitores de forma permanente */

		this.balanceadorAuto = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while(true)
					{
						/*Cada 5 segundos iniciar el proceso de pedir informes a los Monitores*/
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
