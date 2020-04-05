package com.comunicacion;
import java.net.*;
import java.io.*;
public class Servidor
{
	public static void testSerVidor()throws IOException
	{
		/*Creacion del socket del servidor*/
		ServerSocket ss = new ServerSocket(4999);
		/*Aceptando dolicitud del cliente*/
		Socket sc = ss.accept(); 
		System.out.println("Un cliente se ah conectado :)!");
	}
}
