package chatSystem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LogGui extends Gui implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private static LogGui instanceLogGui;
	private JTextField txtName;
	private JButton bConnect;
	private JLabel lUserName;
	private JLabel lErreur;
	private JLabel lTitle;
	private JLabel lCredit;

	
	private LogGui(){
		this.chatCtrl = ChatController.getInstance();
		initComponents();
		this.setAlwaysOnTop(true);
		this.setVisible(true);

	}
	
	//Gives back an instance of LogGui singleton
	public static LogGui getInstance(){
		if(instanceLogGui == null){
			instanceLogGui = new LogGui();
		}
		return instanceLogGui;
	}

	//Asks the controller to perform a connection
	public void connect(){
		if(!(this.txtName.getText().equals(""))){
			this.chatCtrl.performConnect(this.txtName.getText());
			this.txtName.setText("");
			this.lErreur.setText("");
		} else{
			this.lErreur.setText("Enter an username");
		}
	}
        
        //Initiates de components wich composes the window
	public void initComponents() {
		
		lTitle = new javax.swing.JLabel();
        lUserName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        bConnect = new javax.swing.JButton();
        lCredit = new javax.swing.JLabel();
        lErreur = new javax.swing.JLabel();
		
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("Chat System");
        lTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lUserName.setText("Enter your user name");

        bConnect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bConnect.setText("Log in");
        bConnect.addActionListener(this);
        
        txtName.addKeyListener(this);
        
        lCredit.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lCredit.setText("Created by David Fernandes and Axel Chauvin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lCredit)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lUserName)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(bConnect))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lErreur, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lUserName)
                .addGap(5, 5, 5)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lErreur, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(lCredit)));

        pack();

	}

	public void actionPerformed(ActionEvent e) {
		//Connection button pressed
		if(e.getSource() == this.bConnect){
				this.connect();
		}
	}

	public void keyPressed(KeyEvent arg0) {
		//Enter key pressed to perform a connection
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			this.connect();
		}
		
	}

	//Unused methods
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
