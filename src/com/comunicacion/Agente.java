package com.comunicacion;

import java.util.List;
import java.util.Vector;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class Agente {
	
	private Pais miPais;
	private List<ConexionPaises> conexiones = new Vector<ConexionPaises>();
	private ModeloVirus covid19 ;
	
	public Agente(Pais miPais, List<ConexionPaises> conexiones, ModeloVirus covid19) {
		super();
		this.miPais = miPais;
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
	
	public Pais getMiPais() {
		return miPais;
	}

	public void setMiPais(Pais miPais) {
		this.miPais = miPais;
	}

	public List<ConexionPaises> getConexiones() {
		return conexiones;
	}

	public void setConexiones(List<ConexionPaises> conexiones) {
		this.conexiones = conexiones;
	}
	
	
	

}
