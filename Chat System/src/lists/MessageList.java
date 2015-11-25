package lists;

import java.util.ArrayList;
import java.util.Observable;

import packet.Message;

public class MessageList extends Observable{

	private static MessageList instanceMsgList;
	private ArrayList<Message> msgList;
	
	private MessageList(){
		msgList = new ArrayList<Message>();
	}
	
	public static synchronized MessageList getInstance(){
		if(instanceMsgList == null){
			instanceMsgList = new MessageList();
		}
		return instanceMsgList;
	}
	
	public void addMessage(Message msg){    
		this.msgList.add(msg);
		this.setChanged();
		this.notifyObservers(msg);
	}
	
	public void removeAll(){
		this.msgList.clear();
		this.setChanged();
		this.notifyObservers(0);
	}
	
}
