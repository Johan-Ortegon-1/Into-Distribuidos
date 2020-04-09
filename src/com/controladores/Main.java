package com.controladores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.persistencia.*;
import com.comunicacion.*;
import com.negocio.Pais;
public class Main
{
	static Servidor miServidor = new Servidor();
	static Cliente miCliente = new Cliente();
	private static MonitorDeCarga monitor = new MonitorDeCarga();
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt");
		System.out.println("Como desea iniciar esta maquina: " + 
				" 1. Agente -- 2. Balanceador");
		tipoDeInicio = reader.nextInt();
		
		/*Creaciond e un monitor de prueba*/
		Pais paisPrueba = new Pais(456789,1231,456);
		List<Pais> listaPrueba=  new ArrayList<Pais>();
		listaPrueba.add(paisPrueba);
		monitor.setPaises(listaPrueba);
		
		if(tipoDeInicio == 1)
		{
			try
			{
				//miCliente.testCliente();
				monitor.iniciarMonitor();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(tipoDeInicio == 2)
		{
			//miServidor.testSerVidor();
			BalanceadoreCarga.inciarBalanceador();
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
