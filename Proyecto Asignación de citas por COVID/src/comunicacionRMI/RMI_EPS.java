package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.negocio.Eps;
import com.negocio.Paciente;

public class RMI_EPS extends UnicastRemoteObject implements EPS_Servidor
{

	private static final long serialVersionUID = 1L;

	protected RMI_EPS() throws RemoteException
	{
		
	}

	@Override
	public String autorizacionEps(Paciente pacienteActual, Eps epsActual) throws RemoteException
	{
		if (epsActual.pacientesQueAtiendo(pacienteActual))
		{
			if (epsActual.darAutorizacion(pacienteActual))
			{
				return "aprobado";
			}
			return "puntaje bajo";
		}
		return "sin cobertura";
	}

}
