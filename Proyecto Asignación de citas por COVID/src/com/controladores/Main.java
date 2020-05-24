package com.controladores;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.comunicacion.RmiPaciente_IPS;
import com.comunicacion.ServidorUDP;
import com.comunicacion.hiloIPSSolicitaPaciente;
import com.comunicacion.hiloPacienteRespondeIPS;
import com.negocio.Eps;
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
	public static void main(String[] args)
	{
		//ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		int j = 0;
		ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
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
			for (Paciente iterP : pacientesGlobales)
			{
				hiloIPS nuevoIPS = new hiloIPS(puertoActual);
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
				ServidorUDP nuevoUDP  =  new ServidorUDP();
				System.out.println("Iniciando FIN");
				
				for (Paciente iterP : pacientesGlobales)
				{
					hiloINS nuevoINS = new hiloINS(puertoActual);
					miHilosINS.add(nuevoINS);
					nuevoINS.start();
					puertoActual++;
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
			/*for (Paciente iterP : pacientesGlobales)
			{
				hiloPacienteRespondeIPS nuevoPaciente = new hiloPacienteRespondeIPS(iterP, puertoActual);
				misHilosPaciente.add(nuevoPaciente);
				nuevoPaciente.start();
				puertoActual++;
			}*/
		}
	}
	public static List<Paciente> getPacientesGlobales()
	{
		return pacientesGlobales;
	}

}
