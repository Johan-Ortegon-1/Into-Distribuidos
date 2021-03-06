package com.negocio;

import java.util.List;

public class Pais
{
	private long poblacionTotal;
	private double porcenPoblacionVulne;
	private double porcenAislamiento;
	private List<ConexionPaises> conexiones;
	private double propagacionVirus;
	public Pais(int poblacionTotal, double porcenPoblacionVulne, double porcenAislamiento)
	{
		super();
		this.poblacionTotal = poblacionTotal;
		this.porcenPoblacionVulne = porcenPoblacionVulne;
		this.porcenAislamiento = porcenAislamiento;
	}
	public long getPoblacionTotal()
	{
		return poblacionTotal;
	}
	public void setPoblacionTotal(long poblacionTotal)
	{
		this.poblacionTotal = poblacionTotal;
	}
	public double getPorcenPoblacionVulne()
	{
		return porcenPoblacionVulne;
	}
	public void setPorcenPoblacionVulne(double porcenPoblacionVulne)
	{
		this.porcenPoblacionVulne = porcenPoblacionVulne;
	}
	public double getPorcenAislamiento()
	{
		return porcenAislamiento;
	}
	public void setPorcenAislamiento(double porcenAislamiento)
	{
		this.porcenAislamiento = porcenAislamiento;
	}
	public List<ConexionPaises> getConexiones()
	{
		return conexiones;
	}
	public void setConexiones(List<ConexionPaises> conexiones)
	{
		this.conexiones = conexiones;
	}
	public double getPropagacionVirus()
	{
		return propagacionVirus;
	}
	public void setPropagacionVirus(double propagacionVirus)
	{
		this.propagacionVirus = propagacionVirus;
	}
	
	public double calcularTasaDePropagacion()
	{
		/*Se haran calculos dependiendo lo que se investigue de automatas celulares y las reglas de propagacion de un virus*/
		return 0;
	}
}
