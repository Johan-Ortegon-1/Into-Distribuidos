package com.comunicacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.negocio.Cita;
import com.negocio.Eps;
import com.negocio.Paciente;

public class Ips
{
	private List<Paciente> citasProgramadas;
	private List<Eps> entidadesEPS;
	private Ins entidadIns;
	private int maxCitasDia;
	
	public Ips() {
		
	}


	public Ips(List<Paciente> citasProgramadas, List<Eps> entidadesEPS, Ins entidadIns, int maxCitas) {
		super();
		this.citasProgramadas = citasProgramadas;
		this.entidadesEPS = entidadesEPS;
		this.entidadIns = entidadIns;
		this.maxCitasDia = maxCitas;
	}


	public List<Paciente> getCitasProgramadas() {
		return citasProgramadas;
	}


	public void setCitasProgramadas(List<Paciente> citasProgramadas) {
		this.citasProgramadas = citasProgramadas;
	}


	public List<Eps> getEntidadesEPS() {
		return entidadesEPS;
	}


	public void setEntidadesEPS(List<Eps> entidadesEPS) {
		this.entidadesEPS = entidadesEPS;
	}


	public Ins getEntidadIns() {
		return entidadIns;
	}


	public void setEntidadIns(Ins entidadIns) {
		this.entidadIns = entidadIns;
	}


	public int getMaxCitas() {
		return maxCitasDia;
	}


	public void setMaxCitas(int maxCitas) {
		this.maxCitasDia = maxCitas;
	}


	public void asignarCitas(Paciente paciente) {
		Paciente copia;
		for (int i = 0; i < entidadesEPS.size(); i++) {
			if (entidadesEPS.get(i).getNombreEps() == paciente.getEps()) {
				if(entidadesEPS.get(i).pacientesQueAtiendo(paciente)) {
					int evaluacion = entidadIns.evaluarPaciente(paciente);
					paciente.setEvaluacion(evaluacion);
					copia = entidadesEPS.get(i).darAutorizacion(paciente);
					ordenarPrioridad(copia);
				}else {
					
				}
			}
		}
	}
	
	public void reprogramarCita(Paciente paciente) {
		Calendar fecha = Calendar.getInstance();
		long day = fecha.get(Calendar.DAY_OF_MONTH);
		long month = fecha.get(Calendar.MONTH);
		long year = fecha.get(Calendar.YEAR);
		String fechas = Long.toString(day+1)+" "+Long.toString(month+1)
		                  +" "+Long.toString(year);
		
		int tamHistorial = paciente.getHistorial().size();
		paciente.getHistorial().get(tamHistorial-1).setFecha(fechas);	
		
		//Se debe informar reprogamacion de la cita
		
	}
	
	
	public void ordenarPrioridad(Paciente paciente) {
		//Queda ordenar por orden de llegada cuando hay un empate
		boolean seProgramo = false;
		boolean tieneCita = false;
		boolean tieneCoronavirus = false;
		List<Paciente> copiaCitasProgramadas = new ArrayList<Paciente>();
		
		for (int i = 0; i < citasProgramadas.size(); i++) {
			if (citasProgramadas.get(i).getDocumento() == paciente.getDocumento()) {
				tieneCita = true;
			}
		}
		
		if(paciente.getPrioridad() != "No enfermo" && paciente.getPrioridad() != "Leve")
			tieneCoronavirus = true;
		
		if(citasProgramadas.size()>=0 && !tieneCita && tieneCoronavirus) { //Existen citas disponibles
			for (int i = 0; i < citasProgramadas.size(); i++) {
				
				if (!seProgramo && i+1 < citasProgramadas.size() && i < maxCitasDia) {
					
					if(paciente.getEvaluacion() == citasProgramadas.get(i).getEvaluacion()
							&& paciente.getEvaluacion() > citasProgramadas.get(i+1).getEvaluacion()) {
						copiaCitasProgramadas.add(citasProgramadas.get(i));
						if(i == maxCitasDia-1)
							reprogramarCita(paciente);
						copiaCitasProgramadas.add(paciente);
						seProgramo = true;
					}
					
					if (paciente.getEvaluacion()>citasProgramadas.get(i).getEvaluacion()) {
						copiaCitasProgramadas.add(paciente);
						if(i == maxCitasDia-1)
							reprogramarCita(citasProgramadas.get(i));
						copiaCitasProgramadas.add(citasProgramadas.get(i));
						seProgramo = true;
					}
				}else
					copiaCitasProgramadas.add(citasProgramadas.get(i));
			}
			if (!seProgramo) {
				copiaCitasProgramadas.add(paciente);
			}
		}
		
		if (citasProgramadas.size() == 0) {
			copiaCitasProgramadas.add(paciente);
		}
		
		citasProgramadas = copiaCitasProgramadas;
		
		
	}
	
	
	
}
