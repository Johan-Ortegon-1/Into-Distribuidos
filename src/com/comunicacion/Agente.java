package com.comunicacion;

import java.util.List;
import java.util.Vector;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class Agente {
	
	private List<Pais> mundo = new Vector<Pais>();
	private List<Pais> misPaises = new Vector<Pais>();
	private List<ConexionPaises> conexiones;
	private ModeloVirus covid19 ;
	
	public boolean reglasPropagacion(){
		return true;
	}
	
	public void generacion() {
		while(true) {
			
		}
	}

	public Agente(List<Pais> mundo, List<Pais> misPaises, List<ConexionPaises> conexiones, ModeloVirus covid19) {
		super();
		this.mundo = mundo;
		this.misPaises = misPaises;
		this.conexiones = conexiones;
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

	public List<Pais> getMundo() {
		return mundo;
	}

	public void setMundo(List<Pais> mundo) {
		this.mundo = mundo;
	}

	public List<Pais> getMisPaises() {
		return misPaises;
	}

	public void setMisPaises(List<Pais> misPaises) {
		this.misPaises = misPaises;
	}

	public List<ConexionPaises> getConexiones() {
		return conexiones;
	}

	public void setConexiones(List<ConexionPaises> conexiones) {
		this.conexiones = conexiones;
	}
	
	

}
