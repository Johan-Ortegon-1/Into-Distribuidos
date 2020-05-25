package comunicacionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.negocio.Paciente;

public interface IPS_Server extends Remote
{
	public Paciente responderPeticionCita(Paciente pacienteActual, int puerto)throws RemoteException;
	public String actualizacionFecha(Paciente pActual) throws RemoteException;
}
