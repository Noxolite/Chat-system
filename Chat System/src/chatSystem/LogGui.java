package chatSystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class LogGui extends JFrame implements Observer, ActionListener{

	private static LogGui instanceLogGui;
	private ChatController chatCtrl;
	private JTextArea txtName;
	private JButton bConnect;
	private JLabel lUserName;
	private JLabel lErreur;
	private ChatGui mGUI ;

	private LogGui(){
		this.chatCtrl = ChatController.getInstance();
		this.mGUI = ChatGui.getInstance();
		initComponents();
		this.setAlwaysOnTop(true);
		this.setVisible(true);

	}
	
	public static LogGui getInstance(){
		if(instanceLogGui == null){
			instanceLogGui = new LogGui();
		}
		return instanceLogGui;
	}

	public void setmGUI(ChatGui mGUI) {
		this.mGUI = mGUI;
	}

	public void showChatGui()
    {
        mGUI.setVisible(true);
        mGUI.getTxtRecMessage().setText("\nBienvenue\n");
        this.setVisible(false);
    }

	public void closeChatGui()
    {
        mGUI.setVisible(false);
        this.setVisible(true);
    }
	
	public void connect(){
		if(!(this.txtName.getText() == "")){
			this.chatCtrl.performConnect(this.txtName.getText());
			this.txtName.setText("");
		} else{
			this.lErreur.setText("Rentrez un username");
		}
	}

	private void initComponents() {
		
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
		setTitle("Connexion Window");
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.bConnect){
			this.connect();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
