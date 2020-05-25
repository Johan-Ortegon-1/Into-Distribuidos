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
			Registry registry = LocateRegistry.getRegistry("192.168.1.114", puerto);
			IPS_Server cs = (IPS_Server)Naming.lookup("//192.168.1.114/IPS_Server");
			Paciente pActual = cs.responderPeticionCita(this.pacienteActual, puerto);
			
			//Impresion del resultado de peticion de cita
			if(!pActual.getHistorial().isEmpty())
			{
				System.out.println(" Yo soy: " + pacienteActual.getNombre() + " (***) CITA ASIGNADA PARA LA FECHA: " + pActual.getHistorial().get(0).getFecha());
				
				//System.out.println("COMMIT");
			}
			else
			{
				System.out.println(" Yo soy: " + pacienteActual.getNombre() + " (***) NO SE PUDO ASIGNAR LA CITA");
				//System.out.println("COMMIT");
			}
			
			//Pediente de cambios en la cita
			if(!pActual.getHistorial().isEmpty())
			{
				String nueva_fecha = cs.actualizacionFecha(pActual);
				System.out.println(" Yo soy: " + pacienteActual.getNombre() + " (!!!) MI CITA FUE REASIGNADA DEL: "+ 
						pActual.getHistorial().get(0).getFecha() + " A LA FECHA: " + nueva_fecha);
				pActual.getHistorial().get(0).setFecha(nueva_fecha);
				//System.out.println("ROLLBACK");
			}
				
			
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
