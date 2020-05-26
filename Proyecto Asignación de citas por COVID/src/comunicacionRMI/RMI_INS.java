package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.negocio.Ins;
import com.negocio.Paciente;

public class RMI_INS extends UnicastRemoteObject implements INS_Server
{
	private Ins myIns;
	protected RMI_INS(Ins myIns) throws RemoteException
	{
		this.myIns = myIns;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Paciente responderPeticionPuntaje(Paciente pacienteActual) throws RemoteException
	{
		myIns.evaluarPaciente(pacienteActual);
		//System.out.println("Por referencia 1: " + pacienteActual.getEvaluacion());
		return pacienteActual;
	}

}
