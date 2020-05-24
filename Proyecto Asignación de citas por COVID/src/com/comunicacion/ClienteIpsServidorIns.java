package com.comunicacion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClienteIpsServidorIns {
	private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buffer;
 
    
    public 	ClienteIpsServidorIns() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost"); // AQUI VA LA IP
    }
    
 
    public String enviar(String msg) throws IOException {//Metodo
    	buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4445); //Creo puerto a enviar
        socket.send(packet);
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }
 
    public void close() {
        socket.close();
    }
}

