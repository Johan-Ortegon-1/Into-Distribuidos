package com.controladores;

import java.util.ArrayList;
import java.util.List;

import com.negocio.Eps;
import com.negocio.Paciente;
import com.persistencia.ManejadorArchivos;

public class Main
{
	static List<Paciente> pacientesGlobales = new ArrayList<Paciente>();
	static List<Eps> epsGlobales = new ArrayList<Eps>();
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		pacientesGlobales = ManejadorArchivos.leerArchivo("Archivo/pacientes.txt");
		for(int i = 0; i< pacientesGlobales.size(); i++)
    	{
			String nombreEps = pacientesGlobales.get(i).getEps();
			if(buscarEsp(nombreEps) == false)
			{
				
			}
    		System.out.println(pacientesGlobales.get(i).getNombre() + " + ");
    		
    	}
		
		// COMENTARIO DE ALDEMAR
		//Der komentar
	}
	public static boolean buscarEsp(String nombre)
	{
		for(Eps iter : epsGlobales)
		{
			if(iter.equals(nombre))
			{
				return true;
			}
		}
		return false;
	}
}
