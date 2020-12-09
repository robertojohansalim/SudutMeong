package Controllers;

import java.sql.Date;
import java.util.Vector;


import Models.CartItem;
import Models.Product;
import Models.Transaction;
import Models.TransactionItem;
import Models.Voucher;
import Views.PaymentForm;
import Views.PaymentMethodForm;
import Views.TransactionReport;

public class TransactionHandler {

	private static TransactionHandler controller;
	private Transaction transaction;
	private TransactionItem transactionItem;

	// Additional Attribute not mentioned in Class Diagra,
	// Total Price do not canges according to discount
	private int totalPrice = 0;
	private float discount = 0;
	private int voucherID = 0;

	private TransactionHandler() {
		transaction = new Transaction();
		transactionItem = new TransactionItem();
	}

	public static TransactionHandler getInstance(){
		if(controller == null){
			controller = new TransactionHandler();
		}
		return controller;
	}

	/*--- DEFINE Method in Class Diagram ---*/
	// Process Transaction in Sequence Diagram have additional parameter money
	// And Return Changes (?)
	/**Add Transaction to DB, Process Product Stock with Cart, Add TransactionItem to DB */
	public Transaction processTransaction(String paymentType, String money){
		// CalculateChanges()
		System.out.println("Process Transaction");
		int employeeID = LoginHandler.getInstance().getLoggedEmployeeID();
		int changes;
		try {
			changes = calculateChange(Integer.parseInt(money));
		} catch (Exception e) {
			return null;
		}
		if(changes < 0){
			return null;
		}
		// 		useVoucher --> VoucherHandler
		if(voucherID != 0){
			VoucherHandler.getInstance().useVoucher(voucherID);
		}

		// addTransaction --> Transaction
		Transaction transaction = new Transaction(voucherID, employeeID, paymentType);
		transaction = transaction.addTransaction();
		int transactionID = transaction.getTransactionID();

//--------------------------------------
//GANTI KE ChartHandler LOL
//--------------------------------------
		CartHandler.getInstance().processCartItem(transactionID);

		this.voucherID = 0;
		this.totalPrice = 0;
		this.discount = 0;

		return transaction;
	}

	/**
	 * get Transaction details by given transaciton ID
	 */
	public Vector<TransactionItem> getTransactionDetail(int transactionID){
		return transactionItem.getTransactionItem(transactionID);

	}

	public Vector<Transaction> getTransactionReport(String month_txt, String year_txt){
		int month, year;
		try {
			month = Integer.parseInt(month_txt);
			year = Integer.parseInt(year_txt);
		} catch (Exception e) {
			return null;
		}
		return transaction.getTransactionReport(month, year);
	}

	/**
	 * See Check Out
	 */
	// This Return Total Price but havent Calculate Price?
	/**Set Voucher to Transaction Handler (-1 Voucher ID means unasign) */
	public int applyVoucher(String voucherId){
		// prepare data
		int voucherID;
		try {
			voucherID = Integer.parseInt(voucherId);
		} catch (Exception e) {
			return totalPrice;
		}

		//Find Voucher in Voucher DB
		Voucher voucher = VoucherHandler.getInstance().getVoucher(voucherID);

		// if Foucher ID not exist
		if(voucher == null){
			System.out.println("Voucher Not Found");
			return totalPrice;
		} 
		// Valid Date
		//if(//Today . after. date)
		// apakah Valid Date 
		//				Due Date								Today
		if(Date.valueOf(voucher.getValidDate()).before(  (new Date(new java.util.Date().getTime())))){
			System.out.println("Voucher Date invalid");
			return totalPrice;
		}

		// Status
		if(!voucher.getStatus().equalsIgnoreCase("not used")){
			System.out.println("Voucher Used");
			return totalPrice;
		}
		//Calculate Total Price (?)
		this.discount = voucher.getDiscount();
		this.voucherID = voucher.getVoucherID();
		totalPrice =  calculateTotalPrice();
		return getDiscountedPrice();
	}
	
	public void viewTransactionReportForm(){
		new TransactionReport();
	}

	// Additional Methods not mention in the Class Diagram
	public void viewPaymentMethodForm(){
		new PaymentMethodForm();
	}

	public void viewPaymentForm(){
		new PaymentForm();
	}
	
	public int addProdcutToCart(String productID_txt, String quantity_txt){
		int quantity;
		int productID;
		try {
			productID = Integer.parseInt(productID_txt);
			quantity = Integer.parseInt(quantity_txt);
		} catch (Exception e) {
			return -2;
		}
		Product prod = ProductHandler.getInstance().getProduct(productID);
		if(prod == null || quantity < 0 || prod.getStock() < quantity){
			return -1;
		}
		if(CartHandler.getInstance().updateStock(productID, quantity) == null){
			CartHandler.getInstance().addToCart(productID, quantity);
		}
		totalPrice = calculateTotalPrice();
		return totalPrice;
	}

	public int calculateTotalPrice(){
		this.totalPrice = 0;

		//Accessing Cart using Additional method   getAllItem():Vector<CartItem>
		if(CartHandler.getInstance().getAllItem() != null && CartHandler.getInstance().getAllItem().size() != 0){
			Vector<CartItem> cartlist =  CartHandler.getInstance().getAllItem();
			for(int i = 0, l = cartlist.size(); i < l; i++){
				this.totalPrice += cartlist.get(i).getProduct().getPrice() * cartlist.get(i).getQuantity();
			}
		}

		// //Calculate Discount
		// this.totalPrice *= (1.0 - discount);
		return this.totalPrice;
	}

	public int calculateChange(int money){
		/**
		 * returns -1 if invalid
		 */
		int changes = money - totalPrice;
		if(totalPrice < 0){
			return -1;
		}
		return changes;
	}

	public int getDiscountedPrice(){
		return (int) (this.totalPrice * (1.0 - discount));
	}
}

