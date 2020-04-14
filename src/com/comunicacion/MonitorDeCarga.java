package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.negocio.Agente;
import com.negocio.AutomataCelular;
import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class MonitorDeCarga
{
	int operacionesActuales = 0;

	private List<Agente> agentes = new Vector<Agente>();
	private List<Pais> todosLosPaises = new Vector<Pais>();
	private List<Pais> paises = new Vector<Pais>();
	private List<ConexionPaises> conexiones = new Vector<ConexionPaises>();
	private ModeloVirus covid19 = new ModeloVirus();
	private List<AutomataCelular> misHilos = new ArrayList<AutomataCelular>();
	private int j = 0;

	public void iniciarMonitor(String direccion) throws IOException
	{
		
		/* Elementos de conectividad */
		InetAddress direccionBalanceador = InetAddress.getByName(direccion);
		InetAddress direccionAgente = InetAddress.getLocalHost();
		Socket s1 = null;
		String line = null;
		BufferedReader br = null;
		BufferedReader is = null;
		PrintWriter os = null;

		/* Elementos del informe */
		long totalpoblacion = 0;

		// Establece conexion
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

		String response = "";
		try
		{
			while (true)
			{
				response = is.readLine();
				if (response.equals("distribucion"))
				{
					int numPaises = 0;
					List<Integer> idPaises = new ArrayList<Integer>();
					/* Leer el numero de paises correspondiente */
					numPaises = Integer.parseInt(is.readLine());
					/* Leer la lista de paises que corresponden */
					for (int i = 0; i < numPaises; i++)
					{
						idPaises.add(Integer.parseInt(is.readLine()));
					}
					for (int i = 0; i < idPaises.size(); i++)
					{
						asignarPais(idPaises.get(i));
					}
					System.out.println("Mis paises por id son: " + idPaises.toString());
					for (int i = 0; i < agentes.size(); i++)
					{
						System.out.println("Mis paises son: " + agentes.get(i).getMiPais().getNombre());
					}
					llamadaHilos();

				}
				if (response.equals("informar"))
				{
					System.out.println("Llego la hora de informar");
					for (int i = 0; i < agentes.size(); i++)
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
						System.out.print("Tasa Transmision  : ");
						System.out.println(agentes.get(j).getCovid19().getTasaTransmision());
						System.out.print("Tasa Mortalidad  Vulnerable: ");
						System.out.println(agentes.get(j).getCovid19().getTasaMortalidadVul());
						System.out.print("Tasa Mortalidad No Vulnerable: ");
						System.out.println(agentes.get(j).getCovid19().getTasaMortalidadNoVul());
						System.out.println("-------------------------------------");
					}
					System.out.println("-------------------------------------");
					if (totalpoblacion != 0)
						os.println(totalpoblacion);
					else
						os.println("No hay paises activos en esta maquina");// Enviarle informacion al servidor
					os.flush();
					totalpoblacion = 0;
					response = "";
				}
				if (response.equals("sustraer agente"))
				{
					Pais tempP = new Pais();
					Agente aActual = new Agente();
					Collections.sort(agentes, new comparadorAgentes());// Ordenar a los agentes por la poblacion total															// de su pais
					aActual = agentes.get(0);
					tempP = aActual.getMiPais();// Extraer el pais con la menor poblacion
					System.out.println("Pais a sustraer: " + tempP);
					os.println(tempP.getId());// Enviar el id del pais
					os.flush();
					os.println(aActual.getConexiones().size());//Enviar numero de coneciones del pais
					os.flush();
					for (int i = 0; i < aActual.getConexiones().size(); i++)//Envio de los paises con los que tiene conexion el agente 
					{
						os.println(aActual.getConexiones().get(i));
						os.flush();
						os.println(" ");
						os.flush();
					}
					this.agentes.remove(0);// Remover el agente el pais enviado !!!BOOMMM
				}
				if (response.equals("agregar agente"))
				{
					Agente nuevoAgente = new Agente();
					int idNuevoPais = -1, indicePaisAct = 0, cantVecinos = 0;
					String nombreTempVecino = "";
					int paisNuevoAutomata = 0;
					char tipoConexion;
					List<Integer> idVecinos = new ArrayList<Integer>();
					List<String> paisesVecinos = new ArrayList<String>();
					idNuevoPais = Integer.parseInt(is.readLine());
					System.out.println("Llego el Agente con id: " + idNuevoPais);
					cantVecinos = Integer.parseInt(is.readLine());
					paisNuevoAutomata = Pais.buscarPais(todosLosPaises, idNuevoPais);//Buscar pais del cual me dieron el id
					for (int i = 0; i < cantVecinos; i++)//Armando las conexiones del nuevo agente.
					{
						Pais nuevoPaisVecino = new Pais();
						ConexionPaises nuevaConexion = new ConexionPaises();
						nombreTempVecino = is.readLine();//Obtener el id del pais vecino (paisB)
						tipoConexion = is.readLine().charAt(0);//Obtener el tipo de conexion
						nuevoPaisVecino = Pais.buscarPaisPorNombre(todosLosPaises, nombreTempVecino);
						paisesVecinos.add(nuevoPaisVecino.getNombre());
					}
					//Terminar de armar el agente
					nuevoAgente.setConexiones(paisesVecinos);
					nuevoAgente.setCovid19(covid19);
					nuevoAgente.setMiPais(todosLosPaises.get(paisNuevoAutomata));
					//Agregar el agente a la lista del Monitor
					this.agentes.add(nuevoAgente);
					AutomataCelular nuevoAutomataC = new AutomataCelular(this.agentes.get(this.agentes.size()-1));
					this.misHilos.add(nuevoAutomataC);
					nuevoAutomataC.start();
				}
				if (response.equals("apadrinar"))
				{
					int numHuerfanos = 0, idhuerfanos = 0, inxNuevoPais;
					numHuerfanos = Integer.parseInt(is.readLine());
					List<ConexionPaises> conexionNuevoP = new ArrayList<ConexionPaises>();
					Pais pAux = new Pais();
					boolean agregarAgente = true;
					System.out.println("Numero de huerfanos para recibir: " + numHuerfanos);
					for (int i = 0; i < numHuerfanos; i++)
					{
						Agente nuevoAgente = new Agente();
						idhuerfanos = Integer.parseInt(is.readLine());
						for (int j = 0; j < this.agentes.size(); j++)
						{
							if(this.agentes.get(j).getMiPais().getId() == idhuerfanos)
							{
								System.out.println("huerfano repetido :(asdsdhf =" + idhuerfanos);
								agregarAgente = false;
								break;
							}
						}

						if(agregarAgente)
							this.asignarPais(idhuerfanos);
						agregarAgente = true;
					}
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
	public void llamadaHilos()
	{
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

	public void asignarPais(int id)
	{
		String nombre = "";
		Agente myAgente = new Agente();
		List<String> myConexion = new Vector<String>();
		
        for(int i = 0; i< paises.size();i++ ) {
        	if(id == paises.get(i).getId()) {
        		nombre = paises.get(i).getNombre();
        		myAgente.setMiPais(paises.get(i));
        	}
        }
        
        for(int i = 0; i< conexiones.size();i++ ) { //Recorre conexiones mundiales
        	if(conexiones.get(i).getPaisA().equals(nombre) ) {
        		if(myConexion.size() == 0) {
        			myConexion.add(conexiones.get(i).getPaisB());
        		}else {
        			boolean esta = false;
        			for(int j = 0; j<myConexion.size();j++) {
            			if(conexiones.get(i).getPaisB().equals(myConexion.get(j)) ) {
            				esta = true;
            			}
            			
            		}
        			if(esta == false) {
        				myConexion.add(conexiones.get(i).getPaisB());
        			}
        		}
        		
        	}
        	if( conexiones.get(i).getPaisB().equals(nombre)) {
        		if(myConexion.size() == 0) {
        			myConexion.add(conexiones.get(i).getPaisA());
        		}else {
        			boolean esta = false;
        			for(int j = 0; j<myConexion.size();j++) {
            			if(conexiones.get(i).getPaisA().equals(myConexion.get(j))) {
            				esta = true;
            			}
            			
            		}
        			if(esta == false) {
        				myConexion.add(conexiones.get(i).getPaisA());
        			}
        		}
        	}
        }
        
        
        myAgente.setConexiones(myConexion);

        ModeloVirus copiaModeloVirus = new ModeloVirus(covid19.getTasaTransmision(),covid19.getTasaMortalidadVul(),covid19.getTasaMortalidadNoVul());
        myAgente.setCovid19(copiaModeloVirus);
        
        agentes.add(myAgente);
	}	
	public List<Pais> getTodosLosPaises()
	{
		return todosLosPaises;
	}

	public void setTodosLosPaises(List<Pais> todosLosPaises)
	{
		this.todosLosPaises = todosLosPaises;
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

	public synchronized List<Agente> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<Agente> agentes)
	{
		this.agentes = agentes;
	}

	public List<ConexionPaises> getConexiones()
	{
		return conexiones;
	}

	public void setConexiones(List<ConexionPaises> conexiones)
	{
		this.conexiones = conexiones;
	}

	public ModeloVirus getCovid19()
	{
		return covid19;
	}

	public void setCovid19(ModeloVirus covid19)
	{
		this.covid19 = covid19;
	}

}
