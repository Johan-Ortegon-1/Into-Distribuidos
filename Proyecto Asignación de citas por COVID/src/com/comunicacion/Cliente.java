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
	private List<Long> documentoGlobales = new ArrayList<Long>();
	public void iniciarCliente()
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry("192.168.1.63", 1099);
			ClienteServidorIpsCliente cs = (ClienteServidorIpsCliente)Naming.lookup("//192.168.1.63/ClienteServidorIpsCliente");
			Paciente pActual = cs.obtenerPacientes();
			System.out.println("Paciente actual: " + pActual);
			System.out.println("Documentos de afiliados: ");
			for(Long iter : documentoGlobales)
			{
				System.out.print(iter + " - ");
			}
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
