package com.negocio;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Eps {
	private String nombreEps;
	private List<Paciente> pacientesAfiliados;
	public Eps() {
		
	}

	public Eps(String nombreEps, List<Paciente> pacientesAfiliados) {
		this.nombreEps = nombreEps;
		this.pacientesAfiliados = pacientesAfiliados;
	}


	public List<Paciente> getPacientesAfiliados() {
		return pacientesAfiliados;
	}
	public void setPacientesAfiliados(List<Paciente> pacientesAfiliados) {
		this.pacientesAfiliados = pacientesAfiliados;
	}
	public String getNombreEps() {
		return nombreEps;
	}

	public void setNombreEps(String nombreEps) {
		this.nombreEps = nombreEps;
	}
	public Paciente darAutorizacion(Paciente paciente) {
		boolean aprobado = false;
		for (int i = 0; i < pacientesAfiliados.size(); i++) {
			if (pacientesAfiliados.get(i).getCubrimiento() >= paciente.getEvaluacion()) {
				aprobado = true;
				
				long identificador = paciente.getDocumento();
				
				Calendar fecha = Calendar.getInstance();
				long day = fecha.get(Calendar.DAY_OF_MONTH);
				long month = fecha.get(Calendar.MONTH);
				long year = fecha.get(Calendar.YEAR);
				String fechas = Long.toString(day)+" "+Long.toString(month+1)
				                  +" "+Long.toString(year);
				
				Cita nuevaCita = new Cita(aprobado, fechas, identificador);
				paciente.getHistorial().add(nuevaCita);
				pacientesAfiliados.get(i).getHistorial().add(nuevaCita);
				
			}
		}
		return paciente;
	}
	
	public boolean pacientesQueAtiendo(Paciente paciente) {
		boolean atiendo = false;
		for (int i = 0; i < pacientesAfiliados.size(); i++) {
			if (pacientesAfiliados.get(i).getNombre() == paciente.getNombre() && 
					pacientesAfiliados.get(i).getDocumento() == paciente.getDocumento()) {
				atiendo = true;
			}
		}
		return atiendo;
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
