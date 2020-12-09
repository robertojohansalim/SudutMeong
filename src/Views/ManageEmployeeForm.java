package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import AbstractClass.AbstractTableForm;
import Controllers.EmployeeHandler;
import Controllers.LoginHandler;
import Controllers.RoleHandler;
import Controllers.TransactionHandler;
import Models.Employee;

public class ManageEmployeeForm extends AbstractTableForm implements ActionListener{

	JButton btnInsert, btnUpdate, btnFire;
	JButton customButton1;

	String selectedEmploID = "";
	String selectedEmploRole = "";
	String selectedEmploName = "";
	String selectedEmploUsername = "";
	String selectedEmploDOB = "";
	String selectedEmploSalary = "";
	String selectedEmploStatus = "";
	String selectedEmploPassword = "";

	public ManageEmployeeForm() {
		super();
	}

	@Override
	protected void loadDataFromDataBase() {
		Vector<String> header = new Vector<String>();
		header.add("Employee ID");
		header.add("Role ID");
		header.add("Name");
		header.add("Username");
		header.add("Date-of-Birth");
		header.add("Salary");
		header.add("Status");
		header.add("Password");

		//Get All Data From Database
		Vector<Employee> list = EmployeeHandler.getInstance().getAllEmployee();
		
		//Table
		DefaultTableModel dataModel = new DefaultTableModel(header, 0);

		// Add Row
		for(int i = 0, l = list.size(); i < l; i++){
			Vector<Object> row = new Vector<Object>();
			row.add(list.get(i).getEmployeeID());
			row.add(list.get(i).getRoleID());
			row.add(list.get(i).getName());
			row.add(list.get(i).getUsername());
			row.add(list.get(i).getDOB());
			row.add(list.get(i).getSalary());
			row.add(list.get(i).getStatus());
			row.add(list.get(i).getPassword());
			dataModel.addRow(row);
		}

		table.setModel(dataModel);
	}

	@Override
	protected void configure_panelButtonBot() {
		btnInsert = new JButton("Insert");
		panelButtonBot.add(btnInsert);
		btnUpdate = new JButton("Update");
		panelButtonBot.add(btnUpdate);
		btnFire = new JButton("Fire");
		panelButtonBot.add(btnFire);

		// Specify Role
		String customButtonName1 = "";
		// System.out.println(LoginHandler.getInstance().getLoggedRoleID());
		if(LoginHandler.getInstance().getLoggedRoleID() == RoleHandler.HUMANRESOUCE_ID){
			customButtonName1 = "Reset Password";
		}
		else if(LoginHandler.getInstance().getLoggedRoleID() == RoleHandler.MANAGER_ID){
			customButtonName1 = "View Report";
		}
		customButton1 = new JButton(customButtonName1);
		panelButtonBot.add(customButton1);
	}

	@Override
	protected String setTableTitle() {
		return "Manage Employee";
	}

	@Override
	protected void addListener() {
		// Connect Btn with actionlistnener
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnFire.addActionListener(this);
		
		customButton1.addActionListener(this);


		// Click and select Feature
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = table.getSelectedRow();

				selectedEmploID = table.getValueAt(row, 0).toString();
				selectedEmploRole = table.getValueAt(row, 1).toString();
				selectedEmploName = table.getValueAt(row, 2).toString();
				selectedEmploUsername = table.getValueAt(row, 3).toString();
				selectedEmploDOB = table.getValueAt(row, 4).toString();
				selectedEmploSalary = table.getValueAt(row, 5).toString();
				selectedEmploStatus = table.getValueAt(row, 6).toString();
				selectedEmploPassword = table.getValueAt(row, 7  ).toString();
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnInsert ){
			insert();
		}
		else if(e.getSource() == btnUpdate){
			update();
		}
		else if(e.getSource() == btnFire){
			fire();
		}
		
