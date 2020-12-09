package Models;

import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class Voucher extends AbstractModel {

	private int voucherID;
	private float discount;
	private String validDate;
	private String status;		

	public Voucher() {
		super("Voucher");
	}
	/**Constructor to Insert new Voucher */
	public Voucher(float discount, String validDate, String status){
		super("Voucher");
		this.discount = discount;
		this.validDate = validDate;
		this.status = status;
	}

	@Override
	protected Voucher fill(ResultSet rs) {
		Voucher vouch = new Voucher();
		try {
			vouch.voucherID = rs.getInt("voucherID");
			vouch.discount = rs.getFloat("discount");
			vouch.validDate = rs.getString("validDate");
			vouch.status = rs.getString("status");
			return vouch;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/* DEFINE Method in Class Diagram */
	public Vector<Voucher> getAllVoucher(){
		String query = String.format("SELECT * FROM %s", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			ResultSet rs = (ResultSet) statement.executeQuery();
			Vector<Voucher> list = new Vector<Voucher>();
			while(rs.next()) {
				list.add(this.fill(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Voucher getVoucher(int voucherID){
		String query = String.format("SELECT * FROM %s WHERE voucherID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			statement.setInt(1, voucherID);
			ResultSet rs = (ResultSet) statement.executeQuery();
			rs.next();
			return this.fill(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Voucher insertVoucher(){
		String query = String.format("INSERT INTO %s (discount, validDate, status)"
		+ "VALUE (?, ?, ?)", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			// Auto Increase
			statement.setFloat(1, discount);
			statement.setString(2, validDate);
			statement.setString(3, status);
			statement.execute();
			
			//Get inserted ID
			// ResultSet rs = (ResultSet) connect.executeQuery(String.format("SELECT MAX(productID) as ID FROM %s", tableName));
			// rs.next();
			// voucherID = rs.getInt("ID"); 
			return this;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Voucher updateVoucher(){
		String query = String.format("UPDATE %s SET discount = ?, validDate = ?, status = ? WHERE voucherID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);

		try {
			statement.setFloat(1, discount);
			statement.setString(2, validDate);
			statement.setString(3, status);
			statement.setInt(4, voucherID);
			statement.execute();

			return this;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteVoucher(){
		String query = String.format("DELETE FROM %s WHERE voucherID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		try {
			statement.setInt(1, voucherID);
			statement.execute();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*--- SETTERS / GETTERS ---*/	
	public int getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	/**Status: "valid" or "used" */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
