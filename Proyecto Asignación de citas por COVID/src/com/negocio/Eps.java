package com.negocio;

import java.util.List;

public class Eps {
	List<Paciente> pacientesAfiliados;
	List<Paciente> pacientesConCita;
	public Eps() {
		
	}
		
	public Eps(List<Paciente> pacientesAfiliados, List<Paciente> pacientesConCita)
	{
		super();
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
	
}
