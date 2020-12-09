package Views;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controllers.TransactionHandler;

public class AddToCartForm {

	public AddToCartForm() {
		JTextField productID_txt = new JTextField();
		JTextField quantity_txt = new JTextField();
		while(true){
			JComponent[] inputs = new JComponent[]{
				new JLabel("Product ID:"),
				productID_txt,
				new JLabel("Quantity:"),
				quantity_txt
			};
			int result = JOptionPane.showConfirmDialog(null, inputs, "Insert New Voucher", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION){
				// Check and Insert Data
				int productID, quantity;
				productID = Integer.parseInt(productID_txt.getText());
				quantity = Integer.parseInt(quantity_txt.getText());
				result = TransactionHandler.getInstance().addProdcutToCart(productID_txt.getText(), quantity_txt.getText());
	
				if(result == -1){
					JOptionPane.showMessageDialog(null,"Product not exist or stock is unsufficient");
					productID_txt.setText(productID +"");
					quantity_txt.setText(quantity +"");
				}
				else{
					return;
				}
			}else{
				return;
			}
		}
	}

}
