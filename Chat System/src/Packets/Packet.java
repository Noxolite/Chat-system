package Packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

public abstract class Packet  implements Serializable{

	private static final long serialVersionUID = 1L;

	public static byte[] serialize(Object obj){
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(out);
			os.writeObject(obj);
		} catch (IOException e) {
			System.out.println("IOException dans serialize de Paquet");
		}
	    return out.toByteArray();
	}
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
	
	public DatagramPacket toDatagramPacket(InetAddress addrCible, int portDistant){
		byte[] msgSeria;
		msgSeria = serialize(this);
		int length = msgSeria.length; 
		byte buffer[] = msgSeria; 
		return new DatagramPacket(buffer,length,addrCible,portDistant);
	}
	
	public static Packet toPacket(DatagramPacket in){
		Packet p = null;
			try {
				p = (Packet) deserialize(in.getData());
			} catch (ClassNotFoundException e) {
				System.out.println("Classe non trouvée dans toPacket de Packet");
			} catch (IOException e) {
				System.out.println("IOException dans toPacket de Packet");
			}
		return p;
	}
	
}
