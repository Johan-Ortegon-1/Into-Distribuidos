package com.controladores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.persistencia.*;
import com.comunicacion.*;
import com.negocio.Pais;
public class Main
{
	static Servidor miServidor = new Servidor();
	static Cliente miCliente = new Cliente();
	private static MonitorDeCarga monitor = new MonitorDeCarga();
	private static Agente agenteTemp = new Agente();
	
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt", agenteTemp);
		
		
        for(int i = 0; i< agenteTemp.getMisPaises().size();i++ ) {
        	System.out.println(agenteTemp.getMisPaises().get(i).getNombre());
        }
//        for(int i = 0; i< agenteTemp.getConexiones().size();i++ ) {
//        	System.out.println(agenteTemp.getConexiones().get(i).getMedioTransporte());
//        }
		
		
		System.out.println("Como desea iniciar esta maquina: " + 
				" 1. Agente -- 2. Balanceador");
		tipoDeInicio = reader.nextInt();
		
		/*Creacion de un monitor de prueba*/
		Pais paisPrueba = new Pais(456789,1231,456);
		List<Pais> listaPrueba=  new ArrayList<Pais>();
		listaPrueba.add(paisPrueba);
		monitor.setPaises(listaPrueba);
		
		if(tipoDeInicio == 1)//Inicio como Agente
		{
			try
			{
				monitor.iniciarMonitor();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(tipoDeInicio == 2)//Inicio como balanceador
		{
			System.out.println("Cuantas maquinas tiene la topologia inicial?:");
			int numMaquinas = 0;  
			numMaquinas = reader.nextInt();
			BalanceadoreCarga.inciarBalanceador(agenteTemp.getMisPaises(), numMaquinas);
		}
		
	}
}
