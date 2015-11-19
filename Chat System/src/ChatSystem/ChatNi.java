package ChatSystem;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import Packets.*;

public class ChatNi implements Runnable{

	private static ChatNi instanceNI;
	private ChatController chatCtrl;
	private InetAddress localAdress;
	private String adrBroadcast= "127.0.0.1";
	private DatagramSocket socket;
	private int portLocal = 42028;
	private int portDistant = 42028;
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

	public void sendHello(User localUser){
		try {
			Hello msgHello = new Hello(localUser.getNickName(),this.localAdress);
			DatagramPacket dataSent = msgHello.toDatagramPacket(InetAddress.getByName(this.adrBroadcast), this.portDistant);
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
			DatagramPacket dataSent = msgHelloBack.toDatagramPacket(userDistant.getIp(), this.portDistant);
			this.socket.send(dataSent);
			System.out.println("HelloBack envoyé");
		} catch (IOException e) {
			System.out.println("IOException dans sendHelloBack de ChatNI");
		}

	}

	public void sendBye(User localUser){
		try {
			Bye msgBye = new Bye(localUser.getNickName(), this.localAdress);
			DatagramPacket dataSent = msgBye.toDatagramPacket(InetAddress.getByName(this.adrBroadcast), this.portDistant);
			this.socket.send(dataSent);
			System.out.println("Bye envoyé");
		} catch (IOException e) {
			System.out.println("IOException dans sendBye de ChatNI");
		}

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
				Packet inPacket = Packet.toPacket(inDatagramPacket);
				if(inPacket != null){
					this.chatCtrl.receive(inPacket);
				}
			} catch (IOException e) {
				System.out.println("IOException dans run de ChatNI");
			}
			
			
		}
		
	}


}
