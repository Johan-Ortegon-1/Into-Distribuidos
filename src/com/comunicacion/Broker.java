package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class Broker {
	private List<Agente> agentes = new Vector<Agente>();
	private List<Pais> copiaMundo = new Vector<Pais>();
	private final static int puertoServidorBroker = 4446;
	private MonitorDeCarga monitor; 
	
	public Broker(List<Agente> agentes) {
		super();
		this.agentes = agentes;
	}

	public Broker() {
		super();
	}



	public void actualizarAgentes (){
		while(true) {
			try {
				Thread.sleep(22000);
				
				List<Pais> conexionesPais;
				
				for(int i = 0; i < monitor.getAgentes().size(); i++) {
					
					if(monitor.getAgentes().get(i).getMiPais().isInfectado() == false) {
						conexionesPais = new Vector<Pais>();
						List<String> conexiones = monitor.getAgentes().get(i).getConexiones();
						
						for(int j = 0; j < conexiones.size(); j++ ) {
							for(int k = 0; k < copiaMundo.size(); k++) {
								if(copiaMundo.get(k).getNombre().equals(conexiones.get(j))) {
									System.out.println("*****************************************POS ENTRA :)");
									conexionesPais.add(copiaMundo.get(k));
								}
							}
							monitor.getAgentes().get(i).setConexionesPais(conexionesPais);
							System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
							System.out.println("PAIS: "+monitor.getAgentes().get(i).getMiPais().getNombre());
							System.out.println(conexionesPais.size());
							for(int m = 0; m<conexionesPais.size();m++) {
								System.out.println(conexionesPais.get(m).getNombre());
							}
							System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
							conexionesPais = null;
						}
					}
				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void actualizarMundo ()throws IOException{ 

		/* Elementos de conectividad */
		InetAddress direccionBalanceador = InetAddress.getByName("192.168.0.10");
		InetAddress direccionAgente = InetAddress.getLocalHost();
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;
//		try
//		{
//			Thread.sleep(10000);
//		} catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
		
		System.out.println("EL HILO BROKER CORRE ");
		//Establece conexion
		try
		{
			s1 = new Socket(direccionBalanceador, puertoServidorBroker);
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.print("IO Exception");
		}

		String response = "";
		try
		{
			while (true)
			{
				long retorno = 0;
				response = is.readLine();
				if (response.equals("actualizarMundo")) //Mundo del balanceador
				{
					System.out.println("Llego la hora de actualizar");
					os.println(monitor.getAgentes().size());
					os.flush();
					System.out.println("*************************************************************** "+ monitor.getAgentes().size() );
					for (int i = 0; i <  monitor.getAgentes().size(); i++)
					{
						System.out.println("*************************************************************** ENTRAAAAAAAA A ACTUALIZAR");
						os.println(monitor.getAgentes().get(i).getMiPais().getPoblacionTotal());
						os.flush();
						os.println(monitor.getAgentes().get(i).getMiPais().getNombre());
						os.flush();
						os.println(monitor.getAgentes().get(i).getMiPais().getInfectados());
						os.flush();
					}
					
					response = "";
				}
				
				response = is.readLine();
				
				if (response.equals("recibirMundo")) { // Recibe copia desde balanceador
					List<Pais> copia = new ArrayList<Pais>();
					line = is.readLine();
					
					retorno = Long.parseLong(line);
					System.out.println("*************************************************************** "+retorno );
					
					for(int i = 0;i<retorno ; i++) {
						
						System.out.println("*************************************************************** ENTRAAAAAAAA A COPIA");
						Pais nuevo = new Pais();
						
						line = is.readLine();
						nuevo.setPoblacionTotal(Long.parseLong(line));
						line = is.readLine();
						nuevo.setNombre(line);;
						line = is.readLine();
						nuevo.setInfectados(Long.parseLong(line));
						
						copia.add(nuevo);
						nuevo = null;
					}
					//copiaMundo = null;
					copiaMundo = copia;
					System.out.println("*******************VALORRRRRR DE CAOPIA MUNDO :"+copiaMundo.size());
					response = "";
				}
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

	
//	public void recibirMundo (){ 
//		
//	}
	
	public List<Agente> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<Agente> agentes) {
		this.agentes = agentes;
	}

	public List<Pais> getCopiaMundo() {
		return copiaMundo;
	}

	public void setCopiaMundo(List<Pais> copiaMundo) {
		this.copiaMundo = copiaMundo;
	}

	public MonitorDeCarga getMonitor() {
		return monitor;
	}

	public void setMonitor(MonitorDeCarga monitor) {
		this.monitor = monitor;
	}
	
	
	
	
	
	
}
