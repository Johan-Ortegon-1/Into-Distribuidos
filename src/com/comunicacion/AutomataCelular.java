package com.comunicacion;

public class AutomataCelular extends Thread {
	
	private Agente myAgente;
	private int tiempo ; 
	private boolean variacion ; 
	private boolean oscilacion  ; 
	
	
	
	public AutomataCelular(Agente myAgente) {
		super();
		this.myAgente = myAgente;
		this.tiempo = 0;
		this.variacion = false;
		this.oscilacion = false;
	}

	@Override
	public void run() {
//		if(myAgente.getMiPais().getNombre().equals("Colombia")) {
//			myAgente.getMiPais().setNombre("Polombia");
//		}
		//esperarTiempo(10);
		while(true) {
			if(myAgente.getMiPais().isInfectado()) {
				contagioPais();
			}else {
				boolean infectado = preguntarConexiones();
				myAgente.getMiPais().setInfectado(infectado);
				esperarTiempo(4);
			}
//			System.out.print("Infectados: ");
//			System.out.println(myAgente.getMiPais().getInfectados());
//			System.out.print("Poblacion : ");
//			System.out.println(myAgente.getMiPais().getPoblacionTotal());
//			System.out.println("-------------------------------------");
//			System.out.print("TIEMPO ");
//			System.out.println(tiempo );
			esperarTiempo(10);
//			if(oscilacion) {
//				esperarTiempo(10);
//				oscilacion = false;
//			}
			
		}
		
		
	}
	
	public boolean preguntarConexiones(){
		boolean decision;
		String vecino, miNombre;
//		System.out.println("MIRAAAA *************************************************************");
//		System.out.print(myAgente.getMiPais().getNombre());
//		System.out.println(myAgente.getConexionesPais().size());
		int totalVecinos = myAgente.getConexionesPais().size();
		long totalPoblacion = 0;
		long infectados = 0;
		
		miNombre = myAgente.getMiPais().getNombre();
		
		for(int i = 0; i<totalVecinos; i++) {
			infectados = infectados+myAgente.getConexionesPais().get(i).getInfectados();
			//System.out.println("-------------------------- PUES ENTRO A VECINOS ");
			totalPoblacion = totalPoblacion+myAgente.getConexionesPais().get(i).getPoblacionTotal();
			// Preguntar al Broker, que nos devuelve informacion del contagio del Vecino cantidad personas contagiadas 
			// y poblacion de ese vecino 
		}

		// Condicional para saber si mi pais esta infectado 
		//System.out.println("CANTIDAD DE INFECTADOS EN MIS VECINOS :"+infectados);
		if(infectados >= (totalPoblacion * 0.0001) && totalVecinos >= 1 ) {
			decision = true;
			//System.out.println("TRUE");
		}else {
			decision = false;
			//System.out.println("FALSE");
		}
		oscilacion = true;
		
		return decision;
	}

	
	public void contagioPais() {
		long infectados = 0;
		long noVulnerablesIn = 0;
		double vulnerablesIn = 0;
		long muertes = 0;
		
		if(tiempo == 0) {
			infectados = 10;
			myAgente.getMiPais().setInfectados(infectados);
		}
		if(tiempo >= 1) {
			infectados = myAgente.getMiPais().getInfectados();
			long poblacion = myAgente.getMiPais().getPoblacionTotal();
			
			if(infectados>=(poblacion*0.00001)) {
				double aislamiento = myAgente.getMiPais().getPorcenAislamiento();
				if(aislamiento>0.7 &&  aislamiento<0.8) {
					aislamiento = aislamiento+0.05;
				}
				if(aislamiento>0.5 && aislamiento<0.7) {
					aislamiento = aislamiento+0.12;
				}
				if(aislamiento>0.2 && aislamiento<0.5) {
					aislamiento = aislamiento+0.24;
				}
				
//				double tasa = 0;
//				tasa = (1-aislamiento)*myAgente.getCovid19().getTasaTransmision();
//				System.out.print(myAgente.getCovid19().getTasaTransmision());
//				System.out.print("MTRANSMISION: ");
//				System.out.print(tasa);
//				myAgente.getCovid19().setTasaTransmision(tasa);
//				myAgente.getMiPais().setPorcenAislamiento(aislamiento);
				
				if(this.variacion == false) {
					double tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaTransmision();
//					System.out.print(myAgente.getCovid19().getTasaTransmision());
//					System.out.print("MTRANSMISION: ");
//					System.out.print(tasa);
					myAgente.getCovid19().setTasaTransmision(tasa);
					myAgente.getMiPais().setPorcenAislamiento(aislamiento);
					//System.out.print("ENTRO EN EL PAIS ");
					
					
					
					tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaMortalidadNoVul();
//					System.out.print(myAgente.getCovid19().getTasaMortalidadNoVul());
//					System.out.print("NO VULNERABLES: ");
//					System.out.println(tasa);
					
					myAgente.getCovid19().setTasaMortalidadNoVul(tasa);
					tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaMortalidadVul();
//					System.out.print(myAgente.getCovid19().getTasaMortalidadVul());
//					System.out.print("VULNERABLES :");
//					System.out.println(tasa);
					myAgente.getCovid19().setTasaMortalidadVul(tasa);
					this.variacion = true;
					

				}

			}
			
			
			//infectados = myAgente.getMiPais().getInfectados();
			infectados = infectados + (long)(infectados * myAgente.getCovid19().getTasaTransmision());
			double porcentaje = myAgente.getMiPais().getPorcenPoblacionVulne();
			vulnerablesIn = infectados * porcentaje;
			noVulnerablesIn = (long)infectados - (long)vulnerablesIn;
			
			muertes = (long)(vulnerablesIn*myAgente.getCovid19().getTasaMortalidadVul());
//			System.out.println("Muertes 1");
//			System.out.println(muertes);
			muertes = muertes + (long)(noVulnerablesIn*myAgente.getCovid19().getTasaMortalidadNoVul()) ;
			
			poblacion = poblacion - muertes;
			infectados = infectados-muertes;
//			System.out.println("Poblacion Adentro");
//			System.out.println(poblacion);
//			System.out.print("Muertes: ");
//			System.out.println((long)muertes);
			if(poblacion <= 0 ) {
//				System.out.println("POBLACION EXTERMINADA");
//				System.out.println(myAgente.getMiPais().getNombre());
				poblacion = 0;
				infectados = 0;
				
			}
			myAgente.getMiPais().setPoblacionTotal(poblacion);
			
			myAgente.getMiPais().setInfectados(infectados);			
		}
		
		tiempo ++;
	}
	
	public void esperarTiempo(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public Agente getMyAgente() {
		return myAgente;
	}

	public void setMyAgente(Agente myAgente) {
		this.myAgente = myAgente;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	
	

}
