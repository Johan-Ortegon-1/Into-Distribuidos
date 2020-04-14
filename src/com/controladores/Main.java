package com.controladores;
import java.io.IOException;
import java.util.Scanner;
import com.persistencia.*;
import com.comunicacion.*;

public class Main
{
	private static MonitorDeCarga monitor = new MonitorDeCarga();
	private static BalanceadoreCarga bc = new BalanceadoreCarga(); 
	private static Broker bre = new Broker(); 
	private static String direccion ;

	
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt", monitor, direccion);
		System.out.println("Como desea iniciar esta maquina: " + 
				" 1. Agente -- 2. Balanceador");
		tipoDeInicio = reader.nextInt();
		
		monitor.setTodosLosPaises(monitor.getPaises()); 
		
		if(tipoDeInicio == 1)//Inicio como Agente
		{
			
			Thread monitorHilo;
			
			monitorHilo = new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						monitor.iniciarMonitor(direccion);
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			});
			monitorHilo.start();
			
			Thread brokerHilo;
			
			brokerHilo = new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						bre.setMonitor(monitor);
						bre.actualizarMundo(direccion);
						
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			});
			brokerHilo.start();
			
			Thread brokerAgentesHilo;
			
			brokerAgentesHilo = new Thread(new Runnable()
			{
				public void run()
				{
						bre.actualizarAgentes();
				}
			});
			brokerAgentesHilo.start();
			
			
		}
		else if(tipoDeInicio == 2)//Inicio como balanceador
		{
			Thread balanceadorMonitor;
			
			balanceadorMonitor = new Thread(new Runnable()
			{
				public void run()
				{
					System.out.println("Cuantas maquinas tiene la topologia inicial?(Sin contar con el balanceador):");
					int numMaquinas = 0;  
					numMaquinas = reader.nextInt();

					bc.inciarBalanceador(monitor.getPaises(), numMaquinas, monitor.getCovid19());
					
				}
			});
			balanceadorMonitor.start();
			
			Thread balanceadorBroker;
			
			balanceadorBroker = new Thread(new Runnable()
			{
				public void run()
				{
					bc.iniciarBroker();
				}
			});
			balanceadorBroker.start();
		}
		
	}
}
