package com.controladores;
import java.io.IOException;
import java.util.Scanner;
import com.persistencia.*;
import com.comunicacion.*;
public class Main
{
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		int tipoDeInicio = 0;
		ManejadorDeArchivo.leerArchivo("archivo/archivo.txt");
		System.out.println("Como descea iniciar esta maquina: " + 
				" 1. Cliente -- 2. Servidor");
		tipoDeInicio = reader.nextInt();
		if(tipoDeInicio == 1)
		{
			try
			{
				Cliente.testSerVidor();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(tipoDeInicio == 2)
		{
			try
			{
				Servidor.testSerVidor();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
