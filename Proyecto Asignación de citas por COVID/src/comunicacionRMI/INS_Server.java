package comunicacionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.negocio.Paciente;

public interface INS_Server extends Remote
{
	public int responderPeticionPuntaje(Paciente pacienteActual)throws RemoteException;
}
