package com.comunicacion;

import java.rmi.RemoteException;

public interface ClientePacienteServidorIPS
{
	public boolean obtenerRespuestaSolicitudCita(long documento)throws RemoteException;
}
