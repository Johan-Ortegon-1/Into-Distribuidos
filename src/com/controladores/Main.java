package com.controladores;
import java.io.IOException;
import java.util.Scanner;
import com.persistencia.*;
import com.comunicacion.*;
public class Main
{
	static Servidor miServidor = new Servidor();
	static Cliente miCliente = new Cliente();
	
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt");
		System.out.println("Como descea iniciar esta maquina: " + 
				" 1. Cliente -- 2. Servidor");
		tipoDeInicio = reader.nextInt();
		if(tipoDeInicio == 1)
		{
			try
			{
				miCliente.testCliente();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(tipoDeInicio == 2)
		{
			try
			{
				miServidor.testSerVidor();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*try
		{
			miServidor.testSerVidor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			System.out.println("Esperando:...");
			Thread.sleep(1000);
		} catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			System.out.println("Cliente activo...");
			miCliente.testCliente();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
