package com.comunicacion;

public class AutomataCelular extends Thread {
	
	private Agente myAgente;
	private int tiempo ; 
	
	
	
	public AutomataCelular(Agente myAgente) {
		super();
		this.myAgente = myAgente;
		this.tiempo = 0;
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
			System.out.println(myAgente.getMiPais().getInfectados());
			esperarTiempo(10);
			
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
			//if(infectados>poblacion*0.00001) {
			if(infectados>50) {
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
				
				double tasa = (1-aislamiento)*myAgente.getCovid19().getTasaTransmision();
				myAgente.getCovid19().setTasaTransmision(tasa);
				myAgente.getMiPais().setPorcenAislamiento(aislamiento);
				
			}
			
			
			infectados = myAgente.getMiPais().getInfectados();
			infectados = infectados + (infectados * myAgente.getCovid19().getTasaTransmision());
			double porcentaje = myAgente.getMiPais().getPorcenPoblacionVulne();
			vulnerablesIn = infectados * porcentaje;
			noVulnerablesIn = (long)infectados - (long)vulnerablesIn;
			
			muertes = (0.19*myAgente.getCovid19().getTasaMortalidadVul())/porcentaje;
			muertes = muertes + (0.05*myAgente.getCovid19().getTasaMortalidadNoVul() )/1-porcentaje;
			
			poblacion = poblacion - (long)muertes;
			if(poblacion <= 0 ) {
				System.out.println("POBLACION EXTERMINADA");
				System.out.println(myAgente.getMiPais().getNombre());
			}
			myAgente.getMiPais().setPoblacionTotal(poblacion);
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
