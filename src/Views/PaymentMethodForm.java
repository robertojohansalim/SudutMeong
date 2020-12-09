package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controllers.TransactionHandler;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PaymentMethodForm implements ActionListener {

	JFrame frame;
	JButton btnProceed;
	JComboBox<String> cmbPaymentMethod;
	JTextField totalCharge_txt, money_txt;

	String[] comboBoxOptions = {"Cash", "Credit Card"};

	public PaymentMethodForm() {
		initialize();
		updateValue();
		frame.setVisible(true);
	}

	private void initialize(){
		frame = new JFrame();
		frame.setSize(300, 230);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTotalPrice = new JLabel("Payment");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTotalPrice.setBounds(10, 11, 264, 25);
		frame.getContentPane().add(lblTotalPrice);
		
		btnProceed = new JButton("Process Transaction");
		btnProceed.addActionListener(this);
		btnProceed.setBounds(10, 142, 264, 36);
		frame.getContentPane().add(btnProceed);
		
		cmbPaymentMethod = new JComboBox<>(comboBoxOptions);
		cmbPaymentMethod.setBounds(156, 84, 118, 22);
		frame.getContentPane().add(cmbPaymentMethod);
		
		JLabel lblPauymentMethod = new JLabel("Payment Method");
		lblPauymentMethod.setHorizontalAlignment(SwingConstants.CENTER);
		lblPauymentMethod.setBounds(10, 84, 136, 22);
		frame.getContentPane().add(lblPauymentMethod);
		
		JLabel lblMoney = new JLabel("Money");
		lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoney.setBounds(10, 117, 136, 14);
		frame.getContentPane().add(lblMoney);
		
		money_txt = new JTextField();
		money_txt.setBounds(156, 114, 118, 20);
		frame.getContentPane().add(money_txt);
		money_txt.setColumns(10);
		
		totalCharge_txt = new JTextField();
		totalCharge_txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		totalCharge_txt.setEditable(false);
		totalCharge_txt.setBounds(156, 47, 118, 26);
		frame.getContentPane().add(totalCharge_txt);
		totalCharge_txt.setColumns(10);
		
		JLabel lblTotalCharge = new JLabel("Total Charge:");
		lblTotalCharge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalCharge.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalCharge.setBounds(20, 47, 126, 23);
		frame.getContentPane().add(lblTotalCharge);
	}

	private void updateValue(){
		totalCharge_txt.setText(TransactionHandler.getInstance().getDiscountedPrice() + "");;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnProceed){
			String paymentType = (String) cmbPaymentMethod.getSelectedItem();
			String money = money_txt.getText();
			// Calculation of Changes is recomended to be process in the View (As the asisten said via line chat)
			int price = TransactionHandler.getInstance().getDiscountedPrice();
			int changes = Integer.parseInt(money) - price;

			;
			if(TransactionHandler.getInstance().processTransaction(paymentType, money) == null){
				JOptionPane.showMessageDialog(null,"Transaction Fail: not enough money");
			}
			else{
				JOptionPane.showMessageDialog(null,"Transaction Success Change : " + changes);
				frame.setVisible(false);
			}
		}
	}
}