package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.negocio.Pais;

public class hiloBalanceador extends Thread {
	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;

	private long cargaDeMaquina = 0;
	private List<Integer> paises;
	private float porcetajeCarga = 0;

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
		/*
		 * Re abrir la conexion try { is = new BufferedReader(new
		 * InputStreamReader(s.getInputStream())); os = new
		 * PrintWriter(s.getOutputStream());
		 * 
		 * } catch (IOException e) { System.out.println("IO error in server thread"); }
		 */
		long retorno = 0;
		try {
			if (is != null) {
				os.println("informar");
				os.flush();
				System.out.println("Enviando peticion de informe");
				line = is.readLine();
				retorno = Long.parseLong(line);
				this.setCargaDeMaquina(retorno);
				System.out.println(
						"Cantidad de poblacion:  " + line + " en la instancia: " + s.getInetAddress().toString());
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("CLIENTE DESCONECTADO ABRUPTAMENTE - SE A ABIERTO CAMPO PARA UNO NUEVO");
			return false;
		}
		this.cargaDeMaquina = retorno;
		return true;
	}

	public Agente ordenarSustraccionAgente() {
		Agente retorno = new Agente();
		int idPaisSustraer = -1;
		try 
		{
			if(is != null)
			{
				os.println("sustraer agente");
				os.flush();
				System.out.println("Enviando peticion de susteaccion de agente");
				line = is.readLine();
				idPaisSustraer = Integer.parseInt(line);
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

	public float getPorcetajeCarga() {
		return porcetajeCarga;
	}

	public void setPorcetajeCarga(float porcetajeCarga) {
		this.porcetajeCarga = porcetajeCarga;
	}

	public String toString() {
		String retorno = "";
		return retorno + "Cantidad de poblacion: " + this.getCargaDeMaquina() + " porcentaje de carga: "
				+ this.getPorcetajeCarga() + " en el PC: " + s.getInetAddress().toString();
	}

}
