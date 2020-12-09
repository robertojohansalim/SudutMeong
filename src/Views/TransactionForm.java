package Views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Controllers.CartHandler;
import Controllers.LoginHandler;
import Controllers.TransactionHandler;
import Models.CartItem;
import Models.Product;

import AbstractClass.AbstractView;

public class TransactionForm implements ActionListener {

	JTable tableCart;

	JFrame frame;
	JButton btnLogout, btnPaymentFrom, btnAddToCart, btnVoucher;
	JTextField productID_txt, quantity_txt, totalPrice_txt, voucherID_txt;
	
	public TransactionForm() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(490, 7, 80, 23);
		btnLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				LoginHandler.getInstance().viewLogin();
			}
		});
		frame.getContentPane().add(btnLogout);

		
		JLabel lblWelcomeusername = new JLabel("Welcome, " +  LoginHandler.getInstance().getLoggedName(), SwingConstants.RIGHT);
		lblWelcomeusername.setBounds(145, 11, 340, 14);
		frame.getContentPane().add(lblWelcomeusername);
		
		JLabel lblProductId = new JLabel("Product ID");
		lblProductId.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductId.setBounds(56, 66, 139, 14);
		frame.getContentPane().add(lblProductId);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setBounds(229, 66, 139, 14);
		frame.getContentPane().add(lblQuantity);
		
		btnAddToCart = new JButton("Add to Cart");
		btnAddToCart.setBounds(389, 66, 154, 40);
		btnAddToCart.addActionListener(this);
		frame.getContentPane().add(btnAddToCart);
		
		productID_txt = new JTextField();
		productID_txt.setBounds(56, 86, 139, 20);
		frame.getContentPane().add(productID_txt);
		productID_txt.setColumns(10);
		
		quantity_txt = new JTextField();
		quantity_txt.setBounds(229, 86, 139, 20);
		frame.getContentPane().add(quantity_txt);
		quantity_txt.setColumns(10);
		
		JLabel lblTransactionForm = new JLabel("Transaction Form");
		lblTransactionForm.setHorizontalAlignment(SwingConstants.LEFT);
		lblTransactionForm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTransactionForm.setBounds(10, 7, 367, 34);
		frame.getContentPane().add(lblTransactionForm);
		
		JLabel lblCartItem = new JLabel("Cart Item");
		lblCartItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCartItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblCartItem.setBounds(10, 133, 560, 23);
		frame.getContentPane().add(lblCartItem);

		//Table
		tableCart = new JTable(){
			@Override
			public boolean isCellEditable(int row, int cols){
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(tableCart);
		scrollPane.setBounds(10, 158, 560, 203);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblTotalPrice = new JLabel("Total Price :");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPrice.setBounds(191, 375, 154, 14);
		frame.getContentPane().add(lblTotalPrice);
		
		totalPrice_txt = new JTextField();
		totalPrice_txt.setEditable(false);
		totalPrice_txt.setBounds(355, 372, 215, 20);
		frame.getContentPane().add(totalPrice_txt);
		totalPrice_txt.setColumns(10);
		
		btnPaymentFrom = new JButton("Payment Form");
		btnPaymentFrom.addActionListener(this);
		btnPaymentFrom.setBounds(355, 403, 219, 47);
		frame.getContentPane().add(btnPaymentFrom);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Perform Fired");
		if(e.getSource() == btnAddToCart){
			addItemToCart();
		}
		else if(e.getSource() ==  btnVoucher){
			useVoucher();
		}
		else if(e.getSource() == btnPaymentFrom){
			checkout();
		}
	}

	private void addItemToCart(){
		int result = TransactionHandler.getInstance().addProdcutToCart(productID_txt.getText(), quantity_txt.getText());
		if(result < 0){
			JOptionPane.showMessageDialog(null, "Either item do not exist or stock insuficient");
		}
		else{
			update();
			totalPrice_txt.setText(result + "");
			productID_txt.setText("");
			quantity_txt.setText("");
		}
	}

	private void useVoucher(){
		int totalPrice = TransactionHandler.getInstance().applyVoucher(voucherID_txt.getText());
		totalPrice_txt.setText( totalPrice + "");
	}

	private void checkout(){
		if (!totalPrice_txt.getText().equals("")){
			TransactionHandler.getInstance().viewPaymentForm();
			update();
		}
		else{
			JOptionPane.showMessageDialog(null, "Cannot Checkout Empty Cart");
		}
	}

	private void update(){
		Vector<String> header = new Vector<>();
		header.add("Product ID");
		header.add("Product Name");
		header.add("Price");
		header.add("Quantity");
		DefaultTableModel dataModel = new DefaultTableModel(header, 0);

		//This Method is not present in the Class Diagram
		Vector<CartItem> list = CartHandler.getInstance().getAllItem();
		if(list != null){
			for(int i = 0, l = list.size(); i < l; i++){
				Vector<Object> row = new Vector<>();
				Product prod = list.get(i).getProduct();
				row.add(prod.getProductId());
				row.add(prod.getName());
				row.add(prod.getPrice());
				row.add(list.get(i).getQuantity());
				dataModel.addRow(row);
			}
		}
		tableCart.setModel(dataModel);
		// Update Price
		totalPrice_txt.setText( TransactionHandler.getInstance().getDiscountedPrice() + "");
	}
}
