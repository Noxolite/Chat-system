package chatSystem;

import javax.swing.JFrame;

public abstract class Gui extends JFrame{

	private static final long serialVersionUID = 1L;
	protected ChatController chatCtrl;
	
	public ChatController getChatCtrl() {
		return chatCtrl;
	}
	public void setChatCtrl(ChatController chatCtrl) {
		this.chatCtrl = chatCtrl;
	}

	//Makes the window visible
	public void showGui()
	{
		this.setVisible(true);
	}

	//Makes the window disappear
	public void hideGui()
	{
		this.setVisible(false);
	}

	public abstract void initComponents();
	
}
