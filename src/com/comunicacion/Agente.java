package com.comunicacion;

import java.util.List;
import java.util.Vector;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class Agente {
	
	private Pais miPais;
	private List<String> conexiones = new Vector<String>();
	private List<Pais> conexionesPais = new Vector<Pais>();
	private ModeloVirus covid19 ;
	
	public Agente(Pais miPais, List<String> conexiones, List<Pais> conexionesPais, ModeloVirus covid19) {
		super();
		this.miPais = miPais;
		this.conexiones = conexiones;
		this.conexionesPais = conexionesPais;
		this.covid19 = covid19;
	}

	public Agente() {
		super();
	}

	public ModeloVirus getCovid19() {
		return covid19;
	}

	public void setCovid19(ModeloVirus covid19) {
		this.covid19 = covid19;
	}
	
	public Pais getMiPais() {
		return miPais;
	}

	public void setMiPais(Pais miPais) {
		this.miPais = miPais;
	}

	public List<String> getConexiones() {
		return conexiones;
	}

	public void setConexiones(List<String> conexiones) {
		this.conexiones = conexiones;
	}

	public synchronized List<Pais> getConexionesPais() {
		return conexionesPais;
	}

	public synchronized void setConexionesPais(List<Pais> conexionesPais) {
		this.conexionesPais = conexionesPais;
	}
	
	
	
	

}
