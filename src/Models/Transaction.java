package Models;

import java.sql.Date;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class Transaction extends AbstractModel {

	private int transactionID;
	private String purchase_datetime;
	private int voucherID;
	private int employeeID;
	private String paymentType;
	
	public Transaction() {
		super("Transaction");
	}

	
	/** For addTransaction()
	 * 	id & date set Automaticly;
	*/
	public Transaction(int voucherID, int employeeID, String paymentType){
		super("Transaction");
		this.voucherID = voucherID;
		this.employeeID = employeeID;
		this.paymentType = paymentType;
	}

	@Override
	protected Transaction fill(ResultSet rs) {
		Transaction transaction = new Transaction();
		try {
			transaction.transactionID = rs.getInt("transactionID");
			transaction.purchase_datetime = rs.getString("purchase_datetime");
			transaction.voucherID = rs.getInt("voucherID");
			transaction.employeeID = rs.getInt("employeeID");
			transaction.paymentType = rs.getString("paymentType");
			return transaction;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* DEFINE Method in Class Diagram */
	/**Add Transaction to database */
	public Transaction addTransaction(){
		String query = String.format("INSERT INTO %s (purchase_datetime, voucherID, employeeID, paymentType)"
		+ "VALUES (?, ?, ?, ?)", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			// Auto Date
			// Get NOw
			Date date = new Date(new java.util.Date().getTime());
			purchase_datetime = date.toString();

			statement.setString(1, purchase_datetime);
			statement.setInt(2, voucherID);
			statement.setInt(3, employeeID);
			statement.setString(4, paymentType);
			statement.execute();
			
			//Get inserted ID
			 ResultSet rs = (ResultSet) connect.executeQuery(String.format("SELECT MAX(transactionID) as ID FROM %s", tableName));
			rs.next();
			transactionID = rs.getInt("ID"); 

			return this;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<Transaction> getAllTransaction(){
		String query = String.format("SEELCT * FROM %s", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);

		try {
			ResultSet rs = (ResultSet) statement.executeQuery();
			Vector<Transaction> list = new Vector<Transaction>();
			while(rs.next()){
				list.add(this.fill(rs));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<Transaction> getTransactionReport(int month, int year){
		String query = String.format("SELECT * FROM %s WHERE MONTH(purchase_datetime) = ? AND YEAR(purchase_datetime) = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);

		try {
			statement.setInt(1, month);
			statement.setInt(2, year);
			ResultSet rs = (ResultSet) statement.executeQuery();

			Vector<Transaction> list = new Vector<Transaction>();
			while(rs.next()){
				list.add(this.fill(rs));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/*--- SETTERS / GETTERS ---*/
	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getpurchase_datetime() {
		return purchase_datetime;
	}

	public void setpurchase_datetime(String purchase_datetime) {
		this.purchase_datetime = purchase_datetime;
	}

	public int getVoucherID() {
		return voucherID;
	}

	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


}
