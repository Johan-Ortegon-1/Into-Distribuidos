package com.negocio;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
	
	private String nombre;
	private long documento;
	private int edad;
	private String eps;
	private int idCita;
	private String prioridad;
	private List<Cita> historial;
	private List<Boolean> sintomas = new ArrayList<Boolean>();
	private Boolean patologiasAdicionales;
	//Falta constructor de verdad
	public Paciente() 
	{
		
	}
	
	public Paciente(String nombre, long documento, int edad, String eps, int idCita, String prioridad,
			List<Cita> historial)
	{
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.edad = edad;
		this.eps = eps;
		this.idCita = idCita;
		this.prioridad = prioridad;
		this.historial = historial;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getDocumento() {
		return documento;
	}
	public void setDocumento(long documento) {
		this.documento = documento;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getEps() {
		return eps;
	}
	public void setEps(String eps) {
		this.eps = eps;
	}
	public int getIdCita() {
		return idCita;
	}
	public void setIdCita(int idCita) {
		this.idCita = idCita;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public List<Cita> getHistorial() {
		return historial;
	}
	public void setHistorial(List<Cita> historial) {
		this.historial = historial;
	}

	public List<Boolean> getSintomas()
	{
		return sintomas;
	}

	public void setSintomas(List<Boolean> sintomas)
	{
		this.sintomas = sintomas;
	}

	public Boolean getPatologiasAdicionales()
	{
		return patologiasAdicionales;
	}

	public void setPatologiasAdicionales(Boolean patologiasAdicionales)
	{
		this.patologiasAdicionales = patologiasAdicionales;
	}
	
	
	
	
	
}
