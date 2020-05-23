package com.comunicacion;

import com.negocio.Paciente;

public class hiloPaciente extends Thread
{
	private Paciente pacienteActual;
	private int puerto;
	public hiloPaciente(Paciente p, int puerto)
	{
		this.pacienteActual = p; 
		this.puerto = puerto;
	}
	public void run() 
	{
		Servidor servidorCliente = new Servidor();
		servidorCliente.iniciarServidor(puerto);
	}
}
