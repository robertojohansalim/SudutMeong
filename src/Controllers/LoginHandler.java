package Controllers;

import java.util.Vector;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Models.Employee;
import Views.Login;

public class LoginHandler {

	private static LoginHandler controller;
	
	//Active Session if needed :)
	private boolean isLogged;
	private int employeeID;
	private String employeeName;
	private int roleID;

	public LoginHandler() {
		isLogged = false;
	}

	public static LoginHandler getInstance(){
		if(controller == null){
			controller = new LoginHandler();
		}
		return controller;
	}

	public void viewLogin(){
		isLogged = false;
		employeeID = 0;
		employeeName = "";
		roleID = 0;
		System.out.println("Calling Login");
		new Login();
	}

	public boolean login_user(JTextField username_txt, JPasswordField password_txt){
		System.out.println(password_txt.getPassword());
		char pass[] = password_txt.getPassword();
		String username = username_txt.getText();
		String password = "";
		for(int i = 0, l = pass.length; i < l ; i++){
			password += pass[i];
		}
		Vector<Employee> employees = EmployeeHandler.getInstance().getAllEmployee();
		isLogged = false;
		if(employees != null){
			for(int i = 0, l = employees.size() ; i < l ; i++){
				Employee tmp = employees.get(i);
				System.out.println(username + " = " + tmp.getUsername() + " | " + password + " + " + tmp.getPassword());
				if(tmp.getUsername().equals(username) && tmp.getPassword().equals(password)){
					if(tmp.getStatus().equalsIgnoreCase("Fired")){
						return false;
					}
					this.isLogged = true;
					this.employeeID = tmp.getEmployeeID();
					this.roleID = tmp.getRoleID();
					this.employeeName = tmp.getName();
					return this.isLogged;
				}
			}
		}
		return false;
	}

	public void login_user(int employeeID, int roleID){
		System.out.println("Handler ID: " + roleID);
		this.isLogged = true;
		this.employeeID = employeeID;
		this.roleID = roleID;
		this.employeeName = "Anonymus";
	}


	public void goToRolePage(){
		if(employeeID == 0){
			return;
		}
		int id = roleID;
		System.out.println("ID : " + id);
		if(id == RoleHandler.MANAGER_ID){
			EmployeeHandler.getInstance().viewManageEmployeeForm();
		}
		else if(id == RoleHandler.HUMANRESOUCE_ID){
			EmployeeHandler.getInstance().viewManageEmployeeForm();
		}
		else if(id == RoleHandler.STORAGEMANAGER_ID){
			ProductHandler.getInstance().viewManageProductForm();
		}
		else if(id == RoleHandler.PROMOMANAGER_ID){
			VoucherHandler.getInstance().viewManageVoucherForm();
		}
		else if(id == RoleHandler.CASHIER_ID){
			CartHandler.getInstance().viewPurchaseForm();
		}
	}
//	GETTERS
	public boolean isLogged() {
		return isLogged;
	}

	public int getLoggedEmployeeID() {
		return employeeID;
	}

	public int getLoggedRoleID() {
		return roleID;
	}

	public String getLoggedName(){
		return employeeName;
	}
}
