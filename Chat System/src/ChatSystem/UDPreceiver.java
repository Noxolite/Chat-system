package ChatSystem;
import java.io.*;
import java.net.*;

import Packets.*;

public class UDPreceiver {

	final static int port = 42028; 
	final static int taille = 1024; 
	final static byte buffer[] = new byte[taille];

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
	
	public static void main(String argv[]) throws Exception 
	{ 
		DatagramSocket socket = new DatagramSocket(port); 
		while(true) 
		{ 
			DatagramPacket data = new DatagramPacket(buffer,buffer.length); 
			socket.receive(data);
			Hello msgHello = (Hello) deserialize(data.getData());
			System.out.println("User : " + msgHello.getNickname() + " adresse IP : " + msgHello.getIp().toString());
		} 
	}

}
