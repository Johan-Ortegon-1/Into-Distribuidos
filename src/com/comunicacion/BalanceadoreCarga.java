package com.comunicacion;
import java.util.Random;
import java.util.Vector;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.negocio.Agente;
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
	private final static int puertoServidorBroker = 4446;
	private int numMaquinasSistema = 0;
	private List<hiloBalanceador> misHilos = new ArrayList<hiloBalanceador>(); // Hilos Monitor
	private List<hiloBroker> misBroker = new ArrayList<hiloBroker>(); // Hilos Broker
	private List<Pais> mundo =  new Vector<Pais>();
	private boolean entro = false;
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
		entro = true;
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
		double sumTotal = 0, poblacionAct = 0;
		double promedio = 0, porcentajeCagaAct = 0;
		boolean notificacion = true;
		/* Solicitar el reporte de los monitores de forma permanente */
		while (this.numMaquinasSistema > 0)
		{
			System.out.println("CANTIDAD DE MAQUINAS EN EL SISTEMA: " + this.numMaquinasSistema);
			try
			{
				//Parte del informe
				Thread.sleep(5000);
				for (int i = 0; i < misHilos.size(); i++)//Actualiza los valores de carga en cada hilo
				{
					if(misHilos.get(i).darInformePeriodico() == false)//Hilo desconectado
					{
						System.out.println("Cliente desconectado - Matando su hilo");
						this.arreglarDesconexion(misHilos.get(i));
						misHilos.get(i).matarConexiones();
						misHilos.remove(i);
						this.numMaquinasSistema = this.numMaquinasSistema-1; 
						if(misHilos.size() == 0)
						{
							System.out.println("Ya no hay maquinas en el sistema");
							break;
						}
					}
					else if(numMaquinasSistema == 1)
					{
						System.out.println("Unica maquina corriendo");
						System.out.println("Info: " + misHilos.get(i).toString());
						System.out.print(" Manejando un porcentaje de: ");
						System.out.printf("%.3f", 100.000);
						System.out.println(" %");
					}
				}
				//Parte de analisis de carga
				if(this.numMaquinasSistema > 1)
				{
					for (int i = 0; i < misHilos.size(); i++)
					{
						sumTotal = sumTotal + misHilos.get(i).getCargaDeMaquina();
					}
					promedio = sumTotal/numMaquinasSistema;
					System.out.println("Promedio de las poblaciones manejadas por cada maquina: " + promedio);
					Collections.sort(misHilos, new comparadorCargasHilos());
					System.out.println("PCs y sus cargas depues de ordenamiento");
					for (int i = 0; i < misHilos.size(); i++)
					{
						/*Calculo del procentaje de participacion en el manejo de poblacion por hilo*/
						porcentajeCagaAct = (misHilos.get(i).getCargaDeMaquina()*100)/sumTotal;
						misHilos.get(i).setPorcetajeCarga(porcentajeCagaAct);
						System.out.println(misHilos.get(i).toString());
					}
					//Comparaciones entre el mas cargado y el menos cargado despues del ordenamiento 
					int indexFin = 0;
					double aux1 = 0, aux2 = 0;
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
							System.out.println(" %");
							System.out.println("Ocioso: " + misHilos.get(indexFin).toString());
							System.out.print(" Manejando un porcentaje de: ");
							System.out.printf("%.3f", misHilos.get(indexFin).getPorcetajeCarga());
							System.out.println(" %");
							//Llamado para arreglar el desbalance:
							Agente agenteSustraido = new Agente();
							System.out.println("EJECUTANDO BALANCEO");
							if(misHilos.get(i).isHiloActivo())
							{
								agenteSustraido = misHilos.get(i).ordenarSustraccionAgente(this.todosLosPaises, this.covid19);//Sustraer agente del hilo
								misHilos.get(indexFin).ordenarAdicionAgente(agenteSustraido);//Adicionar el agente sustraido a otra maquina
								System.out.println("FIN DEL BALANCEO");
							}
							else
							{
								System.out.println("HILO INACTIVO, BALANCEO CANCELADO");
							}
						}
						else
						{
							System.out.println("El Sistema esta balanceado: ");
							System.out.println("PC " + i + ": " + misHilos.get(i).toString());
							System.out.print(" Manejando un porcentaje de: ");
							System.out.printf("%.3f", misHilos.get(i).getPorcetajeCarga());
							System.out.println(" %");
							System.out.println("PC " + indexFin + ": " + misHilos.get(indexFin).toString());
							System.out.print(" Manejando un porcentaje de: ");
							System.out.printf("%.3f", misHilos.get(indexFin).getPorcetajeCarga());
							System.out.println(" %");
						}
							
					}
					aux1 = 0;
					aux2 = 0;
					sumTotal = 0;
				}
				else if(this.numMaquinasSistema == 1 && notificacion)
				{
					System.out.println("Solo existe una maquina en el sistema -> Deteniendo balanceador!!!");
					notificacion = false;
				}
				
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("NO HAY MAQUINAS CONECTADAS, TERMINANDO PROGRAMA...");
	}

	public void arreglarDesconexion(hiloBalanceador hiloDesconectado)
	{
		List<Integer> paisesHuerfanos = hiloDesconectado.getPaises();
		System.out.println("Estos son los id de los paises que quedaron huerfanos: ");
		System.out.println(paisesHuerfanos.toString());
		System.out.println("Enviardo huerfanos a otra PC");
		for(int i = 0; i < misHilos.size();i++)
		{
			if(misHilos.get(i).isHiloActivo())
			{
				misHilos.get(i).ordenarApadrinamiento(paisesHuerfanos);
			}
		}		
	}
	
	public void iniciarBroker()
	{
		int numMaqActual = 0;
		Socket s = null;
		ServerSocket ss2 = null;
		
		try
		{
			//Apertura del servidor
			ss2 = new ServerSocket(puertoServidorBroker); 

		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Server error");
		}
		
		while(entro == false) {
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Server Listening Broker......");
		while (numMaqActual < numMaquinasSistema )/*Esperar tantas conexiones como haya especificado el usuario*/
		{
			try
			{
				s = ss2.accept();
				System.out.println("Conexion establecida");
				
				hiloBroker st = new hiloBroker(s);
				misBroker.add(st);
				st.start();
				numMaqActual++;
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Error en la conexion");
			}
		}
		
		this.actualizarPaises();
		
	}
	
	public void actualizarPaises() {

		while (true)
		{
			try
			{
				Thread.sleep(10000);
				for (int i = 0; i < misBroker.size(); i++)//Actualiza los valores de carga en cada hilo
				{
					if(misBroker.get(i).recibirPaises(mundo) == false)
					{
						System.out.println("Cliente desconectado - Matando su hilo");
						//this.arreglarDesconexion();
						misBroker.get(i).matarConexiones();
						misBroker.remove(i);
						if(misBroker.size() == 0)
						{
							System.out.println("Ya no hay maquinas en el sistema (Broker)");
							break;
						}
					}
				}
				for (int i = 0; i < misBroker.size(); i++) //Recorre hilos
				{
					List <Pais> copia =  new ArrayList<Pais>();
					copia = misBroker.get(i).getPaisesLocales();
					
					for(int k = 0; k < copia.size();k++) { // Recorre paises del hilo
						boolean encontro = false;
						for(int j = 0;j <mundo.size();j++) { //Recorre mundo
							if(copia.get(k).getNombre().equals(mundo.get(j).getNombre())) {
								encontro = true;
								mundo.get(j).setPoblacionTotal(copia.get(k).getPoblacionTotal());
								mundo.get(j).setInfectados(copia.get(k).getInfectados());
							}
						}
						if(encontro == false) {
							mundo.add(misBroker.get(i).getPaisesLocales().get(k));
						}
					}
					
					
				}
				
				// System.out.println("Promedio de las poblaciones manejadas por cada maquina: " + promedio);
				
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
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
