package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 

import Controllers.LoginHandler;
import Controllers.RoleHandler;

public class Login implements ActionListener{

	private JFrame frame;
	private JTextField username_txt;
	private JPasswordField password_txt;


	private JButton btnLogin;
	private JButton btnManager;
	private JButton btnHr;
	private JButton btnProductM;
	private JButton btnCashier;
	private JButton btnPromoManager;
	
	/**
	 * Create the application.
	 */
	public Login() {
		System.out.println("Initialize Form");
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println("Creating Form");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(36, 34, 48, 14);
		frame.getContentPane().add(lblLogin);
		
		username_txt = new JTextField();
		username_txt.setBounds(108, 68, 96, 20);
		frame.getContentPane().add(username_txt);
		username_txt.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(36, 71, 62, 14);
		frame.getContentPane().add(lblUsername);
		
		password_txt = new JPasswordField();
		password_txt.setBounds(108, 99, 96, 20);
		frame.getContentPane().add(password_txt);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(36, 102, 62, 14);
		frame.getContentPane().add(lblPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(36, 140, 168, 23);
		btnLogin.addActionListener(this);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblOtherLogin = new JLabel("Other Login");
		lblOtherLogin.setBounds(302, 34, 96, 14);
		frame.getContentPane().add(lblOtherLogin);
		
		btnManager = new JButton("Manager");
		btnManager.setBounds(261, 67, 137, 23);
		btnManager.addActionListener(this);
		frame.getContentPane().add(btnManager);
		
		btnHr = new JButton("Human Resource");
		btnHr.setBounds(261, 98, 137, 23);
		btnHr.addActionListener(this);
		frame.getContentPane().add(btnHr);
		
		btnProductM = new JButton("Product Manager");
		btnProductM.setBounds(261, 132, 137, 23);
		btnProductM.addActionListener(this);
		frame.getContentPane().add(btnProductM);
		
		btnPromoManager = new JButton("Promo Manager");
		btnPromoManager.setBounds(261, 166, 137, 23);
		btnPromoManager.addActionListener(this);
		frame.getContentPane().add(btnPromoManager);
		
		btnCashier = new JButton("Cashier");
		btnCashier.setBounds(261, 203, 137, 23);
		btnCashier.addActionListener(this);
		frame.getContentPane().add(btnCashier);
	}

	void closeView(){
		frame.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//btnLogin
		if(e.getSource() == btnLogin){
			if(LoginHandler.getInstance().login_user(username_txt, password_txt)){
				LoginHandler.getInstance().goToRolePage();
				closeView();
			}
			else{
				JOptionPane.showMessageDialog(null, "Login failed, check username and password");
			}
		}
		else if(e.getSource() == btnManager){
			LoginHandler.getInstance().login_user(1, RoleHandler.MANAGER_ID);
			LoginHandler.getInstance().goToRolePage();
			closeView();
		}
		else if(e.getSource() == btnHr){
			LoginHandler.getInstance().login_user(1, RoleHandler.HUMANRESOUCE_ID);
			LoginHandler.getInstance().goToRolePage();
			closeView();
		}
		else if(e.getSource() == btnProductM){
			LoginHandler.getInstance().login_user(1, RoleHandler.STORAGEMANAGER_ID);
			LoginHandler.getInstance().goToRolePage();
			closeView();
		}
		else if(e.getSource() == btnPromoManager){
			LoginHandler.getInstance().login_user(1, RoleHandler.PROMOMANAGER_ID);
			LoginHandler.getInstance().goToRolePage();
			closeView();
		}
		else if(e.getSource() == btnCashier){
			LoginHandler.getInstance().login_user(1, RoleHandler.CASHIER_ID);
			LoginHandler.getInstance().goToRolePage();
			closeView();
		}
	}

}
