package com.controladores;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.comunicacion.RmiPaciente_IPS;
import com.comunicacion.ServidorUDP;
import com.comunicacion.hiloIPSSolicitaPaciente;
import com.comunicacion.hiloPacienteRespondeIPS;
import com.negocio.Eps;
import com.negocio.Ins;
import com.negocio.Ips;
import com.negocio.Paciente;
import com.persistencia.ManejadorArchivos;

import comunicacionRMI.hiloINS;
import comunicacionRMI.hiloIPS;
import comunicacionRMI.hiloPaciente;

public class Main
{
	static List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
	static List<Eps> epsGlobales = new ArrayList<Eps>();
	static List<hiloPacienteRespondeIPS> misHilosPaciente = new ArrayList<hiloPacienteRespondeIPS>();
	static List<hiloIPSSolicitaPaciente> miIPS = new ArrayList<hiloIPSSolicitaPaciente>();
	static List<hiloIPS> miHilosIPS = new ArrayList<hiloIPS>();
	static List<hiloINS> miHilosINS = new ArrayList<hiloINS>();
	static RmiPaciente_IPS miRmi;
	static int puertoActual = 1099;
	static Ips myIps = new Ips();
	static Ins myIns = new Ins();
	
	public static void main(String[] args)
	{
		//ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		int j = 0;
		ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		myIps.setEntidadesEPS(epsGlobales);
		inciarEquipo();
	}
	public static Eps buscarEsp(String nombre)
	{
		for(Eps iter : epsGlobales)
		{
			if(iter.getNombreEps().equals(nombre))
			{
				return iter;
			}
		}
		return null;
	}
	public static  List<Paciente> pacientePorEps(String nombre)
	{
		List<Paciente> retorno = new ArrayList<Paciente>();
		for(Paciente iter : pacientesGlobales)
		{
			if(iter.getEps().equals(nombre))
			{
				retorno.add(iter);
			}
		}
		return retorno;
	}
	public static void inciarEquipo()
	{
		int tipoDeInicio = 0;
		System.out.println("Como desea iniciar esta maquina: 1. Eps, 2. Ips, 3.Ins, 4. Cliente");
		Scanner reader = new Scanner(System.in);
		tipoDeInicio = reader.nextInt();
		if(tipoDeInicio == 1)
		{

		}
		if(tipoDeInicio == 2)//IPS
		{
			//JOptionPane.showMessageDialog(null, "Servidor iniciado");
			for (Paciente iterP : pacientesGlobales)
			{
				hiloIPS nuevoIPS = new hiloIPS(puertoActual,myIps);
				miHilosIPS.add(nuevoIPS);
				nuevoIPS.start();
				puertoActual++;
			}
		}
		if(tipoDeInicio == 3)//INS
		{
			try
			{
				System.out.println("Iniciando INS");
				ServidorUDP nuevoUDP  =  new ServidorUDP(myIns);
				nuevoUDP.starl();
				
				
				for (Paciente iterP : pacientesGlobales)
				{
					hiloINS nuevoINS = new hiloINS(puertoActual,myIns);
					miHilosINS.add(nuevoINS);
					nuevoINS.start();
					puertoActual++;
				}
				int i = 0;
				System.out.println("---TOTAL CASOS REPORTADOS REGISTRADOS EN INS---");
				for(Paciente iter : myIns.getCasosReportados())
				{
					System.out.println("Caso: " + i + " " + iter.getDocumento() + " Con nombre: " + iter.getNombre());
				}
				
			} catch (SocketException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(tipoDeInicio == 4)//Cliente
		{
			for (Paciente iterP : pacientesGlobales)
			{
				hiloPaciente nuevoPaciente = new hiloPaciente(puertoActual, iterP);
				nuevoPaciente.start();
				puertoActual++;
			}
		}

	}
	public static List<Paciente> getPacientesGlobales()
	{
		return pacientesGlobales;
	}
	
	
	
	
	public void llamadaGestorTransacciones()
	{
		
	}

}
