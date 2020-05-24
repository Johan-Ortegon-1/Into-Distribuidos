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
	private int evaluacion;
	private List<Cita> historial =  new ArrayList<Cita>();
	private List<Boolean> sintomas = new ArrayList<Boolean>();
	private boolean patologiasAdicionales;
	private int cubrimiento; //Es un numero de 0 a 100
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

	public int getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}

	public void setPatologiasAdicionales(boolean patologiasAdicionales) {
		this.patologiasAdicionales = patologiasAdicionales;
	}

	public boolean isPatologiasAdicionales() {
		return patologiasAdicionales;
	}

	public int getCubrimiento() {
		return cubrimiento;
	}

	public void setCubrimiento(int cubrimiento) {
		this.cubrimiento = cubrimiento;
	}
	
	
	
	public String toString()
	{
		return this.nombre + " " + this.documento + " " + this.sintomas.get(0);
	}
	
}
