package com.comunicacion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.negocio.Paciente;

public class ClienteIpsServidorIns {
	private DatagramSocket socket;
	private InetAddress address;
	private Paciente pacienteUDP;
	private byte[] buffer;

	public ClienteIpsServidorIns(Paciente pacienteUDP) throws SocketException, UnknownHostException {
		this.pacienteUDP = pacienteUDP;
		socket = new DatagramSocket();
		address = InetAddress.getByName("192.168.1.63"); // AQUI VA LA IP
	}

	public void enviar(Paciente pacienteUDP) throws IOException {// Metodo
		
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(pacienteUDP);
		oo.close();

		buffer = bStream.toByteArray();
				
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4445); // Creo puerto a enviar
		socket.send(packet);
		packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		//String received = new String(packet.getData(), 0, packet.getLength());
		//return received;
	}

	public void close() {
		socket.close();
	}

	public Paciente getPacienteUDP() {
		return pacienteUDP;
	}

	public void setPacienteUDP(Paciente pacienteUDP) {
		this.pacienteUDP = pacienteUDP;
	}

}
