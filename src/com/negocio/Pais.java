package com.negocio;

public class Pais
{
	private int poblacionTotal;
	private double porcenPoblacionVulne;
	private double porcenAislamiento;
	
	public Pais(int poblacionTotal, double porcenPoblacionVulne, double porcenAislamiento)
	{
		super();
		this.poblacionTotal = poblacionTotal;
		this.porcenPoblacionVulne = porcenPoblacionVulne;
		this.porcenAislamiento = porcenAislamiento;
	}
	public int getPoblacionTotal()
	{
		return poblacionTotal;
	}
	public void setPoblacionTotal(int poblacionTotal)
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
	
	
}