		// Custom Button Action (Role Specific)
		else if(e.getSource() == customButton1){
			if(LoginHandler.getInstance().getLoggedRoleID() == RoleHandler.HUMANRESOUCE_ID){
				resetPassword();
			}
			else if(LoginHandler.getInstance().getLoggedRoleID() == RoleHandler.MANAGER_ID){
				viewReport();
			}
		}
	}

	private void insert(){
		// Setting Fields
        JTextField empRole_pop = new JTextField(selectedEmploRole);
		JTextField empName_pop = new JTextField(selectedEmploName);
		JTextField empUsername_pop = new JTextField(selectedEmploUsername);
		JTextField empDOB_pop = new JTextField(selectedEmploDOB);
		JTextField empSalary_pop = new JTextField(selectedEmploSalary);


		JComponent[] inputs = new JComponent[]{
				new JLabel("Role ID: (1:Manager, 2:HR, 3:Storage, 4:Promo, 5:Cashier)"),
				empRole_pop,
                new JLabel("Name:"),
                empName_pop,
				new JLabel("Username:"),
				empUsername_pop,
				new JLabel("Valid Date (YYYY-MM-DD):"),
				empDOB_pop,
				new JLabel("Salary:"),
				empSalary_pop
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Insert New Employee", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){

			// Access to Database
			if(EmployeeHandler.getInstance().addEmployee(empRole_pop.getText(), 
														empName_pop.getText(),
														empUsername_pop.getText(), 
														empDOB_pop.getText(),
														empSalary_pop.getText()) == null)
			 {
				JOptionPane.showMessageDialog(null,"Fail to Insert. Please check the data.");
				return;
			 }
			clearHiddenFields();
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}

	public void update(){
		if(selectedEmploID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select any Employee first");
			return;
		}
		// Setting Fields
		JTextField empID_pop = new JTextField(selectedEmploID);
		empID_pop.setEditable(false);
        JTextField empRole_pop = new JTextField(selectedEmploRole);
		JTextField empName_pop = new JTextField(selectedEmploName);
		JTextField empUsername_pop = new JTextField(selectedEmploUsername);
		JTextField empDOB_pop = new JTextField(selectedEmploDOB);
		JTextField empSalary_pop = new JTextField(selectedEmploSalary);

		JComponent[] inputs = new JComponent[]{
				new JLabel("Employee ID:"),
				empID_pop,
				new JLabel("Role:"),
				empRole_pop,
				new JLabel("Name:"),
				empName_pop,
				new JLabel("Username:"),
				empUsername_pop,
				new JLabel("Valid Date (YYYY-MM-DD):"),
				empDOB_pop,
				new JLabel("Salary:"),
				empSalary_pop
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Update Existing Employee", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			if(empID_pop.getText().equals("")){
				JOptionPane.showMessageDialog(null,"ID Cannot Be Empty");
				return;
			}
			// Access to Database
			if(EmployeeHandler.getInstance().updateEmployee(empID_pop.getText(), 
														empRole_pop.getText(), 
														empName_pop.getText(), 
														empUsername_pop.getText(), 
														empDOB_pop.getText(), 
														empSalary_pop.getText())  == null)
			{
				JOptionPane.showMessageDialog(null,"Fail to Update. Please check the data.");
				return;
			};
			clearHiddenFields();
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}

	public void fire(){
		if(selectedEmploID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select any Employee first");
			return;
		}
		JComponent[] inputs = new JComponent[]{
			new JLabel("Are you sure want to Fire this employee?"),
			new JLabel("Employee ID:"),
			new JLabel(selectedEmploID),
			new JLabel("Role:"),
			new JLabel(selectedEmploRole),
			new JLabel("Name:"),
			new JLabel(selectedEmploName),
			new JLabel("Username:"),
			new JLabel(selectedEmploUsername),
			new JLabel("Valid Date (YYYY-MM-DD):"),
			new JLabel(selectedEmploDOB),
			new JLabel("Salary:"),
			new JLabel(selectedEmploSalary)

		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Fire Employee", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			System.out.println("OK SELECTED");

			// Access to Database
			if(EmployeeHandler.getInstance().fireEmployee(selectedEmploID) == null){
				JOptionPane.showMessageDialog(null,"Fail to Fire. Please check the data.");
			}
			clearHiddenFields();
		}else{
			System.out.println("Fire Canceled");
		}
		loadDataFromDataBase();
	}

	public void resetPassword(){
		if(selectedEmploID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select any Employee first");
			return;
		}
		JTextField empID_pop = new JTextField(selectedEmploID);
		empID_pop.setEditable(false);
		JTextField empRole_pop = new JTextField(selectedEmploRole);
		empRole_pop.setEditable(false);
		JTextField empName_pop = new JTextField(selectedEmploName);
		empName_pop.setEditable(false);
		JTextField empUsername_pop = new JTextField(selectedEmploUsername);
		empUsername_pop.setEditable(false);

		JComponent[] inputs = new JComponent[]{
				new JLabel("Reset Password of This Employee?"),
				new JLabel("Employee ID:"),
				empID_pop,
				new JLabel("Role:"),
				empRole_pop,
				new JLabel("Name:"),
				empName_pop,
				new JLabel("Username:"),
				empUsername_pop,
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Reset Password", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			// Access to Database
			Employee res = EmployeeHandler.getInstance().resetPassword(empID_pop.getText());
			if(res == null){
				JOptionPane.showMessageDialog(null,"Fail to Fire. Please check the data.");
				return;
			}
			String newPassword = res.getPassword();
			JOptionPane.showMessageDialog(null, "New Password: " + newPassword);

			clearHiddenFields();
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
		
	}

	public void viewReport(){
		TransactionHandler.getInstance().viewTransactionReportForm();
	}


	private void clearHiddenFields(){
		// Clear Selected
		selectedEmploID = "";
		selectedEmploRole = "";
		selectedEmploName = "";
		selectedEmploDOB = "";
		selectedEmploSalary = "";
		selectedEmploStatus = "";
		selectedEmploPassword = "";
	}

}
