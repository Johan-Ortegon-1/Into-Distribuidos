package comunicacionRMI;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.comunicacion.ClienteIpsServidorIns;
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
		
		//UDP
		/*ClienteIpsServidorIns clienteUDP;
		try
		{
			clienteUDP = new ClienteIpsServidorIns (pacienteActual);
			clienteUDP.enviar(pacienteActual);
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/	
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
