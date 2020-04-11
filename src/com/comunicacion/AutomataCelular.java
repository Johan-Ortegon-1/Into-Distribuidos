package com.comunicacion;

public class AutomataCelular extends Thread {
	
	private Agente myAgente;
	private int tiempo ; 
	private boolean variacion ; 
	
	
	
	public AutomataCelular(Agente myAgente) {
		super();
		this.myAgente = myAgente;
		this.tiempo = 0;
		this.variacion = false;
	}

	@Override
	public void run() {
//		if(myAgente.getMiPais().getNombre().equals("Colombia")) {
//			myAgente.getMiPais().setNombre("Polombia");
//		}
		while(true) {
			if(myAgente.getMiPais().isInfectado()) {
				contagioPais();
			}else {
				boolean infectado = preguntarConexiones();
				myAgente.getMiPais().setInfectado(infectado);
			}
//			System.out.print("Infectados: ");
//			System.out.println(myAgente.getMiPais().getInfectados());
//			System.out.print("Poblacion : ");
//			System.out.println(myAgente.getMiPais().getPoblacionTotal());
//			System.out.println("-------------------------------------");
//			System.out.print("TIEMPO ");
//			System.out.println(tiempo );
			esperarTiempo(2);
			
		}
		
		
	}
	
	public boolean preguntarConexiones(){
		boolean decision;
		String vecino, miNombre;
		int totalVecinos = myAgente.getConexiones().size();
		int totalPoblacion = 0;
		int infectados = 0;
		
		miNombre = myAgente.getMiPais().getNombre();
		
		for(int i = 0; i<totalVecinos; i++) {
			if(myAgente.getConexiones().get(i).getPaisA().equals(miNombre)) {
				vecino = myAgente.getConexiones().get(i).getPaisB();
			}else {
				vecino = myAgente.getConexiones().get(i).getPaisA();
			}
			
			// Preguntar al Broker, que nos devuelve informacion del contagio del Vecino cantidad personas contagiadas 
			// y poblacion de ese vecino 
		}

		// Condicional para saber si mi pais esta infectado 
		if(infectados >= (totalPoblacion * 0.33)) {
			decision = true;
		}else {
			decision = false;
		}
		
		return decision;
	}

	
	public void contagioPais() {
		double infectados = 0;
		long noVulnerablesIn = 0;
		double vulnerablesIn = 0;
		double muertes = 0;
		
		if(tiempo == 0) {
			infectados = 10;
			myAgente.getMiPais().setInfectados((long)infectados);
		}
		if(tiempo > 0) {
			infectados = myAgente.getMiPais().getInfectados();
			long poblacion = myAgente.getMiPais().getPoblacionTotal();
			if(infectados>poblacion*0.00001) {
				double aislamiento = myAgente.getMiPais().getPorcenAislamiento();
				if(aislamiento>0.7) {
					aislamiento = aislamiento+0.05;
				}
				if(aislamiento>0.5 && aislamiento<0.7) {
					aislamiento = aislamiento+0.1;
				}
				if(aislamiento>0.2 && aislamiento<0.5) {
					aislamiento = aislamiento+0.16;
				}
				
				if(variacion == false) {
					double tasa = (1-aislamiento)*myAgente.getCovid19().getTasaTransmision();
//					System.out.print(myAgente.getCovid19().getTasaTransmision());
//					System.out.print("MTRANSMISION: ");
//					System.out.println(tasa);
					myAgente.getCovid19().setTasaTransmision(tasa);
					myAgente.getMiPais().setPorcenAislamiento(aislamiento);
					
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
					variacion = true;
				}

			}
			
			
			infectados = myAgente.getMiPais().getInfectados();
			infectados = infectados + (infectados * myAgente.getCovid19().getTasaTransmision());
			double porcentaje = myAgente.getMiPais().getPorcenPoblacionVulne();
			vulnerablesIn = infectados * porcentaje;
			noVulnerablesIn = (long)infectados - (long)vulnerablesIn;
			
			muertes = vulnerablesIn*myAgente.getCovid19().getTasaMortalidadVul();
//			System.out.println("Muertes 1");
//			System.out.println(muertes);
			muertes = muertes + (noVulnerablesIn*myAgente.getCovid19().getTasaMortalidadNoVul()) ;
			
			poblacion = poblacion - (long)muertes;
//			System.out.println("Poblacion Adentro");
//			System.out.println(poblacion);
//			System.out.print("Muertes: ");
//			System.out.println((long)muertes);
			if(poblacion <= 0 ) {
				System.out.println("POBLACION EXTERMINADA");
				System.out.println(myAgente.getMiPais().getNombre());
			}
			myAgente.getMiPais().setPoblacionTotal(poblacion);
			infectados = infectados-muertes;
			myAgente.getMiPais().setInfectados((long)infectados);			
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
