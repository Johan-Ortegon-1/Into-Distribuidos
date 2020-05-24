package com.comunicacion;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import com.controladores.Main;
import com.negocio.Paciente;

public class ServidorAsIPS
{
	public void iniciarServidor(int puerto, Paciente p)
	{
		/*try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(2000);
			RmiIPS_Paciente nuevoRmi = new RmiIPS_Paciente();
			nuevoRmi.setPacientesGlobales(Main.getPacientesGlobales());
			r.rebind("ClienteIPSServidorPaciente", nuevoRmi);
			JOptionPane.showMessageDialog(null, "Servidor iniciado");
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}*/
	}
}
