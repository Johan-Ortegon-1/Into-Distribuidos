package com.negocio;

public class Cita {
	private boolean autorizacion;
	private String fecha;
	private long identificador;
	
	public Cita()
	{
		
	}
	
	public Cita(boolean autorizacion, String fecha, long identificador) {
		
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

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}
	
}
