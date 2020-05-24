package com.negocio;

import java.io.Serializable;

public class Cita implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private boolean autorizacion;
	private String fecha;
	private long identificador;
	
	public Cita()
	{
		
	}
	
	public Cita(boolean autorizacion, String fecha, long identificador) {
		
		this.autorizacion = autorizacion;
		this.fecha = fecha;
		this.identificador = identificador;
	}

	public boolean isAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(boolean autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}
	
}



/*for (int i = 0; i < pacientesGlobales.size(); i++) {
System.out.println(pacientesGlobales.get(i).getDocumento());
for (int j = 0; j < pacientesGlobales.get(i).getSintomas().size(); j++) {
	System.out.println(pacientesGlobales.get(i).getSintomas().get(j));
}
}
for (int i = 0; i < epsGlobales.size(); i++) {
System.out.println(epsGlobales.get(i).getNombreEps());
for (int j = 0; j < epsGlobales.get(i).getPacientesAfiliados().size(); j++) {
	System.out.println(epsGlobales.get(i).getPacientesAfiliados().get(j).getDocumento());
}
}

Ins myInsPrueba = new Ins();
for (int i = 0; i < pacientesGlobales.size(); i++) {
myInsPrueba.evaluarPaciente(pacientesGlobales.get(i));
System.out.println(pacientesGlobales.get(i).getDocumento()+" "+pacientesGlobales.get(i).getEvaluacion()+" "+pacientesGlobales.get(i).getPrioridad());
}

System.out.println("_____________________________");
Ips myIpsPrueba = new Ips();
myIpsPrueba.setEntidadesEPS(epsGlobales);
myIpsPrueba.setEntidadIns(myInsPrueba);
for (int i = 0; i < pacientesGlobales.size(); i++) {
myIpsPrueba.asignarCitas(pacientesGlobales.get(i));
}
for (int i = 0; i < myIpsPrueba.getCitasProgramadas().size(); i++) {
System.out.println(myIpsPrueba.getCitasProgramadas().get(i).getDocumento()+" "+myIpsPrueba.getCitasProgramadas().get(i).getEvaluacion());
}*/
