package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

import com.comunicacion.Ips;

public class hiloIPS extends Thread
{
	private int puerto;
	private Ips myIps;
	public hiloIPS(int puerto,Ips myIps)
	{
		this.puerto = puerto;
		this.myIps = myIps;
	}
	public void run() 
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			RMI_IPS nuevoRmi = new RMI_IPS(myIps);
			r.rebind("IPS_Server", nuevoRmi);
			JOptionPane.showMessageDialog(null, "Servidor iniciado");
			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}
	}
}
