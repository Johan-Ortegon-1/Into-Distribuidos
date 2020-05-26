package comunicacionRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import com.negocio.Ins;
import com.negocio.Ips;

public class hiloINS extends Thread
{
	private int puerto;
	private Ins myIns;
	public hiloINS(int puerto, Ins myIns)
	{
		this.puerto = puerto;
		this.myIns = myIns;
	}
	public void run() 
	{
		try
		{
			Registry r = java.rmi.registry.LocateRegistry.createRegistry(puerto);
			RMI_INS nuevoRmi = new RMI_INS(myIns);
			r.rebind("INS_Server", nuevoRmi);			
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Servidor fallido");
			e.printStackTrace();
		}
	}
}
