package com.negocio;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Eps {
	private String nombreEps;
	private List<Paciente> pacientesAfiliados;
	public Eps() {
		pacientesAfiliados = new ArrayList<Paciente>();
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
	public boolean darAutorizacion(Paciente paciente) {
		boolean aprobado = false;
		for (int i = 0; i < pacientesAfiliados.size(); i++) {
			if (pacientesAfiliados.get(i).getCubrimiento() >= paciente.getEvaluacion()
					&& pacientesAfiliados.get(i).getDocumento()==paciente.getDocumento()) {
				aprobado = true;
				//System.out.println("CUBRIMIENTO "+ pacientesAfiliados.get(i).getCubrimiento() +" " +paciente.getEvaluacion());
				long identificador = paciente.getDocumento();
				
				Calendar fecha = Calendar.getInstance();
				long day = fecha.get(Calendar.DAY_OF_MONTH);
				long month = fecha.get(Calendar.MONTH);
				long year = fecha.get(Calendar.YEAR);
				String fechas = Long.toString(day)+"/"+Long.toString(month+1)
				                  +"/"+Long.toString(year);
				
				Cita nuevaCita = new Cita(aprobado, fechas, identificador);
				paciente.getHistorial().add(nuevaCita);
				pacientesAfiliados.get(i).getHistorial().add(nuevaCita);
				//System.out.println(pacientesAfiliados.get(i).getHistorial().get(0).getFecha());
				
			}
		}
		return aprobado;
	}
	
	public boolean pacientesQueAtiendo(Paciente paciente) {
		boolean atiendo = false;
		for (int i = 0; i < pacientesAfiliados.size(); i++) {
			if (pacientesAfiliados.get(i).getDocumento() == paciente.getDocumento()) {
				//System.out.println("ME ATIENDE ESTA EPS "+paciente.getDocumento()+" "+nombreEps);
				atiendo = true;
			}
		}
		return atiendo;
	}

}
