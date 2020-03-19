package com.negocio;

public class ModeloVirus
{
	private double tasaTransmision;
	private double tasaMortalidadVul;
	private double tasaMortalidadNoVul;
	
	public ModeloVirus(double tasaTransmision, double tasaMortalidadVul, double tasaMortalidadNoVul)
	{
		super();
		this.tasaTransmision = tasaTransmision;
		this.tasaMortalidadVul = tasaMortalidadVul;
		this.tasaMortalidadNoVul = tasaMortalidadNoVul;
	}
	
	public double getTasaTransmision()
	{
		return tasaTransmision;
	}
	public void setTasaTransmision(double tasaTransmision)
	{
		this.tasaTransmision = tasaTransmision;
	}
	public double getTasaMortalidadVul()
	{
		return tasaMortalidadVul;
	}
	public void setTasaMortalidadVul(double tasaMortalidadVul)
	{
		this.tasaMortalidadVul = tasaMortalidadVul;
	}
	public double getTasaMortalidadNoVul()
	{
		return tasaMortalidadNoVul;
	}
	public void setTasaMortalidadNoVul(double tasaMortalidadNoVul)
	{
		this.tasaMortalidadNoVul = tasaMortalidadNoVul;
	}
	
	
}
