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

	public void enviar(Paciente pacienteUDP) throws IOException {// Metodo de modificacion por referencia
		
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(pacienteUDP);
		oo.close();

		buffer = bStream.toByteArray();
				
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4445); // Creo puerto a enviar
		this.send(packet);
		packet = new DatagramPacket(buffer, buffer.length);
		this.receive(packet);
		
		String received = new String(packet.getData(), 0, packet.getLength());
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
	
	
	
	
	public void send(DatagramPacket packet)
	{
		
	}
	public void receive(DatagramPacket packet)
	{
		
	}

}
