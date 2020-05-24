package com.comunicacion;

import java.io.IOException;

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
		try {
			clienteIPS.iniciarCliente(this.puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
