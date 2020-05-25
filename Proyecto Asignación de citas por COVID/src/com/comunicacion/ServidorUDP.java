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
	private Ins myIns;
	public ServidorUDP(Ins myIns) throws SocketException {
		socket = new DatagramSocket(4445);
		this.myIns = myIns;
	}

	public void run() {
		ejecucion = true;
		Paciente pacienteActual = new Paciente();
		while (ejecucion) {
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);//Se recibe el paquete con el paciente
			} catch (IOException e) {
				e.printStackTrace();
			}
			ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
			ObjectInputStream iStream;
			try {//Recibe el paciente desde IPS
				iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
				pacienteActual = (Paciente) iStream.readObject();
				if(pacienteActual != null)
				{
					myIns.evaluarPaciente(pacienteActual);
				}
				iStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// ObjectInputStream is = new ObjectInputStream(new
			// BufferedInputStream(byteStream));
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			//Devolver Respuesta a la IPS
			if(pacienteActual != null)
			{
				try
				{
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					ObjectOutput oo;
					oo = new ObjectOutputStream(bStream);
					oo.writeObject(pacienteActual);
					oo.close();
					
					buffer = bStream.toByteArray();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 


				InetAddress direccion = packet.getAddress();
				int puerto = packet.getPort();
				
				DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length, 4445); // Creo puerto a enviar
				packet2 = new DatagramPacket(buffer, buffer.length, direccion, puerto);

				try {
					socket.send(packet2);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		socket.close();
	}
}
