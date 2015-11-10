package ChatSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class ChatGui extends JFrame implements Observer, ActionListener {

	private static ChatGui instanceGui;
	private ChatController chatCtrl;
	private JTextArea txtName;
	private JTextArea txtWriting;
	private JTextArea txtRecMessage;
	private JButton bConnect;
	private JButton bSend;
	private JLabel lUserName;
	private JLabel lWriteHere;
	private JLabel lErreur;
	private JLabel lListtitle;
	private JList userList;
	
	private ChatGui(){
		//this.chatCtrl = ChatController.getInstance();
	}
	
	public static ChatGui getInstance(){
		if(instanceGui == null){
			instanceGui = new ChatGui();
		}
		return instanceGui;
	}

	public ChatController getChatCtrl() {
		return chatCtrl;
	}

	public void ConnectionWindow(){
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		lUserName = new JLabel("Enter your user name");
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.insets = new Insets(10, 15, 0, 15);
		this.add(lUserName, gbc);
		
		txtName = new JTextArea();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 15, 0, 15);
		this.add(txtName, gbc);
		
		lErreur = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 15, 0, 15);
		this.add(lErreur, gbc);
		
		bConnect = new JButton("Connect");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 60, 10, 60);
		this.add(bConnect, gbc);
		bConnect.addActionListener(this);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void ChatWindow(){
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel lListtitle = new JLabel("Connected Users");
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.insets = new Insets(10, 10, 5, 10);
		this.add(lListtitle, gbc);
		
		JList userList = new JList();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 10, 10, 10);
		gbc.ipadx = 100;
		gbc.ipady = 200;
		this.add(userList, gbc);
		
		JTextArea txtRecMessage = new JTextArea();
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
		
		JScrollPane scrollPane2 = new JScrollPane(txtRecMessage);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
        add(scrollPane2, gbc);
		
		JLabel lWriteHere = new JLabel("Enter your message");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = gbc.gridheight = 1;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.fill = GridBagConstraints.NONE;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		this.add(lWriteHere, gbc);
		
		JTextArea txtWriting = new JTextArea();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.insets = new Insets(10, 15, 15, 15);
		gbc.ipadx = 400;
		gbc.ipady = 100;
		this.add(txtWriting, gbc);
		
		JScrollPane scrollPane1 = new JScrollPane(txtWriting);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
        add(scrollPane1, gbc);
		
		JButton bSend = new JButton("Send");
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
		
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
	}
	
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ChatGui chat = new ChatGui();
		chat.ChatWindow();
	    
	  }
	
	
}
