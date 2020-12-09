package Controllers;

import java.util.Vector;

import Models.CartItem;
import Models.Product;
import Models.TransactionItem;
import Views.AddToCartForm;
import Views.TransactionForm;

public class CartHandler {

	private Vector<CartItem> listCart;
	private static CartHandler controller;

	private CartHandler() {
		listCart =  new Vector<>();
	}

	public static CartHandler getInstance(){
		if(controller == null){
			controller = new CartHandler();
		}
		return controller;
	}

	/* DEFINE Method in Class Diagram */
	public CartItem addToCart(int productID, int quantity){
		// Calling Other Controller (Product)
		Product product = ProductHandler.getInstance().getProduct(productID);

		// Create Cart Item entitiy
		CartItem item = new CartItem(product, quantity);
		this.listCart.add(item);
		//Returning Entity;
		return item;
	}
	public void viewAddToCartForm(){
		new AddToCartForm();
	}

	public void viewPurchaseForm(){
		new TransactionForm();
	}

	public CartItem updateStock(int productID, int quantity){
		/*
		 * Dari addToChart Sequence Diagram 
		 * updateStock dipake KALO ----> Udah ada di dalem Cart
		 */
		//Find Product in List
		CartItem item;
		if(listCart != null){
			for(int i = 0, l = listCart.size(); i < l; i++){
				item = listCart.get(i);
				if(item.getProduct().getProductId() == productID){
					//Update
					item.setQuantity(quantity);
	
					return item;
				}
			}
		}

		return null;
	}

	// Additional Methods not mention in the Class Diagram
	/**Returns List of CartItem */
	public Vector<CartItem> getAllItem(){
		return listCart;
	}
	/**Process Items in cart:
	 * <ul>
	 * <li> Update Stock  </li>
	 * <li> Insert Database </li>
	 * <li> Emplty Cart </li>
	 * </ul>
	 * Returns List of TranasctionItem
	 * <br>
	 */
	public Vector<TransactionItem> processCartItem(int transactionID){
		Vector<TransactionItem> listTI =  new Vector<>();
		for(int i = 0, l = listCart.size(); i < l; i++){
			CartItem tmpCart = listCart.get(i);
			int productID = tmpCart.getProduct().getProductId();
			int quantity = tmpCart.getQuantity();
			
			// Use Stock
			ProductHandler.getInstance().useStock(productID, quantity);

			// Add Transaction Item
			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setTransactionID(transactionID);
			transactionItem.setProductID(productID);
			transactionItem.setQuantity(quantity);

			transactionItem.addTransactionItem();
			listTI.add(transactionItem);
		}
		listCart.removeAllElements();
		return listTI;
	}
}
