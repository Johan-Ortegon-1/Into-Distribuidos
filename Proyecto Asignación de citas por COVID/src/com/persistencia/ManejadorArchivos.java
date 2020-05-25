package com.persistencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.negocio.Eps;
import com.negocio.Paciente;

public class ManejadorArchivos
{
	public static void leerArchivo(String archivo, List<Paciente> pacientesGlobales, List<Eps> epsGlobales)
	{
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

			boolean parteEPS = false;
			int k = 0;
			while((cadena = b.readLine())!=null) 
			{
				String[] words = cadena.split("\\-");
				//Asignacion de valores
				//System.out.println("PRIMERA PALABRA "+ words[0]);
				if (words[0].equals("PACIENTES")) {
					parteEPS = false;
				}

				if (words[0].equals("EPS")) {
					parteEPS = true;
				}


				//System.out.println(parteEPS+" "+words.length);
				if ((!parteEPS) && words.length>1 ) {
					//System.out.println("Pacientes " + k+ " "+ words[1]);
					patologiaAdicional = false;
					sintomas = new ArrayList<Boolean>();
					fecha = words[0];
					nombre = words[1];
					documento = Long.parseLong(words[2]);
					edad = Integer.parseInt(words[3]);
					eps = words[4];	   
					for(int i = 5; i <= 12; i++){
						if(words[i].equals("0")){
							sintomas.add(false);
						}else {
							sintomas.add(true);
						}
					}
					//System.out.println(words.length);
					if(words.length > 13){
						patologiaAdicional = true;
					}
					pacientesGlobales.add(new Paciente(nombre,documento,edad,eps,sintomas,patologiaAdicional));
				}

				if (parteEPS && words.length>1) {
					//System.out.println("EPS" + k);
					eps = words[0];
					Eps nuevaEps = new Eps();
					nuevaEps.setNombreEps(eps);
					for (int i = 1; i < words.length; i++) {
						String[] afiliado = words[i].split("\\|");
						documento = Long.parseLong(afiliado[0]);
						int cubrimiento = Integer.parseInt(afiliado[1]);
						Paciente nuevoPaciente = new Paciente();
						nuevoPaciente.setCubrimiento(cubrimiento);
						nuevoPaciente.setDocumento(documento);
						nuevaEps.getPacientesAfiliados().add(nuevoPaciente);
					}
					epsGlobales.add(nuevaEps);
				}
				k++;
			}//if

			b.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}

