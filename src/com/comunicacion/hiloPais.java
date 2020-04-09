package com.comunicacion;

import com.negocio.Pais;

public class hiloPais extends Thread
{
	Pais paisactual;
	public hiloPais(Pais p)
	{
		this.paisactual = p;
	}
	public void run()
	{
		System.out.println("Corriendo nuevo pais :)");
		paisactual.experimentacion();
	}
}
