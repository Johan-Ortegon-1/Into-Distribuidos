package com.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManejadorDeArchivo
{
	public static void leerArchivo(String archivo)
	{
		try
		{
			String cadena;
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        while((cadena = b.readLine())!=null) {
	            System.out.println(cadena);
	        }
	        b.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
