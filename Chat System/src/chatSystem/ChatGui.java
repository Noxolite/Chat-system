package chatSystem;

import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import packet.Message;
import lists.MessageList;
import lists.User;
import lists.UserList;

public class ChatGui extends Gui implements Observer, ActionListener, WindowListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private static ChatGui instanceGui;
	private JTextArea txtWriting;
	private JTextPane txtRecMessage;
	private JButton bSend;
	private JButton bDisconnect;
	private JLabel lWriteHere;
	private JLabel lListTitle;
	private JLabel lBoxTitle;
	private JList<User> userList;
	private DefaultListModel<User> listModel;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private HTMLEditorKit textPanelHtmlKit;

	//Constructor
	private ChatGui(){
		this.chatCtrl = ChatController.getInstance();
		this.chatCtrl.getMyUserList().addObserver(this);
		this.chatCtrl.getMyMsgList().addObserver(this);
                this.setTitle("Chat session of " + this.chatCtrl.getLocalUser());
		initComponents();
		this.setAlwaysOnTop(true);
		addWindowListener(this);

	}

	//Gives back an instance of ChatGui singleton
	public static ChatGui getInstance(){
		if(instanceGui == null){
			instanceGui = new ChatGui();
		}
		return instanceGui;
	}

	//Asks for the disconnection to the controller
	public void disconnect(){
		this.chatCtrl.performDisconnect();
		this.txtRecMessage.setText("");
		this.txtWriting.setText("");
	}
	
	//Sends the message to sent to the controller
	public void askSendMessage(){
		if((this.userList.getSelectedValue() != null) && !(this.txtWriting.getText().equals(""))){
                    if(this.userList.getSelectedValue().equals(this.chatCtrl.getMyUserList().getBroadcast())){
                        this.chatCtrl.performSendMessage(this.userList.getSelectedValue(),this.txtWriting.getText(),true);
                    }
                    else{
                        this.chatCtrl.performSendMessage(this.userList.getSelectedValue(),this.txtWriting.getText(),false);
                    }
		this.txtWriting.setText("");
		}
	}

	public void initComponents(){

        jScrollPane1 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        lListTitle = new javax.swing.JLabel();
        lWriteHere = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtWriting = new javax.swing.JTextArea();
        bSend = new javax.swing.JButton();
        bDisconnect = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRecMessage = new javax.swing.JTextPane();
        lBoxTitle = new javax.swing.JLabel();
        textPanelHtmlKit = new HTMLEditorKit();

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listModel = new DefaultListModel<User>();
		userList = new JList<User>(listModel);
		this.listModel.setSize(500);
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        jScrollPane1.setViewportView(userList);

        lListTitle.setText("Connected users");

        lWriteHere.setText("Enter a message");

        txtWriting.setColumns(20);
        txtWriting.setRows(5);
        txtWriting.addKeyListener(this);
        txtWriting.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none");
        jScrollPane3.setViewportView(txtWriting);

        bSend.setText("Send");
        bSend.addActionListener(this);

        bDisconnect.setText("Log out");
        bDisconnect.addActionListener(this);

        txtRecMessage.setEditable(false);
        txtRecMessage.setContentType("text/html");
        txtRecMessage.setEditorKit(textPanelHtmlKit);
        txtRecMessage.setDocument(new HTMLDocument());
        jScrollPane2.setViewportView(txtRecMessage);
        DefaultCaret caret = (DefaultCaret)txtRecMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        lBoxTitle.setText("Dialog box");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lWriteHere)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lListTitle)
                                .addGap(79, 79, 79)
                                .addComponent(lBoxTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lListTitle)
                    .addComponent(lBoxTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lWriteHere)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bSend, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDisconnect)))
                .addContainerGap())
        );

        pack();
	}
	
	//Display the message in the conversation window
	public void displayMsg(Message msg){
		SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
                String line = null;
                if (msg.isBroadcast()){
                    line = "<div><font size=4 color=RED><b>" + msg.getFrom() + "</b></font><font size=4 color=GRAY><i>(" + date_format.format(msg.getTime()) + ")</i></font><font size=3 color=BLACK> : " + msg.getPayload() + "</font></div>";
                }else{
                    line = "<div><font size=4 color=RED><b>" + msg.getFrom() + "</b></font><font size=4 color=GRAY><i>(" + date_format.format(msg.getTime()) + ")</i></font><font size=3 color=BLACK> : (Whisper) " + msg.getPayload() + "</font></div>";
                }
		try {
			this.textPanelHtmlKit.insertHTML((HTMLDocument) this.txtRecMessage.getDocument(), this.txtRecMessage.getDocument().getLength(), line, 0, 0, null);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException pour l'affichage d'un message");
		} catch (IOException e) {
			System.out.println("IOException pour l'affichage d'un message");
		}
	}

	
	
	public void update(Observable arg0, Object arg1) {
		//Notify from the user list
		if(arg0 instanceof UserList){
			//One user was deleted or added
			if(arg1 instanceof User){
				this.listModel.clear();
				for(User us : chatCtrl.getMyUserList().getUserList()){
					this.listModel.addElement(us);
					this.userList.repaint();
				}
			//The user list was cleared	
			} else{
				this.listModel.clear();
				this.userList.repaint();
			}
		//Notify from the message list	
		}else if(arg0 instanceof MessageList){
			//A message was added
			if(arg1 instanceof Message){
				Message msg = (Message) arg1;
				this.displayMsg(msg);
				this.txtRecMessage.repaint();
			//The message list was cleared	
			} else{
				this.txtRecMessage.setText("");
				this.txtRecMessage.repaint();
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Disconnection button clicked
		if(e.getSource() == this.bDisconnect){
			this.disconnect();
		//Send button clicked
		} else if(e.getSource() == this.bSend){
				this.askSendMessage();
		}

	}
	
	public void keyPressed(KeyEvent arg0) {
		//Enter key pressed to send a message
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			this.askSendMessage();
		}
	}

	public void windowClosing(WindowEvent arg0) {
		this.disconnect();
	}

	//Unused methods
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
