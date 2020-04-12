package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class hiloBalanceador extends Thread {
	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;

	private long cargaDeMaquina = 0;
	private List<Integer> paises;
	private double porcetajeCarga = 0;
	private boolean hiloActivo = true;
	
	public hiloBalanceador(Socket s, List<Integer> pPaises) {
		this.s = s;
		this.paises = pPaises;
	}

	public void run() {
		try {
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e) {
			System.out.println("IO error in server thread");
		}

		try {
			/* Asignar a cada maquina los paises correspondientes */
			os.println("distribucion");// Avisar que voy a asignar paises
			os.flush();
			os.println(paises.size());// Enviar numero de paises que debe esperar la maquina
			os.flush();
			for (int i = 0; i < paises.size(); i++)// Envio de todos los paises
			{
				os.println(paises.get(i));
				os.flush();
			}

			/* Cada 5 segudos pedir el informe a los monitores */
			/*
			 * while (true) { Thread.sleep(5000); os.println("informar"); os.flush();
			 * System.out.println("Enviando peticion de informe"); line = is.readLine();
			 * System.out.println("Cantidad de operaciones:  " + line + " en la instancia: "
			 * + s.getInetAddress().toString()); }
			 */
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client " + line + " Closed");
		}

	}

	public void matarConexiones() {
		try {
			System.out.println("Connection Closing..");
			if (is != null) {
				is.close();
				System.out.println(" Socket Input Stream Closed");
			}

			if (os != null) {
				os.close();
				System.out.println("Socket Out Closed");
			}
			if (s != null) {
				s.close();
				System.out.println("Socket Closed");
			}

		} catch (IOException ie) {
			System.out.println("Socket Close Error");
		}
	}

	public boolean darInformePeriodico() {
		long retorno = -1;
		try 
		{
			if (is != null) {
				os.println("informar");
				os.flush();
				System.out.println("Enviando peticion de informe");
				line = is.readLine();
				if(line != null)
				{
					if(!line.equals("No hay paises activos en esta maquina"))
					{
						retorno = Long.parseLong(line);
						this.setCargaDeMaquina(retorno);
						if(retorno == -1)
						{
							this.hiloActivo = false;
							return false;
						}
						//System.out.println("Cantidad de poblacion:  " + line + " en la instancia: " + s.getInetAddress().toString());
					}
					else
					{
						System.out.println("Ya no hay paises en la instancia: " + s.getInetAddress().toString());
						this.hiloActivo = false;
						return false;
					}
				}
				else
				{
					this.hiloActivo = false;
					return false;
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("CLIENTE DESCONECTADO DE FORMA ABRUPTA");
			System.out.println("**INICIANDO RE DISTRIBUCION DE SUS AGENTES");
			this.hiloActivo = false;
			return false;
		}
		this.cargaDeMaquina = retorno;
		return true;
	}

	public Agente ordenarSustraccionAgente(List<Pais> todosLosPaises, ModeloVirus covid19) {
		Agente retorno = new Agente();
		int idPaisSustraer = -1;
		int idNuevoPais = -1, indicePaisAct = 0, cantVecinos = 0;
		String idTempVecino = "";
		int paisNuevoAutomata = 0;
		char tipoConexion;
		List<Integer> idVecinos = new ArrayList<Integer>();
		List<ConexionPaises> paisesVecinos = new ArrayList<ConexionPaises>();
		try 
		{
			if(is != null)
			{
				System.out.println("Enviando peticion de sustraccion de agente");
				os.println("sustraer agente");
				os.flush();
				idNuevoPais = Integer.parseInt(is.readLine());
				System.out.println("Trasladando agente con id: " + idNuevoPais);
				cantVecinos = Integer.parseInt(is.readLine());
				System.out.println("Conexiones del agente: " + cantVecinos);
				paisNuevoAutomata = Pais.buscarPais(todosLosPaises, idNuevoPais);//Buscar pais del cual me dieron el id
				System.out.println("****PAIS ENCONTRADO******");
				todosLosPaises.get(paisNuevoAutomata).toString();
				for (int i = 0; i < cantVecinos; i++)//Armando las conexiones del nuevo agente.
				{
					Pais nuevoPaisVecino = new Pais();
					ConexionPaises nuevaConexion = new ConexionPaises();
					idTempVecino = is.readLine();//Obtener el nombre del pais vecino (paisB)
					//System.out.println("Recibiendo id temp pos: " + idTempVecino);
					tipoConexion = is.readLine().charAt(0);//Obtener el tipo de conexion
					nuevoPaisVecino = Pais.buscarPaisPorNombre(todosLosPaises, idTempVecino);
					nuevaConexion.setPaisA(todosLosPaises.get(paisNuevoAutomata).getNombre());
					nuevaConexion.setPaisB(nuevoPaisVecino.getNombre());
					nuevaConexion.setMedioTransporte(tipoConexion);
					paisesVecinos.add(nuevaConexion);
				}
				//Terminar de armar el agente
				retorno.setConexiones(paisesVecinos);
				retorno.setCovid19(covid19);
				retorno.setMiPais(todosLosPaises.get(paisNuevoAutomata));
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("CLIENTE DESCONECTADO ABRUPTAMENTE");
		}
		return retorno;
	}

	public void ordenarAdicionAgente(Agente aActual) 
	{
		Pais tempP = aActual.getMiPais();
		if(is != null)
		{
			os.println("agregar agente");
			os.flush();
			os.println(tempP.getId());//Enviar id del pais
			os.flush();
			os.println(aActual.getConexiones().size());//Enviar numero de coneciones del pais
			os.flush();
			for (int i = 0; i < aActual.getConexiones().size(); i++)//Envio de los paises con los que tiene conexion el agente 
			{
				os.println(aActual.getConexiones().get(i).getPaisB());
				os.println(aActual.getConexiones().get(i).getMedioTransporte());
			}
			System.out.println("Enviando peticion para adicionar un agente");
			//line = is.readLine();
		}
	}

	/* GETTERS AND SETTERS */
	public long getCargaDeMaquina() {
		return cargaDeMaquina;
	}

	public void setCargaDeMaquina(long cargaDeMaquina) {
		this.cargaDeMaquina = cargaDeMaquina;
	}

	public List<Integer> getPaises() {
		return paises;
	}

	public void setPaises(List<Integer> paises) {
		this.paises = paises;
	}

	public double getPorcetajeCarga() {
		return porcetajeCarga;
	}

	public void setPorcetajeCarga(double porcetajeCarga) {
		this.porcetajeCarga = porcetajeCarga;
	}

	public boolean isHiloActivo() {
		return hiloActivo;
	}

	public void setHiloActivo(boolean hiloActivo) {
		this.hiloActivo = hiloActivo;
	}

	public String toString() {
		String retorno = "";
		return retorno + "Cantidad de poblacion: " + this.getCargaDeMaquina() + " en el PC: " + s.getInetAddress().toString();
	}

}
