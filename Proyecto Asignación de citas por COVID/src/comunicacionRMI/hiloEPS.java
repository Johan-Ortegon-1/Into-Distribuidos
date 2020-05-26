package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import com.negocio.Ins;

public class hiloEPS extends Thread
{
	private int puerto;
	public hiloEPS (int puerto)
	{
		this.puerto = puerto;
	}
	public void run() 
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			RMI_EPS nuevoRmi = new RMI_EPS();
			r.rebind("EPS_Servidor", nuevoRmi);	
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}
	}
}

