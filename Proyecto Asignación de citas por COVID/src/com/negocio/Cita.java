package com.negocio;

public class Cita {
	private boolean autorizacion;
	private String fecha;
	private int identificador;
	
	public Cita()
	{
		
	}
	
	public Cita(boolean autorizacion, String fecha, int identificador) {
		
		this.autorizacion = autorizacion;
		this.fecha = fecha;
		this.identificador = identificador;
	}

	public boolean isAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(boolean autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
}
