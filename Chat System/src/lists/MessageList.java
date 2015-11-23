package lists;

import java.util.Observable;

public class MessageList extends Observable{

	private static MessageList instanceMsgList;
	
	private MessageList(){
		
	}
	
	public static MessageList getInstance(){
		if(instanceMsgList == null){
			instanceMsgList = new MessageList();
		}
		return instanceMsgList;
	}
}
