package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class hiloINS extends Thread
{
	private int puerto;
	public hiloINS(int puerto)
	{
		this.puerto = puerto;
	}
	public void run() 
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			RMI_INS nuevoRmi = new RMI_INS();
			r.rebind("INS_Server", nuevoRmi);			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}
	}
}
