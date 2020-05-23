package com.comunicacion;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class Servidor
{
	public void iniciarServidor(int puerto)
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			r.rebind("ClienteServidorIpsCliente", new Rmi());
			JOptionPane.showMessageDialog(null, "Servidor iniciado");
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}
	}
}
