package com.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ips
{
	private List<Paciente> citasProgramadas;
	private List<Eps> entidadesEPS;
	private Ins entidadIns;
	private int maxCitasDia;

	public Ips() {
		citasProgramadas = new ArrayList<Paciente>();
		entidadesEPS = new ArrayList<Eps>();
		maxCitasDia = 5;
	}


	public Ips(List<Paciente> citasProgramadas, List<Eps> entidadesEPS, Ins entidadIns, int maxCitas) {
		super();
		this.citasProgramadas = citasProgramadas;
		this.entidadesEPS = entidadesEPS;
		this.entidadIns = entidadIns;
		this.maxCitasDia = maxCitas;
	}


	public List<Paciente> getCitasProgramadas() {
		return citasProgramadas;
	}


	public void setCitasProgramadas(List<Paciente> citasProgramadas) {
		this.citasProgramadas = citasProgramadas;
	}


	public List<Eps> getEntidadesEPS() {
		return entidadesEPS;
	}


	public void setEntidadesEPS(List<Eps> entidadesEPS) {
		this.entidadesEPS = entidadesEPS;
	}


	public Ins getEntidadIns() {
		return entidadIns;
	}


	public void setEntidadIns(Ins entidadIns) {
		this.entidadIns = entidadIns;
	}


	public int getMaxCitas() {
		return maxCitasDia;
	}


	public void setMaxCitas(int maxCitas) {
		this.maxCitasDia = maxCitas;
	}


	public boolean asignarCitas(Paciente paciente) {
		Paciente copia;
		boolean resp = false;
		String respuesta = " ";
		//System.out.println(entidadesEPS.size());
		for (int i = 0; i < entidadesEPS.size(); i++) {
			//System.out.println(entidadesEPS.get(i).getNombreEps()+" "+paciente.getEps());
			if (entidadesEPS.get(i).getNombreEps().equals(paciente.getEps())) {
				//System.out.println("entro?");
				if(entidadesEPS.get(i).pacientesQueAtiendo(paciente)) {
					//entidadIns.evaluarPaciente(paciente);
					if(entidadesEPS.get(i).darAutorizacion(paciente)) {
						//System.out.println("HAY UNA EPS PARA "+ paciente.getDocumento()+" "+entidadesEPS.get(i).getNombreEps()
								//+" "+paciente.getEvaluacion());
						resp = true;
						ordenarPrioridad(paciente);
					}else {
						//System.out.println("+++++++++++NO TIENE CUBRIMIENTO CON SU NIVEL DE GRAVEDAD  "+ paciente.getDocumento());
						respuesta = "(!!!) Error de asignacion: El PACIENTE "+ paciente.getNombre()+" CON DOCUMENTO "+paciente.getDocumento()+" NO TIENE CUBRIMIENTO CON SU NIVEL DE GRAVEDAD";
						System.out.println(respuesta);
					}
					
				}else {
					//Informar que el PACIENTE NO ESTA REGISTRADO EN LA EPS A LA QUE DICE PERTENECER
					//System.out.println("_______________NO ESTA AFILIADO A ESA EPS "+ paciente.getDocumento());
					respuesta = "(!!!) Error de asignacion: El PACIENTE "+ paciente.getNombre()+" CON DOCUMENTO "+paciente.getDocumento()+" NO ESTA AFILIADO A LA EPS QUE DICE";
					System.out.println(respuesta);
				}
			}
		}
		return resp;
	}

	public void reprogramarCita(Paciente paciente) {
		Calendar fecha = Calendar.getInstance();
		long day = fecha.get(Calendar.DAY_OF_MONTH);
		long month = fecha.get(Calendar.MONTH);
		long year = fecha.get(Calendar.YEAR);
		String fechas = Long.toString(day+1)+" "+Long.toString(month+1)
		+" "+Long.toString(year);

		int tamHistorial = paciente.getHistorial().size();
		if(!fechas.equals(paciente.getHistorial().get(tamHistorial-1).getFecha())) {
			paciente.getHistorial().get(tamHistorial-1).setFecha(fechas);
			System.out.println("(***) SE MOVIO LA FECHA DEL PACIENTE: "+paciente.getDocumento());
		}
		

		//Se debe informar reprogamacion de la cita

	}


	public void ordenarPrioridad(Paciente paciente) {
		//Queda ordenar por orden de llegada cuando hay un empate
		boolean seProgramo = false;
		boolean tieneCita = false;
		boolean tieneCoronavirus = false;
		List<Paciente> copiaCitasProgramadas = new ArrayList<Paciente>();
		for (int i = 0; i < citasProgramadas.size(); i++) {
			if (citasProgramadas.get(i).getDocumento() == paciente.getDocumento()) {
				tieneCita = true;
			}
		}

		if(paciente.getPrioridad() != "No enfermo" && paciente.getPrioridad() != "Leve")
			tieneCoronavirus = true;

		if(citasProgramadas.size()>=0 && !tieneCita && tieneCoronavirus) { //Existen citas disponibles
			for (int i = 0; i < citasProgramadas.size(); i++) {

				if (!seProgramo && i+1 < citasProgramadas.size()) {
					boolean entro = false;
					if(paciente.getEvaluacion() == citasProgramadas.get(i).getEvaluacion()
							&& paciente.getEvaluacion() > citasProgramadas.get(i+1).getEvaluacion()) {
						//System.out.println("***************** USO ESTA OPCION ****** "+paciente.getDocumento());
						//System.out.println(citasProgramadas.get(i).getDocumento());
						//System.out.println(citasProgramadas.get(i+1).getDocumento());
						copiaCitasProgramadas.add(citasProgramadas.get(i));
						copiaCitasProgramadas.add(paciente);
						seProgramo = true;
						entro = true;
						
					}

					if (paciente.getEvaluacion()>citasProgramadas.get(i).getEvaluacion()) {
						//System.out.println("+++++++++ USO ESTA OPCION +++++++++ "+paciente.getDocumento());
						//System.out.println(citasProgramadas.get(i).getDocumento());
						copiaCitasProgramadas.add(paciente);
						copiaCitasProgramadas.add(citasProgramadas.get(i));
						
						seProgramo = true;
						entro = true;
					}
					if (!entro) {
						copiaCitasProgramadas.add(citasProgramadas.get(i));
					}
				}else {
					copiaCitasProgramadas.add(citasProgramadas.get(i));
				}
					
			}
			if (!seProgramo) {
				copiaCitasProgramadas.add(paciente);
			}
			/*for (int j = 0 ; j < copiaCitasProgramadas.size(); j++) {
				System.out.println(copiaCitasProgramadas.get(j).getDocumento()+" "+copiaCitasProgramadas.get(j).getEvaluacion());
			}*/
			for (int k = maxCitasDia ; k < copiaCitasProgramadas.size(); k++) {
				reprogramarCita(copiaCitasProgramadas.get(k));
			}
			this.citasProgramadas = copiaCitasProgramadas;
			this.setCitasProgramadas(copiaCitasProgramadas);
		}

		


	}



}
