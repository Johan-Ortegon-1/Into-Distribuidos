package com.negocio;

import java.util.List;

public class Pais
{
	/*Atributos propios del pais*/
	
	private int id;
	private String nombre;
	private long poblacionTotal;
	private double porcenPoblacionVulne;
	private double porcenAislamiento;
	private boolean infectado;
	private long infectados; 
	//private double propagacionVirus;
	
	/*Atributos necesarios para el balanceo de cargas*/
	private int contOperacionesRealizadas = 0;
	
	public Pais(int poblacionTotal, double porcenPoblacionVulne, double porcenAislamiento)
	{
		super();
		this.poblacionTotal = poblacionTotal;
		this.porcenPoblacionVulne = porcenPoblacionVulne;
		this.porcenAislamiento = porcenAislamiento;
	}
	
	public Pais() {
		super();
	}

	public Pais(int id, String nombre, long poblacionTotal, double porcenPoblacionVulne, double porcenAislamiento, 
			int contOperacionesRealizadas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.poblacionTotal = poblacionTotal;
		this.porcenPoblacionVulne = porcenPoblacionVulne;
		this.porcenAislamiento = porcenAislamiento;
		this.contOperacionesRealizadas = contOperacionesRealizadas;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	public boolean isInfectado() {
		return infectado;
	}

	public void setInfectado(boolean infectado) {
		this.infectado = infectado;
	}
	
	public long getInfectados() {
		return infectados;
	}

	public void setInfectados(long infectados) {
		this.infectados = infectados;
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
	
	public static Pais buscarPais(List<Pais> paises, int id)
	{
		int indicePaisAct=0;
		for (int i = 0; i < paises.size(); i++)//Buscar el pais con ese id y crear un agente con el
		{
			if(paises.get(i).getId() == id)
			{
				indicePaisAct = i;
				//Agente nuevoAgente = new Agente(paises.get(i),paises.get(i).);
			}
		}
		return paises.get(indicePaisAct);
	}
	
	@Override
	public String toString()
	{
		return "Pais [id=" + id + ", nombre=" + nombre + ", poblacionTotal=" + poblacionTotal + "]";
	}
	
	
}
