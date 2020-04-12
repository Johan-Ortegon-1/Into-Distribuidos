package com.comunicacion;
import java.util.Random;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.negocio.ModeloVirus;
import com.negocio.Pais;

public class BalanceadoreCarga
{
	/*
	 * https://stackoverflow.com/questions/10131377/socket-programming-multiple-
	 * client-to-one-server
	 */
	private Thread balanceadorAuto;
	private Thread serverAuto;
	private final static int puertoServidor = 4445;
	private int numMaquinasSistema = 0;
	private List<hiloBalanceador> misHilos = new ArrayList<hiloBalanceador>();
	private List<Pais> todosLosPaises = new ArrayList<Pais>();
	private ModeloVirus covid19 = new ModeloVirus();
	public void inciarBalanceador(List<Pais> pPaises, int numMaquinas, ModeloVirus pCovid19)
	{
		int numMaqActual = 0;
		Socket s = null;
		ServerSocket ss2 = null;
		System.out.println("Server Listening......");
		numMaquinasSistema = numMaquinas;
		this.todosLosPaises = pPaises;
		this.covid19 = pCovid19;
		try
		{
			//Apertura del servidor
			ss2 = new ServerSocket(puertoServidor); 

		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Server error");
		}
		
		/*Repartir paises:*/
		Random random = new Random();//Para la distribucion de los paises
		int numPaises = pPaises.size();
		int aux = numPaises;
		int randomInt = 0;
		List<Integer> paisesXMaqu = new ArrayList<Integer>();
		int paisesRandom[] = new int[numPaises];
		for (int i = 0; i < numMaquinas; i++)
		{
			if(i == numMaquinas-1)
			{
				paisesXMaqu.add(aux);
				break;
			}
			randomInt = random.nextInt(numPaises);
			if(randomInt == 0)
				randomInt = 1;
			paisesXMaqu.add(randomInt);
			aux = aux - randomInt;	
		}
		paisesRandom[0] = (int)(Math.random()*(numPaises)); 
		for(int i = 1; i < numPaises; i++)
		{
			paisesRandom[i] = (int)(Math.random()*(numPaises));
			for (int j = 0; j < i; j++)
			{
				if(paisesRandom[i] == paisesRandom[j])
				{
					i--;
				}
			}
		}
//		for (int i = 0; i < paisesRandom.length; i++) 
//		{
//			System.out.println(paisesRandom[i] + " ");
//		}
		int indexIni = 0, indexFin = 0;
		while (numMaqActual < numMaquinas)/*Esperar tantas conexiones como haya especificado el usuario*/
		{
			try
			{
				int cantPaisesMAct = paisesXMaqu.get(numMaqActual);
				indexFin = indexIni + cantPaisesMAct;
				List<Integer> paisesCorrespondientes = new ArrayList<Integer>();
				for(int i = indexIni;i < indexFin;i++)
				{
					paisesCorrespondientes.add(paisesRandom[i]);
				}
				//System.out.println("Paises que el corresponden a la instancia: " + numMaqActual + " " + paisesCorrespondientes.toString());
				s = ss2.accept();
				System.out.println("Conexion establecida");
				hiloBalanceador st = new hiloBalanceador(s, paisesCorrespondientes);
				misHilos.add(st);
				st.start();
				numMaqActual++;
				indexIni = indexFin;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Error en la conexion");
			}
		}
		/*Empezar a analizar carga de ahora en adelante*/
		this.analizarCarga();
	}

	public void analizarCarga()
	{
		long sumTotal = 0, poblacionAct = 0;
		float promedio = 0, porcentajeCagaAct = 0;
		/* Solicitar el reporte de los monitores de forma permanente */
		while (true)
		{
			try
			{
				Thread.sleep(5000);
				for (int i = 0; i < misHilos.size(); i++)//Actualiza los valores de carga en cada hilo
				{
					if(misHilos.get(i).darInformePeriodico() == false)
					{
						System.out.println("Cliente desconectado - Matando su hilo");
						this.arreglarDesconexion();
						misHilos.get(i).matarConexiones();
						misHilos.remove(i);
						if(misHilos.size() == 0)
						{
							System.out.println("Ya no hay maquinas en el sistema");
							break;
						}
					}
				}
				for (int i = 0; i < misHilos.size(); i++)
				{
					sumTotal = sumTotal + misHilos.get(i).getCargaDeMaquina();
				}
				promedio = sumTotal/numMaquinasSistema;
				System.out.println("Promedio de las poblaciones manejadas por cada maquina: " + promedio);
				Collections.sort(misHilos, new comparadorCargasHilos());
				System.out.println("Hilos y sus cargas depues de ordenamiento");
				for (int i = 0; i < misHilos.size(); i++)
				{
					/*Calculo del procentaje de participacion en el manejo de poblacion por hilo*/
					porcentajeCagaAct = (misHilos.get(i).getCargaDeMaquina()*100)/sumTotal;
					misHilos.get(i).setPorcetajeCarga(porcentajeCagaAct);
					System.out.println(misHilos.get(i).toString());
				}
				//Comparaciones entre el mas cargado y el menos cargado despues del ordenamiento 
				int indexFin = 0;
				float aux1 = 0, aux2 = 0;
				for (int i = 0; i < misHilos.size()/2; i++) 
				{
					indexFin = misHilos.size()-1-i;
					aux1 = misHilos.get(i).getPorcetajeCarga();
					aux2 = misHilos.get(indexFin).getPorcetajeCarga();
					aux1 = aux1-20;//Supera en 20% 
					//System.out.println("Aux1: " + aux1 + " Aux2: " + aux2);
					if(aux1 >= aux2)
					{
						System.out.println("Se ha encontrado un desbalance entre!!");
						System.out.println("Sobre cargado: " + misHilos.get(i).toString());
						System.out.print(" Manejando un porcentaje de: ");
						System.out.printf("%.3f", misHilos.get(i).getPorcetajeCarga());
						System.out.println("Ocioso: " + misHilos.get(indexFin).toString());
						System.out.print(" Manejando un porcentaje de: ");
						System.out.printf("%.3f", misHilos.get(indexFin).getPorcetajeCarga());
						//Llamado para arreglar el desbalance:
						Agente agenteSustraido = new Agente();
						System.out.println("EJECUTANDO BALANCEO");
						agenteSustraido = misHilos.get(i).ordenarSustraccionAgente(this.todosLosPaises, this.covid19);//Sustraer agente del hilo
						misHilos.get(indexFin).ordenarAdicionAgente(agenteSustraido);//Adicionar el agente sustraido a otra maquina
						System.out.println("FIN DEL BALANCEO");
					}
				}
				aux1 = 0;
				aux2 = 0;
				sumTotal = 0;
				
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void arreglarDesconexion()
	{
		
	}

	public List<Pais> getTodosLosPaises() {
		return todosLosPaises;
	}

	public void setTodosLosPaises(List<Pais> todosLosPaises) {
		this.todosLosPaises = todosLosPaises;
	}

	public ModeloVirus getCovid19() {
		return covid19;
	}

	public void setCovid19(ModeloVirus covid19) {
		this.covid19 = covid19;
	}

	
}
