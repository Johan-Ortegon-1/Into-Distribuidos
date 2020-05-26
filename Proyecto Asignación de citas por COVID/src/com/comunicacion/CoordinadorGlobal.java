package com.comunicacion;

import java.net.Socket;
import java.util.Map;
/*
import client.Main;
import compartido.Mensaje;
import compartido.RecursoDummy;
import compartido.Registro;
import server.EventoRespuesta;
import server.Participante2;*/

/*public class CoordinadorGlobal {
	private Registro registro;
	private RecursoDummy recurso;
	private Map<Socket, Participante2> conexiones;
	
	volatile int cont;
	
	public CoordinadorGlobal(Registro registro, RecursoDummy recurso, Map<Socket, Participante2> conexiones){
		this.registro = registro;
		this.recurso = recurso;
		this.conexiones = conexiones;
	}
	public void iniciarCommit() throws InterruptedException{
		cont = 0;
		registro.escritura(Registro.iniciar2PC);
		Mensaje peticionVoto = new Mensaje("PETICIONVOTO");
		
		
			Thread.sleep(Main.tiempoSueno);
		
		
		for(Participante2 p: conexiones.values()){
			p.enviarMensaje(peticionVoto, new EventoRespuesta() { 
				@Override
				public void notificacion(Mensaje message) { //successfull
					if(message.type.equals("VOTODECOMMIT")){
						commitP();
					}else{
						abortoP();
					}
				}
			}, new EventoRespuesta() {
				
				@Override
				public void notificacion(Mensaje message) { //timeout
					abortoP();
				}
			});
		}
		
	}
	
	private synchronized void commitP(){
		cont++;
		String latestLog = registro.latest();
		if(cont == conexiones.size() && !latestLog.equals(Registro.ABORTOGLOBAL)){
			registro.escritura(Registro.COMMITGLOBAL);
			Mensaje notiCommit = new Mensaje("COMMITGLOBAL"); 
			for(Participante2 pp: conexiones.values()){
				pp.enviarMensaje(notiCommit);
			}
		}
		
	}
	private synchronized void abortoP(){
		registro.escritura(Registro.ABORTOGLOBAL);
		Mensaje notiAborto = new Mensaje("ABORTOGLOBAL"); 
		for(Participante2 pp: conexiones.values()){
			pp.enviarMensaje(notiAborto);
		}
	}
	

}*/
