package Models;

import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class TransactionItem extends AbstractModel {

	private int transactionID;
	private int productID;
	private int quantity;

	public TransactionItem() {
		super("TransactionItem");
	}

	@Override
	protected TransactionItem fill(ResultSet rs) {
		TransactionItem transItem = new TransactionItem();
		try {
			transItem.transactionID = rs.getInt("transactionID");
			transItem.productID = rs.getInt("productID");
			transItem.quantity = rs.getInt("quantity");
			return transItem;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*--- DEFINE Method in Class Diagram ---*/
	public TransactionItem addTransactionItem(){
		String query = String.format("INSERT INTO %s (transactionID, productID, quantity) VALUES (?, ?, ?)", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		try {
			statement.setInt(1, transactionID);	
			statement.setInt(2, productID);	
			statement.setInt(3, quantity);	
			statement.execute();
			return this;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vector<TransactionItem> getTransactionItem(int transactionID){
		String query = String.format("SELECT * FROM %s WHERE transactionID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		try {
			statement.setInt(1, transactionID);	
			ResultSet rs = (ResultSet) statement.executeQuery();
			Vector<TransactionItem> list = new Vector<>();
			while(rs.next()){
				list.add(this.fill(rs));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--- SETTERS / GETTER ---*/
	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

	
}
