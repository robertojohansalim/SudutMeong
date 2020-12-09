package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

import Controllers.TransactionHandler;

import java.awt.Font;
import javax.swing.JButton;

public class PaymentForm implements ActionListener {

	private JFrame frame;
	private JTextField voucherID_txt, totalPrice_txt;
	private JButton btnCheckout, btnAddVoucher;

	public PaymentForm() {
		initialize();
		updatePrice();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPaymentForm = new JLabel("Payment Form");
		lblPaymentForm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPaymentForm.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaymentForm.setBounds(10, 11, 364, 29);
		frame.getContentPane().add(lblPaymentForm);
		
		JLabel lblTotalPrice = new JLabel("Total Price");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setBounds(92, 51, 77, 17);
		frame.getContentPane().add(lblTotalPrice);
		
		totalPrice_txt = new JTextField();
		totalPrice_txt.setEditable(false);
		totalPrice_txt.setBounds(195, 48, 96, 20);
		frame.getContentPane().add(totalPrice_txt);
		totalPrice_txt.setColumns(10);
		
		JLabel lblVoucherId = new JLabel("Voucher ID");
		lblVoucherId.setHorizontalAlignment(SwingConstants.CENTER);
		lblVoucherId.setBounds(92, 87, 77, 20);
		frame.getContentPane().add(lblVoucherId);
		
		voucherID_txt = new JTextField();
		voucherID_txt.setBounds(195, 87, 96, 20);
		frame.getContentPane().add(voucherID_txt);
		voucherID_txt.setColumns(10);
		
		btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(this);
		btnCheckout.setBounds(92, 159, 199, 23);
		frame.getContentPane().add(btnCheckout);

		btnAddVoucher = new JButton("Apply Voucher");
		btnAddVoucher.addActionListener(this);
		btnAddVoucher.setBounds(116, 118, 152, 23);
		frame.getContentPane().add(btnAddVoucher);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnCheckout){
			checkout();
		}
		else if(e.getSource() == btnAddVoucher){
			applyVoucher();
		}
	}

	private void checkout(){
		if(JOptionPane.showConfirmDialog(null,"Are You Sure?", "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION){
			TransactionHandler.getInstance().viewPaymentMethodForm();
			frame.setVisible(false);
		}
	}

	private void applyVoucher(){
		String voucherID = voucherID_txt.getText();
		int totalprice = TransactionHandler.getInstance().applyVoucher(voucherID);
		totalPrice_txt.setText(totalprice + "");
	}

	private void updatePrice(){
		int price = TransactionHandler.getInstance().getDiscountedPrice();
		totalPrice_txt.setText(price + "");
	}
}
