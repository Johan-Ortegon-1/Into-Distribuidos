package com.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paciente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private long documento;
	private int edad;
	private String eps;
	private int idCita;
	private String prioridad;
	private List<Cita> historial;
	private List<Boolean> sintomas = new ArrayList<Boolean>();
	private boolean patologiasAdicionales;
	//Falta constructor de verdad
	public Paciente() 
	{
		
	}

	public Paciente(String nombre, long documento, int edad, String eps, List<Boolean> sintomas,
			boolean patologiasAdicionales)
	{
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.edad = edad;
		this.eps = eps;
		this.sintomas = sintomas;
		this.patologiasAdicionales = patologiasAdicionales;
	}



	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getDocumento() {
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
	
	public String toString()
	{
		return this.nombre + " " + this.documento + " " + this.sintomas.get(0);
	}
	
}
