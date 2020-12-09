package Models;

import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class Role extends AbstractModel{

	private int roleID;
	private String name;
	
	public Role() {
		super("role");
	}

	/* DEFINE Method in Class Diagram */
	public Role getRole(int roleID) {
		String query = String.format("SELECT * FROM %s WHERE roleID = ?",tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			statement.setInt(1, roleID);
			ResultSet rs = (ResultSet) statement.executeQuery();
			while(rs.next()) {
				this.name = rs.getString("name");
				this.roleID = roleID;
			}
			return this;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Role> getAllRole(){
		String query = String.format("SELECT * FROM %s",tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			Vector<Role> list = new Vector<Role>();
			ResultSet rs = (ResultSet) statement.executeQuery();
			while(rs.next()) {
				list.add(fill(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Role fill(ResultSet rs) {
		Role role = new Role();
		try {
			role.roleID = rs.getInt("roleID");
			role.name = rs.getString("name");
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--- GETTER ---*/
	public int getRoleID() {
		return roleID;
	}

	public String getName() {
		return name;
	}

}
