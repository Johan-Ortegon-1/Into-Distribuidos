package com.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import com.negocio.Pais;

public class hiloBroker extends Thread{
	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;
	
	private List<Pais> paisesLocales =  new Vector<Pais>();
	
	public hiloBroker(Socket s) {
		super();
		this.s = s;
	}

	public void run() {
		try {
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e) {
			System.out.println("IO error in server thread");
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
	
	public boolean recibirPaises(List<Pais> copiaMundo) {
		long retorno = 0;
		boolean respuesta = true;
		try 
		{
			if(is != null)
			{
				os.println("actualizarMundo");
				os.flush();
				System.out.println("Enviando peticion de actualizacion");
				line = is.readLine();
				
				retorno = Long.parseLong(line);
				//paisesLocales = null;
				paisesLocales = null;
				paisesLocales = new Vector<Pais>();
				for(int i = 0;i<retorno ; i++) {
					Pais nuevo = new Pais();
					
					line = is.readLine();
					nuevo.setPoblacionTotal(Long.parseLong(line));
					System.out.println(nuevo.getPoblacionTotal());
					line = is.readLine();
					nuevo.setNombre(line);;
					System.out.println(nuevo.getNombre());
					line = is.readLine();
					nuevo.setInfectados(Long.parseLong(line));
					System.out.println(nuevo.getInfectados());
					
					paisesLocales.add(nuevo);
					nuevo = null;
				}			
				os.println("recibirMundo");
				os.flush();
				os.println(copiaMundo.size());
				os.flush();
				for (int i = 0; i <  copiaMundo.size(); i++)
				{
					os.println(copiaMundo.get(i).getPoblacionTotal());
					os.flush();
					os.println(copiaMundo.get(i).getNombre());
					os.flush();
					os.println(copiaMundo.get(i).getInfectados());
					os.flush();
				}
				
			}
		} catch (IOException e) {
			System.out.println("CLIENTE DESCONECTADO ABRUPTAMENTE (BROKER) - SE A ABIERTO CAMPO PARA UNO NUEVO");
			respuesta = false;
		}
		return respuesta;
	}
	
	public String toString() {
		String retorno = "";
		return retorno + "Cantidad de poblacion PRIMER PAIS  (BROKER): " + paisesLocales.get(0).getPoblacionTotal() + " en el socket: "
				+ s.getInetAddress().toString();
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public BufferedReader getIs() {
		return is;
	}

	public void setIs(BufferedReader is) {
		this.is = is;
	}

	public PrintWriter getOs() {
		return os;
	}

	public void setOs(PrintWriter os) {
		this.os = os;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public List<Pais> getPaisesLocales() {
		return paisesLocales;
	}

	public void setPaisesLocales(List<Pais> paisesLocales) {
		this.paisesLocales = paisesLocales;
	}

	
	
	
}
