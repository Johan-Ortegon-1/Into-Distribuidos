package comunicacionRMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

import com.comunicacion.ClienteIPSServidorPaciente;
import com.negocio.Paciente;

public class hiloPaciente extends Thread
{
	private int puerto;
	private Paciente pacienteActual;
	public hiloPaciente(int puerto, Paciente pActual)
	{
		this.puerto = puerto;
		this.pacienteActual = pActual;
	}
	public void run() 
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry("192.168.1.63", puerto);
			IPS_Server cs = (IPS_Server)Naming.lookup("//192.168.1.63/IPS_Server");
			boolean pActual = cs.responderPeticionCita(this.pacienteActual, puerto);
			
			System.out.println("La respuesta del servidor: " + pActual + " Yo soy: " + pacienteActual.getNombre());
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
	}
}
