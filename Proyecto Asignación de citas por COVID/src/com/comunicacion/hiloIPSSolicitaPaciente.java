package com.comunicacion;

public class hiloIPSSolicitaPaciente extends Thread
{
	private int puerto;
	public hiloIPSSolicitaPaciente(int puerto)
	{
		this.puerto = puerto;
	}
	public void run() 
	{
		ClienteAsIPS clienteIPS = new ClienteAsIPS();
		clienteIPS.iniciarCliente(this.puerto);
	}
}
