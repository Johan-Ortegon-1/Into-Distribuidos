package com.comunicacion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.negocio.Paciente;

public interface ClienteIPSServidorPaciente extends Remote
{
	public List<Long> obtenerDocumento() throws RemoteException;
	public Paciente obtenerPacientes() throws RemoteException;
}
