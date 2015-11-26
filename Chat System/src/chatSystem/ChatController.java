package chatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import lists.*;
import packet.*;

public class ChatController {

	private static ChatController instanceController;
	private ChatNi ni;
	private ChatGui chatGui;
	private LogGui logGui;
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

	public ChatGui getGui() {
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

	public void performConnect(String nickName){

		this.localUser = new User(nickName);
		this.chatGui.showChatGui();
		this.ni.startListening();
		this.ni.sendHello(localUser);
		try {
			this.myUserList.addUser(new User(("Broadcast"), InetAddress.getByName(this.ni.getAdresseBroadcast())));
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException dans performConnect de ChatController");
		}
	}

	public void performDisconnect(){

		this.ni.sendBye(this.localUser);
		//Fermer le GUI
		this.myUserList.removeAll();
		this.myMsgList.removeAll();
		this.localUser = null;
	}

	public void performSendMessage(User user, String payLoad){
		Calendar cal = Calendar.getInstance();
		Message msg = new Message(cal.getTime(), this.getLocalUser().getNickName(), payLoad, this.getLocalUser().getIp());
		this.ni.sendMessage(user, msg);
		this.myMsgList.addMessage(msg);
	}

	public void receive(Packet inPacket) {
		
		if(inPacket instanceof Hello){
			Hello hello = (Hello) inPacket;
			User remoteUser = new User(hello.getNickname(), hello.getIp());
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				System.out.println("IP locale" + hello.getIp() + " IP comparee " + hello.getIp());
				if(!this.myUserList.getUserList().contains(remoteUser)){
					this.myUserList.addUser(remoteUser);
				}
				this.ni.sendHelloBack(this.getLocalUser());
				System.out.println("Hello reçu de " + hello.getNickname() + " à l'adresse " + hello.getIp());
			}
		}

		else if(inPacket instanceof HelloBack){
			HelloBack helloBack = (HelloBack) inPacket;
			User remoteUser = new User(helloBack.getNickname(), helloBack.getIp());
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){

				if(!this.myUserList.getUserList().contains(remoteUser)){
					this.myUserList.addUser(remoteUser);
				}
				System.out.println("HelloBack reçu de " + helloBack.getNickname() + " à l'adresse " + helloBack.getIp());
			}
		}

		else if(inPacket instanceof Bye){
			Bye bye = (Bye) inPacket;
			User remoteUser = new User(bye.getNickname(), bye.getIp());
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
				this.myUserList.deleteUser(remoteUser);
				System.out.println("Bye reçu");
			}
		}

		else if(inPacket instanceof Message){
			Message msg = (Message) inPacket;
			User remoteUser = new User(msg.getFrom(), msg.getIp());
			if(!remoteUser.getIp().equals(this.getLocalUser().getIp())){
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

	public static void main(String[] args){
		ChatController chatCtrl = ChatController.getInstance();
		chatCtrl.setNi(ChatNi.getInstance());
		chatCtrl.setLogGui(LogGui.getInstance());
		chatCtrl.setChatGui(ChatGui.getInstance());
		ChatGui.getInstance().setLogGui(LogGui.getInstance());

	}

}
