package com.comunicacion;

import java.util.List;

import com.negocio.Cita;
import com.negocio.Paciente;

public class Ins
{
	private List<Paciente> casosReportados;

	public Ins() {
		
	}
	
	

	public Ins(List<Paciente> casosReportados)
	{
		super();
		this.casosReportados = casosReportados;
	}



	public List<Paciente> getCasosReportados() {
		return casosReportados;
	}

	public void setCasosReportados(List<Paciente> casosReportados) {
		this.casosReportados = casosReportados;
	}
	
	public int evaluarPaciente(Paciente paciente) {
		//SintomasLeves SintomaFiebre - SintomaTos - SintomaCansancio - SintomaDolor 
		//SintomasGraves FaltaAire - InsuficienciaPulmonar - ShockSeptico - FallaMultiorganica 
		int evaluacion = 0;
		int sintomas = 0;
		int edad = 0;
		String prioridad = "No enfermo";
		//PatologiasAdicionales o Cirugias
		
		List<Boolean> sintomasPa = paciente.getSintomas();
		for (int i = 0; i < sintomasPa.size(); i++) {
			if(i == 0) 	sintomas += 5;
			if(i == 1) 	sintomas += 5;
			if(i == 2) 	sintomas += 5;
			if(i == 3) 	sintomas += 5;
			if(i == 4) 	sintomas += 10;
			if(i == 5) 	sintomas += 10;
			if(i == 6) 	sintomas += 10;
			if(i == 7) 	sintomas += 10;
		}
		
		if (paciente.isPatologiasAdicionales()) 
			evaluacion += 20;
		
		if(paciente.getEdad()>=50) edad += 5;
		if(paciente.getEdad()>=60) edad += 5;
		if(paciente.getEdad()>=80) edad += 5;
		if(paciente.getEdad()>=90) edad += 5;
		
		if(paciente.getEdad()<=10) edad += 10;
		if(paciente.getEdad()<=5) edad += 10;
		
		int total = evaluacion+sintomas+edad;
		
		if (sintomas <=15 && evaluacion == 0) 
			prioridad = "Leve";
		if (sintomas ==0 && evaluacion == 0) 
			prioridad = "No enfermo";
		if (sintomas <=15 && evaluacion == 20) 
			prioridad = "Severa";
		if (sintomas >=15 && evaluacion == 20) 
			prioridad = "Grave";
		if (sintomas >=15 && edad == 10 && evaluacion == 0) 
			prioridad = "Severa";
		if (total >= 70) 
			prioridad = "Grave";
		
		paciente.setEvaluacion(total);
		paciente.setPrioridad(prioridad);
		casosReportados.add(paciente);
		
		
		//FALTA EL WAIT FORZADO DE un segundo
		return evaluacion;
	}
	
	
}
