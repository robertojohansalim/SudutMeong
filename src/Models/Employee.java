package Models;

import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class Employee extends AbstractModel {

	private int employeeID = -1;
	private int roleID = -1;
	private String name = "";
	private String username = "";
	private String DOB = "";
	private int salary = 0;
	private String status = "";
	private String password = "";
	
	public Employee() {
		super("Employee");
	}

	/* DEFINE Method in Class Diagram */
	public Vector<Employee> getAllEmployee(){
		String query = String.format("SELECT * FROM %s", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			ResultSet rs = (ResultSet) statement.executeQuery();
			Vector<Employee> list = new Vector<Employee>();
			while(rs.next()) {
				list.add(this.fill(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Employee getEmployee(int employeeID) {
		String query = String.format("SELECT * FROM %s WHERE employeeID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			statement.setInt(1, employeeID);
			ResultSet rs = (ResultSet) statement.executeQuery();
			rs.next();
			return this.fill(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Employee insertEmployee() {
		// connect.executeQuery("BEGIN TRANSACTION");
		String query = String.format("INSERT INTO %s (employeeID, roleID, name, username, DOB, salary, status, password)"
				+ "VALUE (?, ?, ?, ?, ?, ?, ?, ?)", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			//Get last inserted ID;
			ResultSet rs = (ResultSet) connect.executeQuery(String.format("SELECT MAX(employeeID) as ID FROM %s", tableName));
			rs.next();
			employeeID = rs.getInt("ID");
			employeeID += 1;
			
			// Auto Increase
			statement.setInt(1, employeeID);
			statement.setInt(2, roleID);
			statement.setString(3, name);
			statement.setString(4, username);
			statement.setString(5, DOB);
			statement.setInt(6, salary);
			statement.setString(7, status);
			statement.setString(8, password);
			statement.execute();
			
			//Get last inserted ID;
//			employeeID = connect.executeQuery("SELECT MAX(employeeID) FROM Employee").getInt("employeeID");
			
			// connect.executeQuery("COMMIT");
			return this;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// connect.executeQuery("ROLLBACK");
		return null;
	}
	
	public Employee UpdateEmployee(int employeeId) {
		this.employeeID = employeeId;
		
		String query = String.format("UPDATE %s SET roleID = ?, name = ?, username = ?, DOB = ?, salary = ?, status = ?, password = ? WHERE employeeID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			statement.setInt(8, employeeId);
			statement.setInt(1, this.roleID);
			statement.setString(2, this.name);
			statement.setString(3, this.username);
			statement.setString(4, this.DOB);
			statement.setInt(5, this.salary);
			statement.setString(6, this.status);
			statement.setString(7, this.password);
			statement.execute();
			return this;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected Employee fill(ResultSet rs) {
		Employee employee = new Employee();
		try {
			employee.employeeID = rs.getInt("employeeID");
			employee.roleID = rs.getInt("roleID");
			employee.name = rs.getString("name");
			employee.username = rs.getString("username");
			employee.DOB = rs.getString("DOB");
			employee.salary = rs.getInt("salary");
			employee.status = rs.getString("status");
			employee.password = rs.getString("password");
			return employee;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--- GETTERS ---*/
	public int getEmployeeID() {
		return employeeID;
	}


	public int getRoleID() {
		return roleID;
	}


	public String getName() {
		return name;
	}


	public String getUsername() {
		return username;
	}


	public String getDOB() {
		return DOB;
	}


	public int getSalary() {
		return salary;
	}


	public String getStatus() {
		return status;
	}


	public String getPassword() {
		return password;
	}
	
	/*--- SETTERS ---*/
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
