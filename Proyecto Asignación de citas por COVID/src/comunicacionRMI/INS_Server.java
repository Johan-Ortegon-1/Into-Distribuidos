package comunicacionRMI;

import java.rmi.RemoteException;

import com.negocio.Paciente;

public interface INS_Server
{
	public int responderPeticionPuntaje(Paciente pacienteActual)throws RemoteException;
}
