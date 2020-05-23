package com.negocio;

import java.util.ArrayList;
import java.util.List;

public class Eps {
	
	private String nobmreEps;
	private List<Paciente> pacientesAfiliados = new ArrayList<Paciente>();
	private List<Paciente> pacientesConCita = new ArrayList<Paciente>();
	public Eps() {
		
	}
		
	public Eps(List<Paciente> pacientesAfiliados, List<Paciente> pacientesConCita, String nombre)
	{
		super();
		this.nobmreEps = nombre;
		this.pacientesAfiliados = pacientesAfiliados;
		this.pacientesConCita = pacientesConCita;
	}

	public List<Paciente> getPacientesAfiliados() {
		return pacientesAfiliados;
	}
	public void setPacientesAfiliados(List<Paciente> pacientesAfiliados) {
		this.pacientesAfiliados = pacientesAfiliados;
	}
	public List<Paciente> getPacientesConCita() {
		return pacientesConCita;
	}
	public void setPacientesConCita(List<Paciente> pacientesConCita) {
		this.pacientesConCita = pacientesConCita;
	}
	
	public void darAutorizacion(Paciente paciente) {
		// TO DO
	}
	
	public void pacientesAtender(Paciente paciente) {
		// TO DO
	}

	public String getNobmreEps()
	{
		return nobmreEps;
	}

	public void setNobmreEps(String nobmreEps)
	{
		this.nobmreEps = nobmreEps;
	}
	
}
