package com.comunicacion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.controladores.Main;
import com.negocio.Paciente;

public class Rmi extends UnicastRemoteObject implements ClienteServidorIpsCliente
{
	private List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
	public Rmi() throws RemoteException
	{
		
	}

	@Override
	public List<Long> obtenerDocumento() throws RemoteException
	{
		List<Long> documento = new ArrayList<Long>();
		List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
		pacientesGlobales = Main.getPacientesGlobales();
		for(Paciente iter : pacientesGlobales)
		{
			documento.add(iter.getDocumento());
		}
		return documento;
	}
	
	@Override
	public Paciente obtenerPacientes() throws RemoteException
	{
		List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
		pacientesGlobales = Main.getPacientesGlobales();
		
		//Creacion de hilo por paciente
		
		return null;
	}
	
	public List<Paciente> getPacientesGlobales()
	{
		return pacientesGlobales;
	}

	public void setPacientesGlobales(List<Paciente> pacientesGlobales)
	{
		this.pacientesGlobales = pacientesGlobales;
	}
	
}
