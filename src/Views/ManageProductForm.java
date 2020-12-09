package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import AbstractClass.AbstractTableForm;
import Controllers.ProductHandler;
import Models.Product;

public class ManageProductForm extends AbstractTableForm implements ActionListener{

	private String selectedProductID = "";
	private String selectedProductName = "";
	private String selectedProductDescription = "";
	private String selectedProductStock = "";
	private String selectedProductPrice = "";
	

	JPanel panelTable;
	JButton btnInsert, btnUpdate, btnDelete;
	
	public ManageProductForm() {
		super();
	}

	/**Setting Action performed */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnInsert){
			insert();
		}
		else if(e.getSource() == btnUpdate){
			update();
		}
		else if(e.getSource() == btnDelete){
			delete();
		}
	}

	@Override
	protected void loadDataFromDataBase() {
		Vector<String> header = new Vector<String>();
		header.add("Product ID");
		header.add("Product Name");
		header.add("Description");
		header.add("Price");
		header.add("Stock");

		//Get All Data From Database
		Vector<Product> list = ProductHandler.getInstance().getAllProduct();
		
		//Table
		DefaultTableModel dataModel = new DefaultTableModel(header, 0);

		// Add Row
		for(int i = 0, l = list.size(); i < l; i++){
			System.out.println(list.get(i).getProductId());
			Vector<Object> row = new Vector<Object>();
			row.add(list.get(i).getProductId());
			row.add(list.get(i).getName());
			row.add(list.get(i).getDescription());
			row.add(list.get(i).getPrice());
			row.add(list.get(i).getStock());
			dataModel.addRow(row);
		}

		super.table.setModel(dataModel);
	}

	@Override
	protected void configure_panelButtonBot() {
		btnInsert = new JButton("Insert");
		panelButtonBot.add(btnInsert);
		
		btnUpdate = new JButton("Update");
		panelButtonBot.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		panelButtonBot.add(btnDelete);
	}
	
	@Override
	protected void addListener() {
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);

		super.table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = table.getSelectedRow();
				
				selectedProductID = table.getValueAt(row, 0).toString();
				selectedProductName = table.getValueAt(row, 1).toString();
				selectedProductDescription = table.getValueAt(row, 2).toString();
				selectedProductStock = table.getValueAt(row, 3).toString();
				selectedProductPrice = table.getValueAt(row, 4).toString();

			}
		});
	}
	
	@Override
	protected String setTableTitle() {
		return "Manage Product";
	}


	/**Call Insert From Fields */
	public void insert(){
		// Setting Fields
		JTextField productName_pop = new JTextField(selectedProductName);
        JTextArea productDescription_pop = new JTextArea(selectedProductDescription);
        productDescription_pop.setRows(2);
        JTextField productPrice_pop =  new JTextField(selectedProductPrice);
        JTextField productStock_pop =  new JTextField(selectedProductStock);
		
		JComponent[] inputs = new JComponent[]{
			new JLabel("Product Name:"),
				productName_pop,
                new JLabel("Description:"),
                productDescription_pop,
                new JLabel("Price:"),
                productPrice_pop,
                new JLabel("Stock:"),
                productStock_pop
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Insert New Product", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			// Access to Database
			if(
				ProductHandler.getInstance().addProduct(productName_pop.getText(), productDescription_pop.getText(), productPrice_pop.getText(), productStock_pop.getText()) == null
			){
				JOptionPane.showMessageDialog(null,"Fail to add new Product. Please check the data.");
			}
		}else{
			System.out.println("Insert Canceled");
		}
		 loadDataFromDataBase();
	}

	public void update(){
		if(selectedProductID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select A Product first");
			return;
		}
		// Setting Fields
		int id_pop = Integer.parseInt(selectedProductID);
		// JTextField productID_pop =  new JTextField(selectedProductID.getText());
		JTextField productName_pop = new JTextField(selectedProductName);
        JTextArea productDescription_pop = new JTextArea(selectedProductDescription);
        productDescription_pop.setRows(2);
        JTextField productPrice_pop =  new JTextField(selectedProductPrice);
		
		JComponent[] inputs = new JComponent[]{
				new JLabel("Product ID:"),
				new JLabel(selectedProductID),
				// productID_pop,
				new JLabel("Product Name:"),
                productName_pop,
                new JLabel("Description:"),
                productDescription_pop,
                new JLabel("Price:"),
                productPrice_pop
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Update Existing Product", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			System.out.println("OK SELECTED");
			// Access to Database
			if(
				ProductHandler.getInstance().updateProduct(selectedProductID, productName_pop.getText(), productDescription_pop.getText(), productPrice_pop.getText()) == null
			){
				JOptionPane.showMessageDialog(null,"Fail to Update Product. Please check the data.");
			}
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}

	public void delete(){
		// Setting Fields
		if(selectedProductID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select any product first");
			return;
		}

		JComponent[] inputs = new JComponent[]{
				new JLabel("Are you sure want to delete?"),
				new JLabel("Product ID:"),
				new JLabel("      " +selectedProductID),
				// productID_pop,
				new JLabel("Product Name:"),
				new JLabel("      " +selectedProductName),
				new JLabel("Description:"),
				new JLabel("      " +selectedProductDescription),
				new JLabel("Price:"),
				new JLabel("      " +selectedProductPrice)
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Delete Existing Product", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			System.out.println("OK SELECTED");

			// Access to Database
			if(
				!ProductHandler.getInstance().deleteProduct(selectedProductID)
			){
				JOptionPane.showMessageDialog(null,"Fail to delete Product. Please check the data.");
			}
		}else{
			System.out.println("Insert Canceled");
		}

		// ProductHandler.getInstance().deleteProduct(id);
		loadDataFromDataBase();
	}

}
