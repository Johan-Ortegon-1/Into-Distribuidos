package com.negocio;

import java.util.List;

public class Pais
{
	/*Atributos propios del pais*/
	
	private long poblacionTotal;
	private double porcenPoblacionVulne;
	private double porcenAislamiento;
	private List<ConexionPaises> conexiones;
	private double propagacionVirus;
	
	/*Atributos necesarios para el balanceo de cargas*/
	private int contOperacionesRealizadas = 0;
	
	public Pais(int poblacionTotal, double porcenPoblacionVulne, double porcenAislamiento)
	{
		super();
		this.poblacionTotal = poblacionTotal;
		this.porcenPoblacionVulne = porcenPoblacionVulne;
		this.porcenAislamiento = porcenAislamiento;
	}
	
	
	
	public int getContOperacionesRealizadas()
	{
		System.out.println("OPERACIONES: " + contOperacionesRealizadas);
		return contOperacionesRealizadas;
	}

	public void setContOperacionesRealizadas(int contOperacionesRealizadas)
	{
		this.contOperacionesRealizadas = contOperacionesRealizadas;
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
	/*Funcion de prueba para el balanceador - de uso temporal*/
	public void experimentacion()
	{
		try
		{
			while(true)
			{
				System.out.println("**Experimentando**");
				contOperacionesRealizadas++;
				this.setContOperacionesRealizadas(contOperacionesRealizadas);
				Thread.sleep(4000);
			}
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
