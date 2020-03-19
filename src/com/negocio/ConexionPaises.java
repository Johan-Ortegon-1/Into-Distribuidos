package com.negocio;

public class ConexionPaises
{
	private Pais paisOrigen;
	private Pais paisDestino;
	private char medioTransporte; /*T(Terrestre)/M(Marítimo)/A(Aéreo)*/
	public ConexionPaises(Pais paisOrigen, Pais paisDestino, char medioTransporte)
	{
		super();
		this.paisOrigen = paisOrigen;
		this.paisDestino = paisDestino;
		this.medioTransporte = medioTransporte;
	}
	public Pais getPaisOrigen()
	{
		return paisOrigen;
	}
	public void setPaisOrigen(Pais paisOrigen)
	{
		this.paisOrigen = paisOrigen;
	}
	public Pais getPaisDestino()
	{
		return paisDestino;
	}
	public void setPaisDestino(Pais paisDestino)
	{
		this.paisDestino = paisDestino;
	}
	public char getMedioTransporte()
	{
		return medioTransporte;
	}
	public void setMedioTransporte(char medioTransporte)
	{
		this.medioTransporte = medioTransporte;
	}
	
}
