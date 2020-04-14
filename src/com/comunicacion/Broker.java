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
import com.negocio.Agente;
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
		try {
			Thread.sleep(15000);
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		while(true) {
			try {
				
				
				List<Pais> conexionesPais;
				
				for(int i = 0; i < monitor.getAgentes().size(); i++) {
					
					if(monitor.getAgentes().get(i).getMiPais().isInfectado() == false) {
						List<String> conexiones = monitor.getAgentes().get(i).getConexiones();
						conexionesPais = new Vector<Pais>();
						
						for(int j = 0; j < conexiones.size(); j++ ) { // Conexiones por pais
							
							for(int k = 0; k < copiaMundo.size(); k++) { // Copiar info del mundo
								if(copiaMundo.get(k).getNombre().equals(conexiones.get(j))) {
									
									conexionesPais.add(copiaMundo.get(k));
								}
							}
							monitor.getAgentes().get(i).setConexionesPais(conexionesPais);
						}
					}
					
				}
				
				Thread.sleep(10000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void actualizarMundo (String direccion)throws IOException{ 

		/* Elementos de conectividad */
		InetAddress direccionBalanceador = InetAddress.getByName(direccion);
		InetAddress direccionAgente = InetAddress.getLocalHost();
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;
		
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
					os.println(monitor.getAgentes().size());
					os.flush();
					for (int i = 0; i <  monitor.getAgentes().size(); i++)
					{
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
					
					for(int i = 0;i<retorno ; i++) {
						
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
					copiaMundo = copia;
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
