package com.comunicacion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import com.negocio.Paciente;

public class ClienteAsIPS
{
	
	public void iniciarCliente(int puerto) throws IOException
	{
		try 
		{
			//RMI - Recibir datos del paciente
			System.out.println("Solicitando puerto: " + puerto);
			Registry registry = LocateRegistry.getRegistry("192.168.1.63", puerto);
			ClienteIPSServidorPaciente cs = (ClienteIPSServidorPaciente)Naming.lookup("//192.168.1.63/ClienteIPSServidorPaciente");
			Paciente pActual = cs.obtenerPacientes();
			System.out.println("Paciente actual: " + pActual.toString());

			//INTERNARNA - Validar paciente con EPS
			
			//UDP - Validar paciente INS
			
			//RMI - Devolver resultado al paciente
			
			//UDP
			IPS_Cliente_UDP clienteUDP = new IPS_Cliente_UDP (pActual);	
			clienteUDP.enviar(pActual);

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
