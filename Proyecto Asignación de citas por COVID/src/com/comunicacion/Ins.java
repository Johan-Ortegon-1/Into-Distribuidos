package com.comunicacion;

import java.util.List;

import com.negocio.Cita;
import com.negocio.Paciente;

public class Ins
{
	private List<Cita> casosReportados;

	public Ins() {
		
	}
	

	public Ins(List<Cita> casosReportados)
	{
		super();
		this.casosReportados = casosReportados;
	}



	public List<Cita> getCasosReportados() {
		return casosReportados;
	}

	public void setCasosReportados(List<Cita> casosReportados) {
		this.casosReportados = casosReportados;
	}
	
	public double calcularPrioridad(Paciente paciente) {
		return 0;
	}
	
	
}
