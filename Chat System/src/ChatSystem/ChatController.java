package ChatSystem;

import Packets.*;
import Lists.*;

public class ChatController {

	private static ChatController instanceController;
	private ChatNi ni;
	private ChatGui chatGui;
	private LogGui logGui;
	private User localUser;
	private UserList myUserList;
	
	private ChatController(){
		this.myUserList = UserList.getInstance();
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
	
	public void performConnect(String nickName){

		this.localUser = new User(nickName);
		ni.sendHello(localUser);
		this.logGui.showChatGui();
	}
	
	public void performDisconnect(){
		
		//Faire if(connecté)
		this.ni.sendBye(this.localUser);
		//Fermer le GUI
		UserList.getInstance().removeAll();
		this.localUser = null;
	}
	
	public void receive(Packet inPacket) {

		if(inPacket instanceof Hello){
			Hello hello = (Hello) inPacket;
			User remoteUser = new User(hello.getNickname(), hello.getIp());
			if(!this.myUserList.contains(remoteUser)){
				this.myUserList.addUser(remoteUser);
			}
			this.ni.sendHelloBack(this.getLocalUser());
			System.out.println("Hello reçu de " + hello.getNickname() + " à l'adresse " + hello.getIp());
		}
		
		else if(inPacket instanceof HelloBack){
			HelloBack helloBack = (HelloBack) inPacket;
			User remoteUser = new User(helloBack.getNickname(), helloBack.getIp());
			if(!this.myUserList.contains(remoteUser)){
				this.myUserList.addUser(remoteUser);
			}
			System.out.println("HelloBack reçu de " + helloBack.getNickname() + " à l'adresse " + helloBack.getIp());
		}
		
		else if(inPacket instanceof Bye){
			Bye bye = (Bye) inPacket;
			User remoteUser = new User(bye.getNickname(), bye.getIp());
			this.myUserList.deleteUser(remoteUser);
			System.out.println("Bye reçu");
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
		ChatGui.getInstance().setmLogGUI(LogGui.getInstance());
		
	}
	
}
