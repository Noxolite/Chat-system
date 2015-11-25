package chatSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import packet.Message;
import lists.MessageList;
import lists.User;
import lists.UserList;

public class ChatGui extends JFrame implements Observer, ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static ChatGui instanceGui;
	private LogGui mLogGUI ;
	private ChatController chatCtrl;
	private JTextArea txtWriting;
	private JTextArea txtRecMessage;
	private JButton bSend;
	private JButton bDisconnect;
	private JLabel lWriteHere;
	private JLabel lListTitle;
	private JList<User> userList;
	private DefaultListModel<User> listModel;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;

	private ChatGui(){
		this.chatCtrl = ChatController.getInstance();
		this.chatCtrl.getMyUserList().addObserver(this);
		this.chatCtrl.getMyMsgList().addObserver(this);
		initComponents();
		this.setAlwaysOnTop(true);
		addWindowListener(this);

	}

	public static ChatGui getInstance(){
		if(instanceGui == null){
			instanceGui = new ChatGui();
		}
		return instanceGui;
	}

	public JTextArea getTxtRecMessage(){
		return this.txtRecMessage;
	}

	public ChatController getChatCtrl() {
		return chatCtrl;
	}

	public void setmLogGUI(LogGui mLogGUI) {
		this.mLogGUI = mLogGUI;
	}

	public void disconnect(){
		this.chatCtrl.performDisconnect();
		this.mLogGUI.closeChatGui();
		this.txtRecMessage.setText("");
		this.txtWriting.setText("");
	}
	
	public void askSendMessage(){
		this.chatCtrl.performSendMessage(this.txtWriting.getText());
		this.txtWriting.setText("");
	}

	public void initComponents(){

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		lListTitle = new JLabel("Connected Users");
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.insets = new Insets(10, 10, 5, 10);
		this.add(lListTitle, gbc);

		listModel = new DefaultListModel<User>();
		userList = new JList<User>(listModel);
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 10, 10, 10);
		gbc.ipadx = 100;
		gbc.ipady = 200;
		this.add(userList, gbc);

		txtRecMessage = new JTextArea();
		txtRecMessage.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.ipadx = 500;
		gbc.ipady = 200;
		this.add(txtRecMessage, gbc);
		
		DefaultCaret caret1 = (DefaultCaret)txtRecMessage.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane2 = new JScrollPane(txtRecMessage);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(scrollPane2, gbc);

		lWriteHere = new JLabel("Enter your message");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = gbc.gridheight = 1;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.fill = GridBagConstraints.NONE;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		this.add(lWriteHere, gbc);

		txtWriting = new JTextArea();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.ipadx = 400;
		gbc.ipady = 100;
		this.add(txtWriting, gbc);
		
		DefaultCaret caret2 = (DefaultCaret)txtWriting.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane1 = new JScrollPane(txtWriting);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(scrollPane1, gbc);

		bSend = new JButton("Send");
		bSend.addActionListener(this);
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.fill = GridBagConstraints.NONE;
		this.add(bSend, gbc);
		
		bDisconnect = new JButton("Disconnect");
		bDisconnect.addActionListener(this);
		gbc.gridy = 4;
		this.add(bDisconnect, gbc);

		this.pack();
		//this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof UserList){
			if(arg1 instanceof User){
				this.listModel.clear();
				for(User us : chatCtrl.getMyUserList().getUserList()){
					this.listModel.addElement(us);
				}
			} else{
				this.listModel.clear();
			}
		}else if(arg0 instanceof MessageList){
			if(arg1 instanceof Message){
				Message msg = (Message) arg1;
				this.txtRecMessage.append(msg.getFrom() + " (" + msg.getTime().toString() + ") : \n" + msg.getPayload() + "\n\n");
			} else{
				this.txtRecMessage.setText("");
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.bDisconnect){
			this.disconnect();
		} else if(e.getSource() == this.bSend){
			this.askSendMessage();
		}
		
	}

	
	public void windowClosing(WindowEvent arg0) {
		this.disconnect();
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

}
