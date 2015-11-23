package chatSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;

import lists.User;
import packet.*;

public class ChatNi implements Runnable{

	private static ChatNi instanceNI;
	private ChatController chatCtrl;
	private InetAddress localAdress;
	private String adrBroadcast = ("255.255.255.255");
	private DatagramSocket socket;
	private int portLocal = 42026;
	private int portDistant = 42025;
	private boolean bListening;

	private ChatNi() {

		try {
			this.localAdress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.chatCtrl = ChatController.getInstance();
		this.bListening = true;
		startListening();

	}

	public static synchronized ChatNi getInstance() {
		if (instanceNI == null){
			instanceNI = new ChatNi();
		}
		return instanceNI;
	}

	public ChatController getChatCtrl() {
		return chatCtrl;
	}

	public void setChatCtrl(ChatController chatController) {
		this.chatCtrl = chatController;
	}

	public void setAdresseBroadcast(String broadcast)
	{
		this.adrBroadcast = broadcast;
	}
	
	public InetAddress getLocalAddress(){
		return this.localAdress;
	}

	public void sendHello(User localUser){
		try {
			Hello msgHello = new Hello(localUser.getNickName(),this.localAdress);
			DatagramPacket dataSent = toDatagramPacket(msgHello, InetAddress.getByName(this.adrBroadcast), this.portDistant);
			this.socket.send(dataSent);
			System.out.println("Hello envoyé");
		} catch (UnknownHostException e) {
			System.out.println("Hôte inconnu");
		}
		catch (IOException e) {
			System.out.println("IOException dans sendHello de ChatNI");
		}
	}

	public void sendHelloBack(User userDistant){
		HelloBack msgHelloBack = new HelloBack(this.chatCtrl.getLocalUser().getNickName(),this.localAdress);
		try {
			DatagramPacket dataSent = toDatagramPacket(msgHelloBack, userDistant.getIp(), this.portDistant);
			this.socket.send(dataSent);
			System.out.println("HelloBack envoyé");
		} catch (IOException e) {
			System.out.println("IOException dans sendHelloBack de ChatNI");
		}

	}

	public void sendBye(User localUser){
		try {
			Bye msgBye = new Bye(localUser.getNickName(), this.localAdress);
			DatagramPacket dataSent = toDatagramPacket(msgBye, InetAddress.getByName(this.adrBroadcast), this.portDistant);
			this.socket.send(dataSent);
			System.out.println("Bye envoyé");
		} catch (IOException e) {
			System.out.println("IOException dans sendBye de ChatNI");
		}

	}

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
	
	public DatagramPacket toDatagramPacket(Packet p, InetAddress addrCible, int portDistant){
		byte[] msgSeria;
		msgSeria = serialize(p);
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

	public void startListening(){

		try {
			socket = new DatagramSocket(portLocal);
			(new Thread(this)).start();
		} catch (SocketException e) {
			System.out.println("Erreur de création du socket dans startListening de ChatNI");
		}

	}
	
	public void StopListening() {
        bListening = false;
    }

	public void run() {
		
		while(bListening){
			
			byte[] streamByte = new byte[1500];
            DatagramPacket inDatagramPacket = new DatagramPacket(streamByte, streamByte.length);
            try {
				socket.receive(inDatagramPacket);
				Packet inPacket = toPacket(inDatagramPacket);
				if(inPacket != null){
					this.chatCtrl.receive(inPacket);
				}
			} catch (IOException e) {
				System.out.println("IOException dans run de ChatNI");
			}
			
			
		}
		
	}


}
