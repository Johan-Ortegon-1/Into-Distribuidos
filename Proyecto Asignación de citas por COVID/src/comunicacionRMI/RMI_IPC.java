package comunicacionRMI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
	public boolean responderPeticionCita(Paciente pacienteActual, int puerto) throws RemoteException
	{
		//Logica y funciones de INS y EPS
		
		//EPS
		//Aldegod
		
		//INS
		int puntaje = consultarINS(pacienteActual, puerto);
		System.out.println("Yo sou el paciente: " + pacienteActual.getNombre() + " Con el puntaje: " + puntaje);
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
	
	public int consultarINS(Paciente pacienteActual, int puerto)
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry("192.168.1.114", puerto);
			INS_Server cs = (INS_Server)Naming.lookup("//192.168.1.114/INS_Server");
			int pActual = cs.responderPeticionPuntaje(pacienteActual);
			return pActual;
		} 
		catch(RemoteException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
