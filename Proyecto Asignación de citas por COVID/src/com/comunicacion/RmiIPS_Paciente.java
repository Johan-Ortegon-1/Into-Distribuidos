package com.comunicacion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiIPS_Paciente extends UnicastRemoteObject implements ClientePacienteServidorIPS
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	protected RmiIPS_Paciente() throws RemoteException
	{
		
	}

	@Override
	public boolean obtenerRespuestaSolicitudCita(long documento) throws RemoteException
	{
		return false;
	}

}
