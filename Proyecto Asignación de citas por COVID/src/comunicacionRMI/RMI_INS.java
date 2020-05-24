package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.negocio.Paciente;

public class RMI_INS extends UnicastRemoteObject implements INS_Server
{

	protected RMI_INS() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int responderPeticionPuntaje(Paciente pacienteActual) throws RemoteException
	{
		if(pacienteActual.getNombre().equals("Zeuz"))
		{
			return 95;
		}
		return 50;
	}

}
