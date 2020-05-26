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
		ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		
		/*for (int i = 0; i < pacientesGlobales.size(); i++) {
		System.out.println(pacientesGlobales.get(i).getDocumento());
		for (int j = 0; j < pacientesGlobales.get(i).getSintomas().size(); j++) {
			System.out.println(pacientesGlobales.get(i).getSintomas().get(j));
		}
		}*/
		/*for (int i = 0; i < epsGlobales.size(); i++) {
		System.out.println(epsGlobales.get(i).getNombreEps());
		for (int j = 0; j < epsGlobales.get(i).getPacientesAfiliados().size(); j++) {
			System.out.println(epsGlobales.get(i).getPacientesAfiliados().get(j).getDocumento());
		}
		}*/

		Ins myInsPrueba = new Ins();
		for (int i = 0; i < pacientesGlobales.size(); i++) {
		myInsPrueba.evaluarPaciente(pacientesGlobales.get(i));
		System.out.println(pacientesGlobales.get(i).getDocumento()+" "+pacientesGlobales.get(i).getEvaluacion()+" "+pacientesGlobales.get(i).getPrioridad());
		}

		System.out.println("_____________________________");
		Ips myIpsPrueba = new Ips();
		myIpsPrueba.setEntidadesEPS(epsGlobales);
		myIpsPrueba.setEntidadIns(myInsPrueba);
		for (int i = 0; i < pacientesGlobales.size(); i++) {
		myIpsPrueba.asignarCitas(pacientesGlobales.get(i));
		}
		for (int i = 0; i < myIpsPrueba.getCitasProgramadas().size(); i++) {
		System.out.println(myIpsPrueba.getCitasProgramadas().get(i).getDocumento()+" "+myIpsPrueba.getCitasProgramadas().get(i).getEvaluacion());
		}
		
		//ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		
		/*int j = 0;
		ManejadorArchivos.leerArchivo("Archivo/pacientes.txt", pacientesGlobales, epsGlobales);
		myIps.setEntidadesEPS(epsGlobales);
		inciarEquipo();*/
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
			llamadaGestorTransacciones();
			/*Impresion citas programadas*/
			System.out.println("-*-*-*-*-*-Citas programadas IPS-*-*-*-*-*-");
			System.out.println("tam lista: " + myIps.getCitasProgramadas().size());
			for (int i = 0; i < myIps.getCitasProgramadas().size(); i++) 
			{
				System.out.println(myIps.getCitasProgramadas().get(i).getDocumento()+" "+myIps.getCitasProgramadas().get(i).getEvaluacion() + " Prioridad: " + myIps.getCitasProgramadas().get(i).getPrioridad());
				/*if(!myIps.getCitasProgramadas().get(i).getPrioridad().equals("No enfermo") && !myIps.getCitasProgramadas().get(i).getPrioridad().equals("Leve"))
				{
					System.out.println(myIps.getCitasProgramadas().get(i).getDocumento()+" "+myIps.getCitasProgramadas().get(i).getEvaluacion() + " Prioridad: " + myIps.getCitasProgramadas().get(i).getPrioridad());
				}*/
			}
			for(Eps tem : myIps.getEntidadesEPS())
			{
				System.out.println("PARA LA EPS: " + tem.getNombreEps() + " -*-*-*-*-*-*");
				for(Paciente temp2 : tem.getPacientesAfiliados())
				{
					if(temp2.getHistorial().size() > 0)
					{
						System.out.println(" Documento: " + temp2.getDocumento() + " Citas: " + temp2.getHistorial().get(0).getFecha());
					}
					else
					{
						System.out.println(" Documento: " + temp2.getDocumento() + " Citas: NINGUNA ASIGNADA");
					}
				}
			}
			System.out.println("");
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
				llamadaGestorTransacciones();
				int i = 0;
				System.out.println("---TOTAL CASOS REPORTADOS REGISTRADOS EN INS---");
				for(Paciente iter : myIns.getCasosReportados())
				{
					System.out.println("Caso: " + i + " " + iter.getDocumento() + " Con nombre: " + iter.getNombre() + 
					 "Con prioridad: " + iter.getPrioridad());
					i++;
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
	
	
	
	
	public static void llamadaGestorTransacciones()
	{
		try
		{
			Thread.sleep(15000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
