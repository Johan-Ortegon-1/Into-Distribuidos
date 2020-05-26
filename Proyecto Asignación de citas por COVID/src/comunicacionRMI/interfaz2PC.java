package comunicacionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.negocio.Paciente;

public interface interfaz2PC extends Remote
{
	public Paciente solicitarCommitSolicitud(Paciente pacienteActual, int puerto)throws RemoteException;
}
