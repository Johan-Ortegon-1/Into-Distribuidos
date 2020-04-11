package com.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.comunicacion.Agente;
import com.comunicacion.llamadaHilosAgentes;
import com.negocio.ConexionPaises;
import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class ManejadorDeArchivo
{
	public static void leerArchivo(String archivo, llamadaHilosAgentes mysHilos )
	{
		try
		{
			String cadena;
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        int cont = 0;
	        int contPaises = -1;
	        boolean exitosa = false;
	        boolean exitoPais = false;
	        boolean exitoConex = false;
	        ModeloVirus covid19 = new ModeloVirus();
	        Pais temp = new Pais();
	        List<Pais> misPaises = new Vector<Pais>();
	        ConexionPaises conexion = new ConexionPaises();
	        List<ConexionPaises> conexiones = new Vector<ConexionPaises>();
	        
	        while((cadena = b.readLine())!=null) {
	            if(cadena.equals("*VIRUS*") && cont == 0) {
	            	exitosa = true;
	            }else {
	            	if(exitosa == false ) {
	            		System.out.println("Error de Lectura");
	            	}
	            }
	            
	            if(exitosa && cont == 1) {
	            	covid19.setTasaTransmision(Double.parseDouble(cadena));
	            }
	            if(exitosa && cont == 2) {
	            	covid19.setTasaMortalidadNoVul(Double.parseDouble(cadena));
	            }
	            if(exitosa && cont == 3) {
	            	covid19.setTasaMortalidadVul(Double.parseDouble(cadena));
	            }
	            
	            if(cadena.equals("*PAISES*") && cont == 4) {
	            	exitoPais = true;
	            }
	            
	            if(cadena.equals("*CONEXION-PAIS-PAIS*") ) {
	            	exitoConex = true;
	            	exitoPais = false;
	            }
	            if(exitoPais) {
	            	
	            	if(contPaises == 0) {
		            	temp.setId(Integer.parseInt(cadena));
		            }
	            	if(contPaises == 1) {
	            		temp.setNombre(cadena);
		            }
	            	if(contPaises == 2) {
		            	if(cadena.equals("No infectado")) {
	            			temp.setInfectado(false);
	            		}
		            	if(cadena.equals("Infectado")) {
	            			temp.setInfectado(true);
	            		}
		            }
		            if(contPaises == 3) {
		            	temp.setPoblacionTotal(Long.parseLong(cadena));
		            }
		            if(contPaises == 4) {
		            	temp.setPorcenPoblacionVulne(Double.parseDouble(cadena));
		            }
		            if(contPaises == 5) {
		            	temp.setPorcenAislamiento(Double.parseDouble(cadena));
		            }
		            
		            if(contPaises == 5) {
		            	contPaises = 0;
		            	misPaises.add(temp);
		            	temp = new Pais();
		            }else {
		            	contPaises++;
		            }
		            
	            }
	            
	            if(exitoConex ) {
	            	String[] words = cadena.split("\\-");
            	    
            	    
            	    if(words[0].equals("*CONEXION")) {
            	    	
            	    }else {
            	    	conexion.setMedioTransporte(words[0].charAt(0));
            	    	conexion.setPaisA(words[1]);
            	    	conexion.setPaisB(words[2]);
            	    	
            	    	conexiones.add(conexion);
            	    	conexion = new ConexionPaises();
//            	    	System.out.println(Arrays.toString(words));
            	    }
            	    
	            }
	           
	            cont ++;
	        }
	        
	        mysHilos.setMisPaises(misPaises);
	        mysHilos.setConexiones(conexiones);
	        mysHilos.setCovid19(covid19);
	        
//	        for(int i = 0; i< misPaises.size();i++ ) {
//            	System.out.println(misPaises.get(i).getNombre());
//            }
//	        for(int i = 0; i< conexiones.size();i++ ) {
//            	System.out.println(conexiones.get(i).getMedioTransporte());
//            }
        	
	        b.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
