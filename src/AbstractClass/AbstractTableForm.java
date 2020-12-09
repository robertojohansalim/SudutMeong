package AbstractClass;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Controllers.LoginHandler;

public abstract class AbstractTableForm implements ActionListener{
	
	private JFrame frame;
	protected JPanel panelButtonBot;
	JButton btnLogout;
	
	private String titleTable;
	protected JTable table;
	private JScrollPane scrollPane;
	
	public AbstractTableForm() {
		titleTable = setTableTitle();
		initialize();
		frame.setVisible(true);
		configure_panelButtonBot();
		loadDataFromDataBase();
		addListener();
	}
	
	private void initialize() {
		frame = new JFrame();
		// frame.setBounds(100, 100, 500, 500);
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFormlabel = new JLabel(titleTable, SwingConstants.CENTER);
		lblFormlabel.setBounds(10, 57, 564, 20);
		lblFormlabel.setFont(new Font("sanserif", Font.BOLD, 14));
		frame.getContentPane().add(lblFormlabel);
		
		// Scroll Pane (Table)
		 table = new JTable() {
		 	@Override
		 	public boolean isCellEditable(int row, int column) {
		 		return false;
		 	}
		 };
		
		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 77, 564, 300);
		frame.getContentPane().add(scrollPane);
		
		panelButtonBot = new JPanel();
		panelButtonBot.setBounds(10, 395, 564, 55);
		frame.getContentPane().add(panelButtonBot);
		panelButtonBot.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(490, 7, 80, 23);
		btnLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				LoginHandler.getInstance().viewLogin();
			}
		});

		frame.getContentPane().add(btnLogout);

		
		JLabel lblWelcomeusername = new JLabel("Welcome, " + LoginHandler.getInstance().getLoggedName(), SwingConstants.RIGHT);
		lblWelcomeusername.setBounds(145, 11, 340, 14);
		frame.getContentPane().add(lblWelcomeusername);
	}
	
	protected abstract void loadDataFromDataBase();
	
	protected abstract void configure_panelButtonBot();
	
	protected abstract void addListener();

	protected abstract String setTableTitle();

}
