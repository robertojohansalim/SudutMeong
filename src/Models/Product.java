package Models;

import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import AbstractClass.AbstractModel;

public class Product extends AbstractModel {

	private int productId;
	private String name;
	private String description;
	private int price;
	private int stock;


	public Product() {
		super("Product");
	}
	
	@Override
	protected Product fill(ResultSet rs) {
		Product product = new Product();
		try {
			product.productId = rs.getInt("productID");
			product.name = rs.getString("name");
			product.description = rs.getString("description");
			product.price = rs.getInt("price");
			product.stock = rs.getInt("stock");

			return product;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*--- DEFINE Method in Class Diagram ---*/
	public Vector<Product> getAllProduct(){
		String query = String.format("SELECT * FROM %s", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			ResultSet rs = (ResultSet) statement.executeQuery();
			Vector<Product> list = new Vector<>();
			while(rs.next()){
				list.add(this.fill(rs));
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Product getProduct(int productID){
		String query = String.format("SELECT * FROM %s WHERE productID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			statement.setInt(1, productID);
			ResultSet rs = (ResultSet) statement.executeQuery();
			rs.next();
			return this.fill(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Product insertProduct(){
		String query = String.format("INSERT INTO %s (name, description, price, stock)"
		+ "VALUE (?, ?, ?, ?)", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		
		try {
			// Auto Increase
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, price);
			statement.setInt(4, stock);
			statement.execute();
			
			//Get inserted ID
			// ResultSet rs = (ResultSet) connect.executeQuery(String.format("SELECT MAX(productID) as ID FROM %s", tableName));
			// rs.next();
			// productId = rs.getInt("ID"); 
			return this;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Product updateProduct(){
		String query = String.format("UPDATE %s SET name = ?, description = ?, price = ?, stock = ? WHERE productID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);

		try {
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, price);
			statement.setInt(4, stock);
			statement.setInt(5, productId);
			statement.execute();

			return this;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteProduct(){
		String query = String.format("DELETE FROM %s WHERE productID = ?", tableName);
		PreparedStatement statement = (PreparedStatement) connect.prepareStatement(query);
		try {
			statement.setInt(1, productId);
			statement.execute();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*---SETTER GETTER---*/
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
