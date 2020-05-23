package com.comunicacion;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import com.negocio.Paciente;

public class Cliente//IPS-EPS
{
	public void iniciarCliente(int puerto)
	{
		try 
		{
			System.out.println("Solicitando puerto: " + puerto);
			Registry registry = LocateRegistry.getRegistry("192.168.1.63", puerto);
			ClienteServidorIpsCliente cs = (ClienteServidorIpsCliente)Naming.lookup("//192.168.1.63/ClienteServidorIpsCliente");
			Paciente pActual = cs.obtenerPacientes();
			System.out.println("Paciente actual: " + pActual.toString());
		} 
		catch(RemoteException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
