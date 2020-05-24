package com.comunicacion;

import java.util.ArrayList;
import java.util.List;

import com.negocio.Cita;
import com.negocio.Paciente;

public class Ins
{
	private List<Paciente> casosReportados;

	public Ins() {
		casosReportados = new ArrayList<Paciente>();
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
	
	public void evaluarPaciente(Paciente paciente) {
		//SintomasLeves SintomaFiebre - SintomaTos - SintomaCansancio - SintomaDolor 
		//SintomasGraves FaltaAire - InsuficienciaPulmonar - ShockSeptico - FallaMultiorganica 
		int evaluacion = 0;
		int sintomas = 0;
		int edad = 0;
		String prioridad = "No enfermo";
		//PatologiasAdicionales o Cirugias
		
		
		List<Boolean> sintomasPa = paciente.getSintomas();
		for (int i = 0; i < 4; i++) {
			if(sintomasPa.get(i)) {
				//System.out.println(i);
				sintomas += 5;
			}	
		}
		for (int i = 4; i < 7; i++) {
			if(sintomasPa.get(i)) {
				//System.out.println(i);
				sintomas += 10;
			}		
		}
		if (paciente.isPatologiasAdicionales()) {
			//System.out.println("ADICIONAL");
			evaluacion += 20;
		}

		if(paciente.getEdad()>=50) {
			//System.out.println("EDAD 50");
			edad += 5;
		}
		if(paciente.getEdad()>=60) {
			//System.out.println("EDAD 60");
			edad += 5;
		}
		if(paciente.getEdad()>=70) {
			//System.out.println("EDAD 70");
			edad += 5;
		}
		if(paciente.getEdad()>=80) {
			//System.out.println("EDAD 80");
			edad += 5;
		}
		
		if(paciente.getEdad()<=10) {
			//System.out.println("EDAD 10");
			edad += 10;
		}
		if(paciente.getEdad()<=5) {
			//System.out.println("EDAD 5");
			edad += 10;
		}
		
		int total = evaluacion+sintomas+edad;
		
		if (sintomas ==0 && evaluacion == 0) {
			//System.out.println("Sintomas 2");
			prioridad = "No enfermo";
		}
		if (sintomas >= 40) {
			//System.out.println("Sintomas 5.1");
			prioridad = "Leve";
		}
		if (sintomas <=15 && evaluacion == 0) {
			//System.out.println("Sintomas 1");
			prioridad = "Leve";
		}
		if (sintomas >=15 && edad == 10 && evaluacion == 0) {
			//System.out.println("Sintomas 5");
			prioridad = "Severa";
		}
		if (sintomas >= 50) {
			//System.out.println("Sintomas 5.1");
			prioridad = "Severa";
		}
		if (sintomas <=15 && evaluacion == 20 && total>=40) {
			//System.out.println("Sintomas 3");
			prioridad = "Severa";
		}
		if (sintomas >=30 && edad>=5) {
			//System.out.println("Sintomas 3.1");
			prioridad = "Severa";
		}
		if (sintomas >=15 && evaluacion == 20 && sintomas <=40) {
			//System.out.println("Sintomas 4");
			prioridad = "Severa";
		}
		if (sintomas >=15 && evaluacion == 20 && edad>=5) {
			//System.out.println("Sintomas 4");
			prioridad = "Grave";
		}
		if (total >= 70) {
			//System.out.println("Sintomas 6");
			prioridad = "Grave";
		}
		if (sintomas >= 20 && total <= 30) {
			prioridad = "Leve";
		}
		
		paciente.setEvaluacion(total);
		paciente.setPrioridad(prioridad);
		casosReportados.add(paciente);
		
		
		//FALTA EL WAIT FORZADO DE un segundo
	}
	
	
}
