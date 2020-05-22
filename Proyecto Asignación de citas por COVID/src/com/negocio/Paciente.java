package com.negocio;

import java.util.List;

public class Paciente {
	private String nombre;
	private double documento;
	private int edad;
	private String eps;
	private int idCita;
	private String prioridad;
	private List<Cita> historial;
	
	//Falta constructor de verdad
	public Paciente() {
		
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
	public void setDocumento(double documento) {
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
	
	
	
}
