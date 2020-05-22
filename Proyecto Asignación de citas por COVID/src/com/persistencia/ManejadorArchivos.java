package com.persistencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.negocio.Paciente;

public class ManejadorArchivos
{
	public static List<Paciente> leerArchivo(String archivo)
	{
		List<Paciente> retorno = new ArrayList<Paciente>();
		try
		{
			String cadena;
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        String fecha = "";
	        String nombre = "";
	        long documento = 0;
	        int edad = 0;
	        String eps = "";
	        List<Boolean> sintomas = new ArrayList<Boolean>();
	        boolean patologiaAdicional = false;
	        
	        
	        while((cadena = b.readLine())!=null) 
	        {
	        	String[] words = cadena.split("\\-");
	        	/*System.out.println("Paciente nuevo: ");
	        	for(int i = 0; i< words.length; i++)
	        	{
	        		System.out.println(words[i] + "+");
	        	}
	        	System.out.println();*/
	        	//Asignacion de valores
	        	fecha = words[0];
	        	nombre = words[1];
	        	documento = Long.parseLong(words[2]);
	        	edad = Integer.parseInt(words[3]);
	        	eps = words[4];	   
	        	for(int i = 5; i < 12; i++)
	        	{
	        		if(words[i].equals("0"))
	        		{
	        			sintomas.add(false);
	        		}
	        		else
	        			sintomas.add(true);
	        	}
	        	if(words.length > 13)
	        	{
	        		patologiaAdicional = true;
	        	}
	        	retorno.add(new Paciente(nombre,documento,edad,eps,sintomas,patologiaAdicional));
	        }
	        b.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return retorno;
	}
}
