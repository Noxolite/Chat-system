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

public class ChatGui extends JFrame implements Observer, ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static ChatGui instanceGui;
	private LogGui logGui ;
	private ChatController chatCtrl;
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

	public JTextPane getTxtRecMessage(){
		return this.txtRecMessage;
	}

	public ChatController getChatCtrl() {
		return chatCtrl;
	}

	public void setmLogGUI(LogGui mLogGUI) {
		this.logGui = mLogGUI;
	}

	public void disconnect(){
		this.chatCtrl.performDisconnect();
		this.logGui.closeChatGui();
		this.txtRecMessage.setText("");
		this.txtWriting.setText("");
	}
	
	public void askSendMessage(){
		if(this.userList.getSelectedValue() != null){
		this.chatCtrl.performSendMessage(this.userList.getSelectedValue(),this.txtWriting.getText());
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
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        jScrollPane1.setViewportView(userList);

        lListTitle.setText("Connected users");

        lWriteHere.setText("Enter a message");

        txtWriting.setColumns(20);
        txtWriting.setRows(5);
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

	
	
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof UserList){
			if(arg1 instanceof User){
				this.listModel.clear();
				for(User us : chatCtrl.getMyUserList().getUserList()){
					this.listModel.addElement(us);
					this.userList.repaint();
				}
			} else{
				this.listModel.clear();
				this.userList.repaint();
			}
		}else if(arg0 instanceof MessageList){
			if(arg1 instanceof Message){
				Message msg = (Message) arg1;
				SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
				String line = "<div><font size=4 color=RED><b>" + msg.getFrom() + "</b></font><font size=4 color=GRAY><i>(" + date_format.format(msg.getTime()) + ")</i></font><font size=3 color=BLACK> : " + msg.getPayload() + "</font></div>";
				try {
					this.textPanelHtmlKit.insertHTML((HTMLDocument) this.txtRecMessage.getDocument(), this.txtRecMessage.getDocument().getLength(), line, 0, 0, null);
				} catch (BadLocationException e) {
					System.out.println("BadLocationException pour l'affichage d'un message");
				} catch (IOException e) {
					System.out.println("IOException pour l'affichage d'un message");
				}
				this.txtRecMessage.repaint();
			} else{
				this.txtRecMessage.setText("");
				this.txtRecMessage.repaint();
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
