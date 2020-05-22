package com.comunicacion;

import java.util.List;

import com.negocio.Cita;
import com.negocio.Paciente;

public class Ips
{
	private List<Paciente> pacientesAAtender;
	private List<Cita> citasProgramadas;
	
	public Ips() {
		
	}
	
	public Ips(List<Paciente> pacientesAAtender, List<Cita> citasProgramadas)
	{
		super();
		this.pacientesAAtender = pacientesAAtender;
		this.citasProgramadas = citasProgramadas;
	}

	public List<Paciente> getPacientesAAtender() {
		return pacientesAAtender;
	}
	public void setPacientesAAtender(List<Paciente> pacientesAAtender) {
		this.pacientesAAtender = pacientesAAtender;
	}
	public List<Cita> getCitasProgramadas() {
		return citasProgramadas;
	}
	public void setCitasProgramadas(List<Cita> citasProgramadas) {
		this.citasProgramadas = citasProgramadas;
	}
	
	public void asignarCitas(Paciente paciente) {
		// To do
	}
	
	public void reprogramarCita(Paciente paciente) {
		// To do
	}
	
	
}
