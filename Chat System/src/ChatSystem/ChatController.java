package ChatSystem;

public class ChatController {

	private static ChatController instanceController;
	private ChatNi ni;
	private ChatGui gui;
	
	private ChatController(){
		this.ni = ChatNi.getInstance();
		this.gui = ChatGui.getInstance();
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
		return gui;
	}
	
	
	
}
