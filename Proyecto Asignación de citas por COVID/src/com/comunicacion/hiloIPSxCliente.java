package com.comunicacion;

public class hiloIPSxCliente extends Thread
{
	private int puerto;
	public hiloIPSxCliente(int puerto)
	{
		this.puerto = puerto;
	}
	public void run() 
	{
		Cliente clienteIPS = new Cliente();
		clienteIPS.iniciarCliente();
	}
}
