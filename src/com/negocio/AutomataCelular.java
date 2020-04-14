package com.negocio;

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

		while(true) {
			if(myAgente.getMiPais().isInfectado()) {
				contagioPais();
			}else {
				boolean infectado = preguntarConexiones();
				myAgente.getMiPais().setInfectado(infectado);
				esperarTiempo(4);
			}
			esperarTiempo(10);
			
		}
		
		
	}
	
	public boolean preguntarConexiones(){
		boolean decision;
		String  miNombre;
		int totalVecinos = myAgente.getConexionesPais().size();
		long totalPoblacion = 0;
		long infectados = 0;
		
		miNombre = myAgente.getMiPais().getNombre();
		
		for(int i = 0; i<totalVecinos; i++) {
			infectados = infectados+myAgente.getConexionesPais().get(i).getInfectados();
			totalPoblacion = totalPoblacion+myAgente.getConexionesPais().get(i).getPoblacionTotal();
		}

		// Condicional para saber si mi pais esta infectado 
		if(infectados >= (totalPoblacion * 0.0001) && totalVecinos >= 1 ) {
			decision = true;
		}else {
			decision = false;
		}
		
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
				
				
				if(this.variacion == false) {
					double tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaTransmision();
					myAgente.getCovid19().setTasaTransmision(tasa);
					myAgente.getMiPais().setPorcenAislamiento(aislamiento);
					
					
					
					tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaMortalidadNoVul();
					
					myAgente.getCovid19().setTasaMortalidadNoVul(tasa);
					tasa = 0;
					tasa = (1-aislamiento)*myAgente.getCovid19().getTasaMortalidadVul();
					myAgente.getCovid19().setTasaMortalidadVul(tasa);
					this.variacion = true;
					

				}

			}
			

			infectados = infectados + (long)(infectados * myAgente.getCovid19().getTasaTransmision());
			double porcentaje = myAgente.getMiPais().getPorcenPoblacionVulne();
			vulnerablesIn = infectados * porcentaje;
			noVulnerablesIn = (long)infectados - (long)vulnerablesIn;
			
			muertes = (long)(vulnerablesIn*myAgente.getCovid19().getTasaMortalidadVul());
			muertes = muertes + (long)(noVulnerablesIn*myAgente.getCovid19().getTasaMortalidadNoVul()) ;
			
			poblacion = poblacion - muertes;
			infectados = infectados-muertes;
			if(poblacion <= 0 ) {
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
