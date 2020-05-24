package com.comunicacion;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorUDP extends Thread {
	private DatagramSocket socket;
	private boolean ejecucion;
	private byte[] buffer;

	public ServidorUDP() throws SocketException {
		socket = new DatagramSocket(4445);
		;
	}

	public void run() {
		ejecucion = true;

		while (ejecucion) {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
			ObjectInputStream iStream;
			try {
				iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
				Object o = iStream.readObject();
				iStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// ObjectInputStream is = new ObjectInputStream(new
			// BufferedInputStream(byteStream));
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			InetAddress direccion = packet.getAddress();
			int puerto = packet.getPort();
			packet = new DatagramPacket(buffer, buffer.length, direccion, puerto);

			// String recibido = new String(packet.getData(), 0, packet.getLength());

//            if (recibido.equals("end")) {
//            	ejecucion = false;
//                continue;
//            }
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socket.close();
	}
}
