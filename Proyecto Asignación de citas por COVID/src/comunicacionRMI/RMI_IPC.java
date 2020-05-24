package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.negocio.Paciente;

public class RMI_IPC extends UnicastRemoteObject implements IPS_Server
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected RMI_IPC() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Con base en la informacion que llega del paciente se filtra y devuelve una respuesta
	@Override
	public boolean responderPeticionCita(Paciente pacienteActual) throws RemoteException
	{
		//Logica y funciones de INS y EPS
		if(pacienteActual.getNombre().equals("Zeuz"))//Logica de EPS y INS
			return true;
		return false;
	}

	@Override
	public String actualizacionFecha() throws RemoteException
	{
		while(true)
		{
			/*llego paciente con mayor prioridad*/
			if(true)
			{
				return "25/10/20";
			}
		}
		
	}

}
