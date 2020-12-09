package Controllers;

import java.util.Vector;

import Models.Product;
import Views.ManageProductForm;

public class ProductHandler {

	private static ProductHandler controller;
	private Product product;

	public ProductHandler() {
		product = new Product();
	}

	public static ProductHandler getInstance(){
		if(controller == null){
			controller = new ProductHandler();
		}
		return controller;
	}

	/* DEFINE Method in Class Diagram */
	public void viewManageProductForm(){
		// Something to do with View that 
		new ManageProductForm(); 
	}

	public Vector<Product> getAllProduct(){
		return product.getAllProduct();
	}

	public Product getProduct(int productID){
		return product.getProduct(productID);
	}

	public Product addProduct(String name_txt, String description_txt, String price_txt, String stock_txt){
		// Validation
		if(validateProductName(name_txt)) return null;
		if(validateProductDescription(description_txt)) return null;
		if(validateProductPrice(price_txt)) return null;
		if(validateProductStock(stock_txt)) return null;

		//Accessing DB
		Product product = new Product();
		product.setName(name_txt);
		product.setDescription(description_txt);	
		product.setPrice(Integer.parseInt(price_txt));
		product.setStock(Integer.parseInt(stock_txt));
		return product.insertProduct();
	}

	public Product updateProduct(String productID, String name, String description, String price){
		// Validation
		if(validateProductID(productID)) return null;
		if(validateProductName(productID)) return null;
		if(validateProductDescription(productID)) return null;
		if(validateProductPrice(productID)) return null;
		
		//Get Previouse Data
		Product product = this.product.getProduct(Integer.parseInt(productID));

		//Update Value
		product.setName(name);
		product.setDescription(description);
		product.setPrice(Integer.parseInt(price));

		//Update DB
		return product.updateProduct();
	}

	public boolean deleteProduct(String productID){
		if(validateProductID(productID)) return false;
		Product product = new Product();
		product.setProductId(Integer.parseInt(productID));
		return product.deleteProduct();
	}

	public Product resock(int productID, int  quantity){
		if(validateProductID(productID)) return null;

		//Get Product
		Product product = this.product.getProduct(productID);
		//Update Value
		product.setStock(product.getStock() + quantity);

		// Update Database
		return product.updateProduct();
	}

	public Product useStock(int productID, int quantity){
		if(validateProductID(productID)) return null;

		//Get Product
		Product product = this.product.getProduct(productID);
		//Update Value
		product.setStock(product.getStock() - quantity);

		// Update Database
		return product.updateProduct();
	}



	// Validation Function
	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * 	<li> Must Existl</li>
	 * 	<li> Cannot be updated</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateProductID(String productID){
		// Cannot be empty
		if(productID.equals("")) return true;
		// Check for valid Integer
		int ID;
		try {
			ID = Integer.parseInt(productID);
		} catch (Exception e) {
			return true;
		}
		// Must Exist
		if(product.getProduct(ID) == null) return true;

		// Cannot be updated see the Update Method (ID do not Updated)
		return false;
	}
	
	private boolean validateProductID(int productID){
		// Cannot be empty
		if(product.getProduct(productID) == null) return true;

		// Cannot be updated see the Update Method (ID do not Updated)
		return false;
	}
	
	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateProductName (String productName){
		// Cannot be empty
		if(productName.equals("")) return true;
		return false;
	}

	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateProductDescription (String productDescription){
		// Cannot be empty
		if(productDescription.equals("")) return true;
		return false;
	}

	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * 	<li> Must be above zero</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateProductPrice (String productPrice){
		// Cannot be empty
		if(productPrice.equals("")) return true;

		// Valid integer
		int price;
		try {
			price = Integer.parseInt(productPrice);
		} catch (Exception e) {
			return true;
		}	

		// Must be above Zero
		if(price <= 0) return true;

		return false;                                      
	}

	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * 	<li> Must Existl</li>
	 * 	<li> Must be above zero</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateProductStock (String productStock){
		// Cannot be empty
		if(productStock.equals("")) return true;

		// Valid integer
		int stock;
		try {
			stock = Integer.parseInt(productStock);
		} catch (Exception e) {
			return true;
		}	

		// Must be above Zero
		if(stock <= 0) return true;

		return false;
	}
}
