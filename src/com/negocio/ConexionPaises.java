package com.negocio;

public class ConexionPaises
{
	private String paisA;
	private String paisB;
	private char medioTransporte; /*T(Terrestre)/M(Marítimo)/A(Aéreo)*/
	
	public ConexionPaises() {
		super();
	}
	public ConexionPaises(String paisA, String paisB, char medioTransporte) {
		super();
		this.paisA = paisA;
		this.paisB = paisB;
		this.medioTransporte = medioTransporte;
	}
	
	public String getPaisA() {
		return paisA;
	}
	public void setPaisA(String paisA) {
		this.paisA = paisA;
	}
	public String getPaisB() {
		return paisB;
	}
	public void setPaisB(String paisB) {
		this.paisB = paisB;
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
