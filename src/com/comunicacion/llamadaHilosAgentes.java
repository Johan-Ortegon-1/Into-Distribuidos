package com.comunicacion;

import java.util.List;
import java.util.Vector;

import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class llamadaHilosAgentes {
	private List<Agente> agentes = new Vector<Agente>();
	private List<Pais> misPaises = new Vector<Pais>();
	private List<ConexionPaises> conexiones = new Vector<ConexionPaises>();
	private ModeloVirus covid19 = new ModeloVirus();

	

	public llamadaHilosAgentes() {
		super();
	}



	public llamadaHilosAgentes(List<Agente> agentes, List<Pais> misPaises, List<ConexionPaises> conexiones,
			ModeloVirus covid19) {
		super();
		this.agentes = agentes;
		this.misPaises = misPaises;
		this.conexiones = conexiones;
		this.covid19 = covid19;
	}
	
	public void llamadaHilos() {
		for(int i = 0; i< agentes.size();i++ ) {
//			System.out.println(agentes.get(i).getMiPais().getNombre());
//			System.out.println(agentes.get(i).getMiPais().isInfectado());
//			System.out.println(agentes.get(i).getMiPais().getInfectados());
//			System.out.println(agentes.get(i).getMiPais().getPoblacionTotal());
        }
		
		for(int i = 0; i < agentes.size(); i++) {
			AutomataCelular st = new AutomataCelular(agentes.get(i));
			st.start();
		}
		
		
		for(int i = 0; i< agentes.size();i++ ) {
//			System.out.println(agentes.get(i).getMiPais().getNombre());
//			System.out.println(agentes.get(i).getMiPais().isInfectado());
//			System.out.println(agentes.get(i).getMiPais().getInfectados());
//			System.out.println(agentes.get(i).getMiPais().getPoblacionTotal());
        }
		
	}

	public void asignarPais(int id) {
		String nombre = "";
		Agente myAgente = new Agente();
		List<ConexionPaises> myConexion = new Vector<ConexionPaises>();
		
        for(int i = 0; i< misPaises.size();i++ ) {
        	if(id == misPaises.get(i).getId()) {
        		nombre = misPaises.get(i).getNombre();
        		myAgente.setMiPais(misPaises.get(i));
        	}
        }
        
        for(int i = 0; i< conexiones.size();i++ ) {
        	if(conexiones.get(i).getPaisA().equals(nombre) || conexiones.get(i).getPaisB().equals(nombre) ) {
        		myConexion.add(conexiones.get(i));
        	}
        }
        
        if(myConexion.size()>0) {
        	myAgente.setConexiones(myConexion);
        }
        myAgente.setCovid19(covid19);
        
        agentes.add(myAgente);
	}



	public List<Agente> getAgentes() {
		return agentes;
	}



	public void setAgentes(List<Agente> agentes) {
		this.agentes = agentes;
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



	public ModeloVirus getCovid19() {
		return covid19;
	}



	public void setCovid19(ModeloVirus covid19) {
		this.covid19 = covid19;
	}
	
	
	
	
}
