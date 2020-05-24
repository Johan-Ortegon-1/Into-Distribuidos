package com.comunicacion;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.negocio.Paciente;

public class ServidorUDP extends Thread {
	private DatagramSocket socket;
	private boolean ejecucion;
	private byte[] buffer = new byte[256];
	private String respuesta = "negativo";
	
	public ServidorUDP() throws SocketException {
		socket = new DatagramSocket(4445);
		;
	}

	public void run() {
		ejecucion = true;
		System.out.println("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		while (ejecucion) {
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				System.out.println("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!!!!!!!!!!!");
				socket.receive(packet);
				System.out.println("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!!!!!!!!!!!22222");
			} catch (IOException e) {
				e.printStackTrace();
			}
			ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
			ObjectInputStream iStream;
			try {//Recibe el paciente desde IPS
				System.out.println("Recibiendo paciente");
				iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
				Paciente pacienteActual = (Paciente) iStream.readObject();
				if(pacienteActual.getNombre().equals("Zeuz"))
				{
					System.out.println("!!!!!!!!!! ES ZEUZ !!!!!!!!!");
					respuesta = "positivo";
				}
				iStream.close();
				System.out.println("Paciente FIN");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// ObjectInputStream is = new ObjectInputStream(new
			// BufferedInputStream(byteStream));
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			//Devolver Respuesta a la IPS
			buffer = respuesta.getBytes();
			
			InetAddress direccion = packet.getAddress();
			int puerto = packet.getPort();
			packet = new DatagramPacket(buffer, buffer.length, direccion, puerto);

			// String recibido = new String(packet.getData(), 0, packet.getLength());

//            if (recibido.equals("end")) {
//            	ejecucion = false;
//                continue;
//            }
			try {
				System.out.println("Enviando UDP");
				socket.send(packet);
				System.out.println("Enviando UDP - FIN");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socket.close();
	}
}
