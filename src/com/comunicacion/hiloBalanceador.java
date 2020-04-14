package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import com.negocio.Agente;
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
				System.out.println("**Balanceador: Enviando peticion de informe");
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
		int idNuevoPais = -1, cantVecinos = 0;
		String idTempVecino = "";
		int paisNuevoAutomata = 0;
		char tipoConexion;
		List<Integer> idVecinos = new ArrayList<Integer>();
		List<String> paisesVecinos = new ArrayList<String>();
		try 
		{
			if(is != null)
			{
				System.out.println("**Balanceador: Enviando peticion de sustraccion de agente");
				os.println("sustraer agente");
				os.flush();
				idNuevoPais = Integer.parseInt(is.readLine());
				for (int i = 0; i < paises.size(); i++)
				{
					if(paises.get(i) == idNuevoPais)
					{
						paises.remove(i);
						break;
					}
				}
				System.out.println("	Trasladando agente con id: " + idNuevoPais);
				cantVecinos = Integer.parseInt(is.readLine());
				System.out.println("	Conexiones del agente: " + cantVecinos);
				paisNuevoAutomata = Pais.buscarPais(todosLosPaises, idNuevoPais);//Buscar pais del cual me dieron el id
				todosLosPaises.get(paisNuevoAutomata).toString();
				for (int i = 0; i < cantVecinos; i++)//Armando las conexiones del nuevo agente.
				{
					Pais nuevoPaisVecino = new Pais();
					ConexionPaises nuevaConexion = new ConexionPaises();
					idTempVecino = is.readLine();//Obtener el nombre del pais vecino (paisB)
					//System.out.println("Recibiendo id temp pos: " + idTempVecino);
					tipoConexion = is.readLine().charAt(0);//Obtener el tipo de conexion
					nuevoPaisVecino = Pais.buscarPaisPorNombre(todosLosPaises, idTempVecino);
					/*nuevaConexion.setPaisA(todosLosPaises.get(paisNuevoAutomata).getNombre());
					nuevaConexion.setPaisB(nuevoPaisVecino.getNombre());
					nuevaConexion.setMedioTransporte(tipoConexion);*/
					paisesVecinos.add(nuevoPaisVecino.getNombre());
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
			this.paises.add(aActual.getMiPais().getId());
			os.println("agregar agente");
			os.flush();
			os.println(tempP.getId());//Enviar id del pais
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
			System.out.println("	Enviando peticion para adicionar un agente");
			//line = is.readLine();
		}
	}

	public void ordenarApadrinamiento(List<Integer> idPaisesHuerfanos)
	{
		if(is != null && this.hiloActivo)
		{
			os.println("apadrinar");
			os.flush();
			os.println(idPaisesHuerfanos.size());//Envio numero de huerfanos a recibir
			os.flush();
			for (int i = 0; i < idPaisesHuerfanos.size(); i++)//Envio de los id de paises huerfanos
			{
				os.println(idPaisesHuerfanos.get(i));
				os.flush();
			}
			System.out.println("Enviando peticion para Apadrinar");
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
