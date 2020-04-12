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

public class MonitorDeCarga
{
	int operacionesActuales = 0;

	private List<Agente> agentes = new Vector<Agente>();
	private List<Pais> paises = new Vector<Pais>();
	private List<ConexionPaises> conexiones = new Vector<ConexionPaises>();
	private ModeloVirus covid19 = new ModeloVirus();
	private List<AutomataCelular> misHilos = new ArrayList<AutomataCelular>();
	
	private Thread autoARP;
	private int j = 0;

	public void iniciarMonitor() throws IOException
	{
		/* Elementos de conectividad */
		InetAddress direccionBalanceador = InetAddress.getByName("192.168.0.15");
		InetAddress direccionAgente = InetAddress.getLocalHost();
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;

		/* Elementos del informe */
		long totalpoblacion = 0;
		
		//Establece conexion
		try
		{
			s1 = new Socket(direccionBalanceador, 4445);
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.print("IO Exception");
		}

		System.out.println("DIRECCION DEL Monitor: " + direccionAgente);
		/* poner a trabajar a los paises en hilos diferentes */

		

		/*for (j = 0; j < paises.size(); j++)
		{
			this.autoARP = new Thread(new Runnable()
			{
				public void run()
				{
					paises.get(0).experimentacion();
				}
			});
			this.autoARP.start();
		}*/

		String response = "";
		try
		{
			while (true)
			{
				response = is.readLine();
				if(response.equals("distribucion"))
				{
					int numPaises = 0;
					List<Integer> idPaises = new ArrayList<Integer>();
					/*Leer el numero de paises correspondiente*/
					numPaises = Integer.parseInt(is.readLine());
					/*Leer la lista de paises que corresponden*/
					for(int i = 0; i < numPaises; i++)
					{
						idPaises.add(Integer.parseInt(is.readLine()));
					}
					for (int i = 0; i < idPaises.size(); i++)
					{
						asignarPais(idPaises.get(i));
					}
					System.out.println("Mis paises por id son: " + idPaises.toString());
					for(int i = 0; i < agentes.size();i++) {
						System.out.println("Mis paises son: " + agentes.get(i).getMiPais().getNombre());
					}
					llamadaHilos();
					
					
				}
				if (response.equals("informar"))
				{
					System.out.println("Llego la hora de informar");
					for (int i = 0; i <  agentes.size(); i++)
					{
						totalpoblacion = totalpoblacion + agentes.get(i).getMiPais().getPoblacionTotal();
					}
					System.out.println("Poblacion total: " + totalpoblacion);
					System.out.println("***********************************************");
					for(int j = 0; j<agentes.size();j++) {
						System.out.print("PAIS: ");
						System.out.println(agentes.get(j).getMiPais().getNombre());
						System.out.print("Infectados: ");
						System.out.println(agentes.get(j).getMiPais().getInfectados());
						System.out.print("Poblacion : ");
						System.out.println(agentes.get(j).getMiPais().getPoblacionTotal());
						System.out.println("-------------------------------------");
					}
					System.out.println("-------------------------------------");
					if (totalpoblacion != 0)
						os.println(totalpoblacion);
					else
						os.println("No hay paises activos en esta maquina");// Enviarle informacion al servidor
					os.flush();
					// System.out.println("Server Response : " + response);
					totalpoblacion = 0;
					// line = br.readLine();
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
	
	public void llamadaHilos() {
			for(int j = 0; j< agentes.size();j++ ) {
//				System.out.println(agentes.get(i).getMiPais().getNombre());
//				System.out.println(agentes.get(i).getMiPais().isInfectado());
//				System.out.println(agentes.get(i).getMiPais().getInfectados());
//				System.out.println(agentes.get(i).getMiPais().getPoblacionTotal());
	        }
			//Hilos por cada uno de los agentes
			for(int k = 0; k < agentes.size(); k++) {
				System.out.println("TAMAÑO DE AGENTES "+ agentes.size());
				AutomataCelular st = new AutomataCelular(agentes.get(k));
				misHilos.add(st);
				st.start();
				//st = null;
			}
			
			System.out.println("Salio del hilo-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
	}
	
	public void asignarPais(int id) {
		String nombre = "";
		Agente myAgente = new Agente();
		List<String> myConexion = new Vector<String>();
		
        for(int i = 0; i< paises.size();i++ ) {
        	if(id == paises.get(i).getId()) {
        		nombre = paises.get(i).getNombre();
        		myAgente.setMiPais(paises.get(i));
        	}
        }
        
        for(int i = 0; i< conexiones.size();i++ ) {
        	if(conexiones.get(i).getPaisA().equals(nombre) ) {
        		myConexion.add(conexiones.get(i).getPaisA());
        	}
        	if( conexiones.get(i).getPaisB().equals(nombre)) {
        		myConexion.add(conexiones.get(i).getPaisB());
        	}
        }
        
        if(myConexion.size()>0) {
        	myAgente.setConexiones(myConexion);
        }
        myAgente.setCovid19(covid19);
        
        agentes.add(myAgente);
	}

	public int getOperacionesActuales()
	{
		return operacionesActuales;
	}

	public void setOperacionesActuales(int operacionesActuales)
	{
		this.operacionesActuales = operacionesActuales;
	}

	public List<Pais> getPaises()
	{
		return paises;
	}

	public void setPaises(List<Pais> paises)
	{
		this.paises = paises;
	}

	public List<Agente> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<Agente> agentes) {
		this.agentes = agentes;
	}

	public List<ConexionPaises> getConexiones() {
		return conexiones;
	}

	public void setConexiones(List<ConexionPaises> conexiones) {
		this.conexiones = conexiones;
	}

	public ModeloVirus getCovid19() {
		return covid19;
	}

	public void setCovid19(ModeloVirus covid19) {
		this.covid19 = covid19;
	}
	
	


}
