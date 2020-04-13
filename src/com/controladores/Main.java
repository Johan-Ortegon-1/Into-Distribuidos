package com.controladores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.persistencia.*;
import com.comunicacion.*;
import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class Main
{
	static Servidor miServidor = new Servidor();
	static Cliente miCliente = new Cliente();
	private static MonitorDeCarga monitor = new MonitorDeCarga();
	private static BalanceadoreCarga bc = new BalanceadoreCarga(); 
	private static Broker bre = new Broker(); 

	
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt", monitor);
		//PEDIMOS CON QUE PAISES TRABAJAMOS
		
//        for(int i = 0; i< misPaises.size();i++ ) {
//        	System.out.println(misPaises.get(i).getNombre());
//        }
//        for(int i = 0; i< conexiones.size();i++ ) {
//        	System.out.println(conexiones.get(i).getMedioTransporte());
//        }
		
		

//		Thread balanceadorAuto;
//		
//		balanceadorAuto = new Thread(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					while (true)
//					{
//						System.out.print("NOMBRE: ");
//						System.out.println(misHilos.getAgentes().get(0).getMiPais().getNombre());
//						System.out.print("POBLACION: ");
//						System.out.println(misHilos.getAgentes().get(0).getMiPais().getPoblacionTotal());
//						System.out.print("INFECTADOS: ");
//						System.out.println(misHilos.getAgentes().get(0).getMiPais().getInfectados());
//						Thread.sleep(5000);
//
//					}
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//		balanceadorAuto.start();
		
		

//		System.out.println("Como desea iniciar esta maquina: " + 
//				" 1. Agente -- 2. Balanceador");
//		tipoDeInicio = reader.nextInt();
//		
//		/*Creaciond e un monitor de prueba*/
//		Pais paisPrueba = new Pais(456789,1231,456);
//		List<Pais> listaPrueba=  new ArrayList<Pais>();
//		listaPrueba.add(paisPrueba);
//		monitor.setPaises(listaPrueba);
//		
//		if(tipoDeInicio == 1)
//		{
//			try
//			{
//				//miCliente.testCliente();
//				monitor.iniciarMonitor();
//			} catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else if(tipoDeInicio == 2)
//		{
//			//miServidor.testSerVidor();
//			BalanceadoreCarga.inciarBalanceador();
//		}


//        for(int i = 0; i< misHilos.getMisPaises().size();i++ ) {
//        	System.out.println(misHilos.getMisPaises().get(i).getNombre());
//        }
//        for(int i = 0; i< agenteTemp.getConexiones().size();i++ ) {
//        	System.out.println(agenteTemp.getConexiones().get(i).getMedioTransporte());
//        }
		
		
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
						monitor.iniciarMonitor();
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
						bre.actualizarMundo();
						
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
