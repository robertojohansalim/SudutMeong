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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import AbstractClass.AbstractTableForm;
import Controllers.VoucherHandler;
import Models.Voucher;

public class ManageVoucherForm extends AbstractTableForm implements ActionListener{

	JButton btnInsert, btnUpdate, btnDelete;

	String selectedVoucherID = "";
	String selectedVoucherDiscount = "";
	String selectedVoucherValidDate = "";
	String selectedVoucherStatus = "";
	
	public ManageVoucherForm() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnInsert ){
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
		// Connect Btn with actionlistnener
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);

		// Click and select Feature
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = table.getSelectedRow();

				selectedVoucherID = table.getValueAt(row, 0).toString();
				selectedVoucherDiscount = table.getValueAt(row, 1).toString();
				selectedVoucherValidDate = table.getValueAt(row, 2).toString();
				selectedVoucherStatus = table.getValueAt(row, 3).toString();
			}
		});
	}

	@Override
	protected void loadDataFromDataBase() {
		Vector<String> header = new Vector<String>();
		header.add("Voucher ID");
		header.add("Discount");
		header.add("Valid Date");
		header.add("Status");

		//Get All Data From Database
		Vector<Voucher> list = VoucherHandler.getInstance().getAllVoucher();
		
		//Table
		DefaultTableModel dataModel = new DefaultTableModel(header, 0);

		// Add Row
		for(int i = 0, l = list.size(); i < l; i++){
			// System.out.println(list.get(i).getVoucherID());
			Vector<Object> row = new Vector<Object>();
			row.add(list.get(i).getVoucherID());
			// These Require more in update (actionListnener)...
			// row.add((int) (list.get(i).getDiscount() * 100) + "%");
			row.add(list.get(i).getDiscount() * 100);
			row.add(list.get(i).getValidDate());
			row.add(list.get(i).getStatus());
			dataModel.addRow(row);
		}

		table.setModel(dataModel);
	}

	@Override
	protected String setTableTitle() {
		return "Manage Voucher";
	}

	private void insert(){
		// Setting Fields
		JTextField voucherDiscount_pop = new JTextField(selectedVoucherDiscount);
        JTextField voucherValidDate_pop = new JTextField(selectedVoucherValidDate);
		
		JComponent[] inputs = new JComponent[]{
				new JLabel("Voucher Discount: (Ex: 50 for 50%) "),
				voucherDiscount_pop,
                new JLabel("Valid Date (YYYY-MM-DD):"),
                voucherValidDate_pop,
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Insert New Voucher", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			// Access to Database
			if(
				VoucherHandler.getInstance().addVoucher(voucherDiscount_pop.getText(), voucherValidDate_pop.getText()) == null
			){
				System.out.println("Insert Fail");
				JOptionPane.showMessageDialog(null,"Fail to Insert. Please check the data.");
				return;
			}
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}
	
	private void update(){
		if(selectedVoucherID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select A Product first");
			return;
		}
		// Setting Fields
		// JTextField voucherID_pop = new JTextField(id_pop);
		JTextField voucherDiscount_pop = new JTextField(selectedVoucherDiscount);
		JTextField voucherValidDate_pop = new JTextField(selectedVoucherValidDate);

		JComponent[] inputs = new JComponent[]{
				new JLabel("Voucher ID:"),
				new JLabel(selectedVoucherID),
				new JLabel("Voucher Discount: (Ex: 50 for 50%) "),
				voucherDiscount_pop,
				new JLabel("Valid Date (YYYY-MM-DD):"),
				voucherValidDate_pop,
		};

		int result = JOptionPane.showConfirmDialog(null, inputs, "Update Existing Voucher", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			// Access to Database
			if(
				VoucherHandler.getInstance().updateVoucher(selectedVoucherID, voucherDiscount_pop.getText(), voucherValidDate_pop.getText()) == null
			){
				JOptionPane.showMessageDialog(null,"Fail to Update. Please check the data.");
				return;
			}


			//Clear Selected
			clearselected();
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}

	private void delete(){
		if(selectedVoucherID.equals("")){
			JOptionPane.showMessageDialog(null,"Please Select any voucher first");
			return;
		}
		int id_pop = Integer.parseInt(selectedVoucherID);

		JComponent[] inputs = new JComponent[]{
			new JLabel("Are you sure want to delete?"),
			new JLabel("Voucher ID:"),
			new JLabel("      " +selectedVoucherID),
			// productID_pop,
			new JLabel("Discount:"),
			new JLabel("      " +selectedVoucherDiscount),
			new JLabel("Valid Date:"),
			new JLabel("      " +selectedVoucherValidDate),
			new JLabel("Status:"),
			new JLabel("      " +selectedVoucherStatus)
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Delete Existing Product", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			System.out.println("OK SELECTED");

			int id = id_pop;

			// Access to Database
			VoucherHandler.getInstance().deleteVoucher(id);

			// Clear Selected
			clearselected();
		}else{
			System.out.println("Insert Canceled");
		}
		loadDataFromDataBase();
	}

	private void clearselected(){
		selectedVoucherID = "";
		selectedVoucherDiscount = "";
		selectedVoucherValidDate = "";
		selectedVoucherStatus = "";
	}


	
}
