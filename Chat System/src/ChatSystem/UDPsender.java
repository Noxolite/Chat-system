package ChatSystem;
import java.io.*;
import java.net.*;

import Packets.*;

public class UDPsender {

	  final static int taille = 1024; 
	  final static byte buffer[] = new byte[taille];

	  public static byte[] serialize(Object obj) throws IOException {
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(obj);
		    return out.toByteArray();
		}
	  
	  public static void main(String argv[]) throws Exception 
	    { 
		  Hello msgHello = new Hello("toto",InetAddress.getLocalHost());
		  byte[] msgSeria = serialize(msgHello);
		  InetAddress serveur = InetAddress.getLocalHost(); 
	      int length = msgSeria.length; 
	      byte buffer[] = msgSeria; 
	      DatagramPacket dataSent = new DatagramPacket(buffer,length,serveur,42028); 
	      DatagramSocket socket = new DatagramSocket(); 
	      socket.send(dataSent);
	    } 
	
}
