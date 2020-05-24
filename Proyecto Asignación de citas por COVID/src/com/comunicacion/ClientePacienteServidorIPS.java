package com.comunicacion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientePacienteServidorIPS extends Remote
{
	public boolean obtenerRespuestaSolicitudCita(long documento)throws RemoteException;
}
