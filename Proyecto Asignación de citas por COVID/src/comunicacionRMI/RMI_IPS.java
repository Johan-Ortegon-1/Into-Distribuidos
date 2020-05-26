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

import com.comunicacion.IPS_Cliente_UDP;
import com.negocio.Ips;
import com.negocio.Paciente;

public class RMI_IPS extends UnicastRemoteObject implements IPS_Server
{

	private static final long serialVersionUID = 1L;
	private Ips myIps;
	
	protected RMI_IPS(Ips myIps) throws RemoteException
	{
		this.myIps = myIps;
		// TODO Auto-generated constructor stub
	}
	
	//Con base en la informacion que llega del paciente se filtra y devuelve una respuesta
	@Override
	public Paciente responderPeticionCita(Paciente pacienteActual, int puerto) throws RemoteException
	{
		System.out.println("LLEGO LA SOLUCITUD DEL PACIENTE: " + pacienteActual.getDocumento() + " NOMBRE: " + pacienteActual.getNombre());
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*");
		//Logica y funciones de INS y EPS
		
		//UDP - INS
		IPS_Cliente_UDP clienteUDP;
		try
		{
			clienteUDP = new IPS_Cliente_UDP (pacienteActual);
			clienteUDP.enviar(pacienteActual);//Modificacion por parametro
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
		}
		pacienteActual = consultarINS(pacienteActual, puerto);

		//EPS
		boolean respuesta = myIps.asignarCitas(pacienteActual);
		
		if(respuesta)
		{
			System.out.println("CITA ASIGNADA PARA: " + pacienteActual.getNombre() + " Con el puntaje: " + pacienteActual.getEvaluacion());
		}
		else
		{
			System.out.println("CITA DENEGADA PARA: " + pacienteActual.getNombre() + " Con el puntaje: " + pacienteActual.getEvaluacion());
		}
		//System.out.println(respuesta);
		return pacienteActual;
	}
	
	public Paciente consultarINS(Paciente pacienteActual, int puerto)
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry("192.168.1.63", puerto);
			INS_Server cs = (INS_Server)Naming.lookup("//192.168.1.63/INS_Server");
			Paciente pActual = cs.responderPeticionPuntaje(pacienteActual);
			return pActual;
			//return pacienteActual;
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
		return null;
		
	}

	@Override
	public String actualizacionFecha(Paciente pActual) throws RemoteException
	{
		while(true)
		{
			for (int i = 0; i < myIps.getCitasProgramadas().size(); i++) 
			{
				if(pActual.getDocumento() == myIps.getCitasProgramadas().get(i).getDocumento())
				{
					if(!pActual.getHistorial().get(0).getFecha().equals(myIps.getCitasProgramadas().get(i).getHistorial().get(0).getFecha()))
					{
						System.out.println("(!!!) REPROGRAMACION DE CITA EN CURSO");
						return myIps.getCitasProgramadas().get(i).getHistorial().get(0).getFecha();
					}
				}
			}
		}
		
	}

}
