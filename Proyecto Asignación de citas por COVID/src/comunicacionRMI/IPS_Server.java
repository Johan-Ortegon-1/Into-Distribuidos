package comunicacionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.negocio.Paciente;

public interface IPS_Server extends Remote
{
	public boolean responderPeticionCita(Paciente pacienteActual)throws RemoteException;
}
