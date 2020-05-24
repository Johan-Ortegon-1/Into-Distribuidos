package com.comunicacion;

import com.negocio.Paciente;

public class hiloPacienteRespondeIPS extends Thread
{
	private Paciente pacienteActual;
	private int puerto;
	public hiloPacienteRespondeIPS(Paciente p, int puerto)
	{
		this.pacienteActual = p; 
		this.puerto = puerto;
	}
	public void run() 
	{
		ServidorAsPaciente servidorCliente = new ServidorAsPaciente();
		servidorCliente.iniciarServidor(puerto, pacienteActual);
	}
}
