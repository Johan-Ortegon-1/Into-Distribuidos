package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.negocio.Eps;
import com.negocio.Paciente;

public class RMI_EPS extends UnicastRemoteObject implements EPS_Servidor
{

	protected RMI_EPS() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
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
