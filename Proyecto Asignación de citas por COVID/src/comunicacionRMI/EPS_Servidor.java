package comunicacionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.negocio.Eps;
import com.negocio.Paciente;

public interface EPS_Servidor extends Remote
{
	public String autorizacionEps(Paciente pacienteActual, Eps epsActual)throws RemoteException;
}
