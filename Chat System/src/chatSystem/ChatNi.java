package chatSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import lists.User;
import packet.*;

public class ChatNi implements Runnable{

	private static ChatNi instanceNI;
	private ChatController chatCtrl;
	private InetAddress localAdress;
	private String adrBroadcast = ("255.255.255.255");
	private DatagramSocket socketReception;
	private DatagramSocket socketEmission;
	private int portLocal = 42025;
	private int portDistant = 42025;
	private boolean bListening;

	private ChatNi() {
		
		try {
			this.localAdress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.chatCtrl = ChatController.getInstance();

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

	public String getAdresseBroadcast(){
		return this.adrBroadcast;
	}
	
	public InetAddress getLocalAddress(){
		return this.localAdress;
	}

	public void sendHello(User localUser){
		try {
			Hello msgHello = new Hello(localUser.getNickName(),this.localAdress);
			DatagramPacket dataSent = toDatagramPacket(msgHello, InetAddress.getByName(this.adrBroadcast), this.portDistant);
			this.socketEmission.send(dataSent);
			System.out.println("Hello envoy�");
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException dans sendHello de ChatNi");
		}
		catch (IOException e) {
			System.out.println("IOException dans sendHello de ChatNI");
		}
	}

	public void sendHelloBack(User userDistant){
		HelloBack msgHelloBack = new HelloBack(this.chatCtrl.getLocalUser().getNickName(),this.localAdress);
		try {
			DatagramPacket dataSent = toDatagramPacket(msgHelloBack, userDistant.getIp(), this.portDistant);
			this.socketEmission.send(dataSent);
			System.out.println("HelloBack envoy�");
		} catch (IOException e) {
			System.out.println("IOException dans sendHelloBack de ChatNI");
		}

	}

	public void sendBye(User localUser){
		try {
			this.socketReception.close();
			this.stopListening();
			Bye msgBye = new Bye(localUser.getNickName(), this.localAdress);
			DatagramPacket dataSent = toDatagramPacket(msgBye, InetAddress.getByName(this.adrBroadcast), this.portDistant);
			this.socketEmission.send(dataSent);
			this.socketEmission.close();
			System.out.println("Bye envoy�");
		} catch (IOException e) {
			if(bListening){
				System.out.println("IOException dans sendBye de ChatNI");
			} else{
				System.out.println("Fermeture socket");
			}
		}

	}

	public void sendMessage(User userDistant,Message msg){
		try {
			DatagramPacket dataSent = toDatagramPacket(msg, userDistant.getIp(), this.portDistant);
			this.socketEmission.send(dataSent);
			System.out.println("Msg envoy�");
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException dans sendMessage de ChatNI");
		} catch (IOException e) {
			System.out.println("IOException dans sendMessage de ChatNI");
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
	
	public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
	
	//Convert an object packet into a datagramPacket in order to send it
	public DatagramPacket toDatagramPacket(Packet p, InetAddress addrCible, int portDistant){
		byte[] msgSeria;
		msgSeria = serialize(p);
		int length = msgSeria.length; 
		byte buffer[] = msgSeria; 
		return new DatagramPacket(buffer,length,addrCible,portDistant);
	}
	
	//Convert a datagramPacket into an object packet in order to do the treatment on it
	public Packet toPacket(DatagramPacket in){
		Packet p = null;
			try {
				p = (Packet) deserialize(in.getData());
			} catch (ClassNotFoundException e) {
				System.out.println("Classe non trouv�e dans toPacket de Packet");
			} catch (IOException e) {
				System.out.println("IOException dans toPacket de Packet");
			}
		return p;
	}

	public void startListening(){
		this.bListening = true;
		try {
			socketReception = new DatagramSocket(portLocal);
			socketEmission = new DatagramSocket();
			(new Thread(this)).start();
		} catch (SocketException e) {
			System.out.println("Erreur de cr�ation du socket dans startListening de ChatNI");
		}

	}
	
	public void stopListening() {
        bListening = false;
    }

	public void run() {

		byte[] streamByte = new byte[1500];
        DatagramPacket inDatagramPacket = new DatagramPacket(streamByte, streamByte.length);
        
		while(bListening){
			
            try {
				socketReception.receive(inDatagramPacket);
				Packet inPacket = toPacket(inDatagramPacket);
				if(inPacket != null){
					this.chatCtrl.performReception(inPacket);
				}
            } catch (IOException e) {
            	if(bListening){
            		System.out.println("IOException dans run de ChatNI");
            	} else{
            		System.out.println("Fermeture du socket");
            	}
            }

		}
		
	}


}
