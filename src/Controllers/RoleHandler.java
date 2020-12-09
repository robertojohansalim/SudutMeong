package Controllers;

import java.util.Vector;

import Models.Role;

public class RoleHandler{

	public static int MANAGER_ID = 1;
	public static int HUMANRESOUCE_ID = 2;
	public static int STORAGEMANAGER_ID = 3;
	public static int PROMOMANAGER_ID = 4;
	public static int CASHIER_ID = 5;
	
	private static RoleHandler controller;
	private Role role;
	
	public RoleHandler() {
		role = new Role();
	}

	//Singleton
	public static RoleHandler getInstance() {
		if(controller == null) {
			controller = new RoleHandler();
		}
		return controller;
	}
	
	/* --DEFINE Method in Class Diagram --*/
	public Vector<Role> getAllRole() {
		return role.getAllRole();
	}
	
	public Role getRole(int roleID) {
		return role.getRole(roleID);
	}
}
