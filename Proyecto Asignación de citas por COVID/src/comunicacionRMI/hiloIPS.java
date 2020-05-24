package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class hiloIPS extends Thread
{
	private int puerto;
	public hiloIPS(int puerto)
	{
		this.puerto = puerto;
	}
	public void run() 
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			RMI_IPC nuevoRmi = new RMI_IPC();
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
