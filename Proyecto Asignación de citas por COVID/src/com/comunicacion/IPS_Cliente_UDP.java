package com.comunicacion;

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
import java.net.UnknownHostException;

import com.negocio.Paciente;

public class IPS_Cliente_UDP {
	private DatagramSocket socket;
	private InetAddress address;
	private Paciente pacienteUDP;
	private byte[] buffer;

	public IPS_Cliente_UDP(Paciente pacienteUDP) throws SocketException, UnknownHostException {
		this.pacienteUDP = pacienteUDP;
		socket = new DatagramSocket();
		address = InetAddress.getByName("192.168.1.63"); // AQUI VA LA IP INS
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
		//Recibir respuesta de INS
		try
		{
			this.receive(packet);
			//Asignar informacion por referencia:
			if(packet.getData() != null)
			{
				ObjectInputStream iStream;
				iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
				Paciente pacienteActualizado = (Paciente) iStream.readObject();
				pacienteUDP.setEvaluacion(pacienteActualizado.getEvaluacion());
				pacienteUDP.setPrioridad(pacienteActualizado.getPrioridad());
			}
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
