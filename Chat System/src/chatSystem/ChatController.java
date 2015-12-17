package chatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import lists.*;
import packet.*;

public class ChatController {

	private static ChatController instanceController;
	private ChatNi ni;
	private Gui chatGui;
	private Gui logGui;
	private User localUser;
	private UserList myUserList;
	private MessageList myMsgList;

	private ChatController(){
		this.myUserList = UserList.getInstance();
		this.myMsgList = MessageList.getInstance();
	}

	public static ChatController getInstance(){
		if(instanceController == null){
			instanceController = new ChatController();
		}
		return instanceController;
	}

	public ChatNi getNi() {
		return ni;
	}

	public Gui getGui() {
		return chatGui;
	}

	public User getLocalUser() {
		return this.localUser;
	}

	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}

	public void setNi(ChatNi ni) {
		this.ni = ni;
	}

	public void setChatGui(ChatGui chatGui) {
		this.chatGui = chatGui;
	}

	public void setLogGui(LogGui logGui) {
		this.logGui = logGui;
	}

	public void setMyUserList(UserList myUserList) {
		this.myUserList = myUserList;
	}

	public UserList getMyUserList() {
		return this.myUserList;
	}

	public MessageList getMyMsgList() {
		return this.myMsgList;
	}
	
	//Called by the logGui when the user wants to connect
	public void performConnect(String nickName){
		this.localUser = new User(nickName);
                this.setChatGui(ChatGui.getInstance());
		this.chatGui.showGui();
		this.logGui.hideGui();
		this.ni.startListening();
		this.ni.sendHello(localUser);
		//Add a fake user to send a message using broadcast
		try {
			this.myUserList.addUser(new User(("Broadcast"), InetAddress.getByName(this.ni.getAdresseBroadcast())));
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException dans performConnect de ChatController");
		}
	}

	//Called by the chatGui when the user wants to disconnect
	public void performDisconnect(){
                this.myMsgList.deleteObservers(); 
                this.myUserList.deleteObservers();           
		this.ni.sendBye(this.localUser);
		//The lists are cleared and the local user deleted
		this.myUserList.removeAll();
		this.myMsgList.removeAll();
		this.localUser = null;
                this.chatGui.hideGui();
		this.logGui.showGui();
	}

	//Called by the chatGui when the user wants to send a message
	public void performSendMessage(User user, String payLoad,Boolean broadcast){
		Calendar cal = Calendar.getInstance();
		Message msg = new Message(cal.getTime(), this.getLocalUser().getNickName(), payLoad, this.getLocalUser().getIp(), broadcast);
		this.ni.sendMessage(user, msg);
		this.myMsgList.addMessage(msg);
	}

	//Called by the chatNI when a message is received
	public void performReception(Packet inPacket) {
		
		//Hello received
		if(inPacket instanceof Hello){
			Hello hello = (Hello) inPacket;
			User remoteUser = new User(hello.getNickname(), hello.getIp());
			//Verify if the IP received is different from the local address
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				System.out.println("IP locale" + hello.getIp() + " IP comparee " + hello.getIp());
				//Add the remote user if he is not already in the list
				if(!this.myUserList.getUserList().contains(remoteUser)){
					this.myUserList.addUser(remoteUser);
				}
				this.ni.sendHelloBack(this.getLocalUser());
				System.out.println("Hello re�u de " + hello.getNickname() + " � l'adresse " + hello.getIp());
			}
		}

		//HelloBack received
		else if(inPacket instanceof HelloBack){
			HelloBack helloBack = (HelloBack) inPacket;
			User remoteUser = new User(helloBack.getNickname(), helloBack.getIp());
			//Verify if the IP received is different from the local address
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				//Add the remote user if he is not already in the list
				if(!this.myUserList.getUserList().contains(remoteUser)){
					this.myUserList.addUser(remoteUser);
				}
				System.out.println("HelloBack re�u de " + helloBack.getNickname() + " � l'adresse " + helloBack.getIp());
			}
		}

		//Bye received
		else if(inPacket instanceof Bye){
			Bye bye = (Bye) inPacket;
			User remoteUser = new User(bye.getNickname(), bye.getIp());
			//Verify if the IP received is different from the local address
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				this.myUserList.deleteUser(remoteUser);
				System.out.println("Bye re�u");
			}
		}

		//Message received
		else if(inPacket instanceof Message){
			Message msg = (Message) inPacket;
			User remoteUser = new User(msg.getFrom(), msg.getIp());
			//Verify if the IP received is different from the local address
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				//Add the remote user if he is not already in the list
				if(!this.myUserList.getUserList().contains(remoteUser)){
					this.myUserList.addUser(remoteUser);
				}
				this.getMyMsgList().addMessage(msg);
				System.out.println("Message recu");
			}
		}

		else{
			System.out.println("Paquet inconnu");
		}

	}

	//In order to make the chat system work well the windows firewall should be deactivated
	public static void main(String[] args){
		ChatController chatCtrl = ChatController.getInstance();
		chatCtrl.setNi(ChatNi.getInstance());
		chatCtrl.setLogGui(LogGui.getInstance());
		

	}

}
