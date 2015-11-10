package ChatSystem;
import java.net.*;
import Packets.*;

public class ChatNi {

    private static ChatNi instanceNI;
    private ChatController chatCtrl;
    private InetAddress localAdress;
    
    private ChatNi() {
    	this.chatCtrl = ChatController.getInstance();
		try {
			this.localAdress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
    
	
}
