package Controllers;

import java.sql.Date;
import java.util.Random;
import java.util.Vector;

import Models.Employee;
import Views.ManageEmployeeForm;

public class EmployeeHandler{

	private static EmployeeHandler controller;
	private Employee employee;
	
	public EmployeeHandler() {
		employee = new Employee();
	}

	//Singleton
	public static EmployeeHandler getInstance() {
		if(controller == null) {
			controller = new EmployeeHandler();
		}
		return controller;
	}
	
	/* --DEFINE Method in Class Diagram --*/
	public void viewManageEmployeeForm() {
		new ManageEmployeeForm();
	}
	
	public Vector<Employee> getAllEmployee(){
		return employee.getAllEmployee();
	}
	
	/**All using String inorder to achive validation in Handler */
	public Employee addEmployee(String roleID_txt, String name_txt, String username_txt, String DOB_txt, String salary_txt) {
		if(validateRoleID(roleID_txt)) return null;
		if(validateName(name_txt)) return null;
		if(validateUsername(username_txt)) return null;
		if(validateDOB(DOB_txt)) return null;
		if(validateSalary(salary_txt)) return null;
		//Username must be unique
		Vector<Employee> list = employee.getAllEmployee();
		for(int i = 0, l = list.size(); i < l; i++){
			if(list.get(i).getUsername().equals(username_txt)) return null;
		}

		// Preparing Data
		int roleID, salary;
		String password;
		roleID = Integer.parseInt(roleID_txt);
		salary = Integer.parseInt(salary_txt);
		password = generatePassword();

		// Accessing Model
		Employee employee = new Employee();
		employee.setRoleID(roleID);
		employee.setName(name_txt);
		employee.setUsername(username_txt);
		employee.setDOB(DOB_txt);
		employee.setSalary(salary);
		employee.setPassword(password);
		employee.setStatus("Active");
		return employee.insertEmployee();
	}
	
	public Employee updateEmployee(String employeeID_txt, String roleID_txt, String name_txt, String username_txt, String DOB_txt, String salary_txt) {
		// Validation Check
		if(validateEmployeeID(employeeID_txt)) return null;
		if(validateRoleID(roleID_txt)) return null;
		if(validateName(name_txt)) return null;
		if(validateUsername(username_txt)) return null;
		if(validateDOB(DOB_txt)) return null;
		if(validateSalary(salary_txt)) return null;
		
		// Unique (Not same as Other, but can be same as now)
		Vector<Employee> list = employee.getAllEmployee();
		for(int i = 0, l = list.size(); i < l; i++){
			if(list.get(i).getEmployeeID() == Integer.parseInt(employeeID_txt)) continue;
			if(list.get(i).getUsername().equals(username_txt)) return null;
		}

		// Preparing Data
		int employeeID, roleID, salary;
		employeeID = Integer.parseInt(employeeID_txt);
		roleID = Integer.parseInt(roleID_txt);
		salary = Integer.parseInt(salary_txt);

		// Accessing Model
		Employee employee = this.employee.getEmployee(employeeID);
		
		//Update
		employee.setName(name_txt);
		employee.setUsername(username_txt);
		employee.setDOB(DOB_txt);
		employee.setSalary(salary);
		employee.setRoleID(roleID);
		return employee.UpdateEmployee(employeeID);
	}
	
	
	public Employee resetPassword(String employeeID_txt) {
		// Validating
		if(validateEmployeeID(employeeID_txt)) return null;
		
		// Preparing Data
		int employeeID = Integer.parseInt(employeeID_txt);

		// Accessing Model
		Employee employee = this.employee.getEmployee(employeeID);
		
		//Update
		employee.setPassword(generatePassword());

		// Update DB
		return employee.UpdateEmployee(employeeID);
	}
	
	public Employee fireEmployee(String employeeID_txt) {
		// Validating
		if(validateEmployeeID(employeeID_txt)) return null;

		// Preparing Data
		int employeeID = Integer.parseInt(employeeID_txt);

		//Get
		Employee employee = this.employee.getEmployee(employeeID);

		//Update
		employee.setStatus("Fired");
		return employee.UpdateEmployee(employeeID);
	}

	// Validation Functions
	/**  List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * 	<li>Must be an Integer </li>
	 * 	<li>Must Exist </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateEmployeeID(String employeeID){
		// Cannot Be Empty
		System.out.println("Fail in EmployeeID");
		if(employeeID.equals("")) return true;
		int ID;
		try {
			ID = Integer.parseInt(employeeID);
		} catch (Exception e) {
			return true;
		}
		// Must be Exist
		if(employee.getEmployee(ID) == null) return true;
		return false;
	}
	
	/**  
	 * List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * 	<li>Must be an Integer </li>
	 * 	<li>Must Exist </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateRoleID(String roleID){
		// Cannot be Empty
		if(roleID.equals("")) return true;
		// must Exist
		if(RoleHandler.getInstance().getRole(Integer.parseInt(roleID)) == null) return true;
		return false;
	}

	/**  
	 * List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateName(String name){
		//Cannot Be empty
		if(name.equals("")) return true;
		return false;
	}

	/**  
	 * List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateUsername(String username){
		// Cannot be Empty
		if(username.equals("")) return true;
		return false;
	}

	/**  
	 * List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateDOB(String DOB){
		//Cannot be empty
		if(DOB.equals("")) return true;

		// Must be in the past
		//	is	      DOB    after								Today ? ? ?
		if(Date.valueOf(DOB).after((new Date(new java.util.Date().getTime()))))return true;
		return false;
	}

	/**  
	 * List Check:
	 * <ul>
	 * 	<li>Cannot Be empty </li>
	 * <li>Must be an integer </li>
	 * <li>Must be above zero </li>
	 * </ul>
	 * <br>
	*/
	private boolean validateSalary(String salary){
		// Cannot be empty
		if(salary.equals("")) return true;

		// Must be an integer
		int sal;
		try {
			sal = Integer.parseInt(salary);
		} catch (Exception e) {
			return true;
		}

		// Must above zero
		if(sal <= 0) return true;

		return false;
	}

	//Additional method
	private String generatePassword(){
		String password = "";
		Random rand = new Random();
		for(int i = 0, len = 5; i < len; i++){
			password += (char) (rand.nextInt(25) + (int) 'A');
		}
		return password;
	}
}