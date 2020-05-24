package com.controladores;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.comunicacion.ClienteAsIPS;
import com.comunicacion.RmiPaciente_IPS;
import com.comunicacion.ServidorAsPaciente;
import com.comunicacion.hiloIPSSolicitaPaciente;
import com.comunicacion.hiloPacienteRespondeIPS;
import com.negocio.Eps;
import com.negocio.Paciente;
import com.persistencia.ManejadorArchivos;

public class Main
{
	static List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
	static List<Eps> epsGlobales = new ArrayList<Eps>();
	static List<hiloPacienteRespondeIPS> misHilosPaciente = new ArrayList<hiloPacienteRespondeIPS>();
	static List<hiloIPSSolicitaPaciente> miIPS = new ArrayList<hiloIPSSolicitaPaciente>();
	static RmiPaciente_IPS miRmi;
	static int puertoActual = 1099;
	public static void main(String[] args)
	{
		int j = 0;
		pacientesGlobales = ManejadorArchivos.leerArchivo("Archivo/pacientes.txt");
		for(int i = 0; i< pacientesGlobales.size(); i++)
    	{
			String nombreEps = pacientesGlobales.get(i).getEps();
			Eps epsActual = buscarEsp(nombreEps);
			if(epsActual == null)//Nueva Eps
			{
				Eps nuevaEps = new Eps();
				nuevaEps.setNobmreEps(nombreEps);
				List<Paciente> pacientesActuales = new ArrayList<Paciente>();
				pacientesActuales = pacientePorEps(nombreEps);
				nuevaEps.setPacientesAfiliados(pacientesActuales);
				epsGlobales.add(nuevaEps);
				j++;
			}
			epsActual = null;
    	}

		System.out.println("Infor de Eps");
		for(Eps iter : epsGlobales)
		{
			System.out.println(iter.getNobmreEps() + " Pacientes: " + iter.getPacientesAfiliados());
			for(Paciente p : iter.getPacientesAfiliados())
			{
				System.out.println("Paciente: " + p.getDocumento());
			}
		}	
		inciarEquipo();
	}
	public static Eps buscarEsp(String nombre)
	{
		for(Eps iter : epsGlobales)
		{
			if(iter.getNobmreEps().equals(nombre))
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
			for (Paciente iterP : pacientesGlobales)
			{
				hiloIPSSolicitaPaciente nuevoIPS = new hiloIPSSolicitaPaciente(puertoActual);
				miIPS.add(nuevoIPS);
				nuevoIPS.start();
				puertoActual++;
			}
		}
		if(tipoDeInicio == 3)//INS
		{
			for(Paciente p : pacientesGlobales) {
				//
			}
		}
		if(tipoDeInicio == 4)//Cliente
		{
			for (Paciente iterP : pacientesGlobales)
			{
				hiloPacienteRespondeIPS nuevoPaciente = new hiloPacienteRespondeIPS(iterP, puertoActual);
				misHilosPaciente.add(nuevoPaciente);
				nuevoPaciente.start();
				puertoActual++;
			}
		}
	}
	public static List<Paciente> getPacientesGlobales()
	{
		return pacientesGlobales;
	}

}
