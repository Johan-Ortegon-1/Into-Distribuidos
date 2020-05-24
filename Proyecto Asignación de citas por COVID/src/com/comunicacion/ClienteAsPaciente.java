package com.comunicacion;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.negocio.Paciente;

public class ClienteAsPaciente
{
	public static void iniciarClientePaciente(int puerto, Paciente pActual)
	{
		try 
		{
			
			Registry registry = LocateRegistry.getRegistry("192.168.1.114", puerto);
			ClientePacienteServidorIPS cs = (ClientePacienteServidorIPS)Naming.lookup("//192.168.1.114/ClientePacienteServidorIPS");
			boolean respuesta = cs.obtenerRespuestaSolicitudCita(pActual.getDocumento());
			System.out.println("Estimado(a): " + pActual.getNombre() + " Estado de su cita: "+ respuesta);
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
