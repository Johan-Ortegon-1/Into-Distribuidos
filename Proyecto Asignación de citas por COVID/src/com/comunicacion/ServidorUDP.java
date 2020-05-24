package com.comunicacion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorUDP extends Thread {
	private DatagramSocket socket;
    private boolean ejecucion;
    private byte[] buffer = new byte[256];
 
    public ServidorUDP() throws SocketException {
    	socket=new DatagramSocket(4445);
;    }
 
    public void run() {
        ejecucion = true;
 
        while (ejecucion) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            InetAddress direccion = packet.getAddress();
            int puerto = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, direccion, puerto);
            String recibido = new String(packet.getData(), 0, packet.getLength());
             
            if (recibido.equals("end")) {
            	ejecucion = false;
                continue;
            }
            try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        socket.close(); //
    }
}
