package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Controllers.TransactionHandler;
import Models.Transaction;
import Models.TransactionItem;

public class TransactionReport implements ActionListener{

	JTable table;

	JButton compileReport;
	int month, year;
	
	JFrame frame;
	String[] months =  {"--Choose--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	JComboBox<String> selectMonth;
	String[] years =  {"--Choose--", "2020", "2019"};
	JComboBox<String> selectYear;
	JButton bntCompile;

	String selectedTransactionID;
	String selectedTransactionDatel;
	String selectedTransactionVoucherID;
	String selectedTransactionEmployeeID;
	String selectedTransactionPayment;

	public TransactionReport() {
		initialize();
		addListeners();
		configureTable();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("View Report");
		frame.setBounds(0, 0, 500, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMonth.setBounds(12, 69, 88, 14);
		frame.getContentPane().add(lblMonth);

		selectMonth = new JComboBox<>(months);
		selectMonth.setBounds(110, 67, 85, 22);
		frame.getContentPane().add(selectMonth);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYear.setBounds(223, 69, 69, 14);
		frame.getContentPane().add(lblYear);

		selectYear = new JComboBox<>(years);
		selectYear.setBounds(302, 67, 89, 22);
		frame.getContentPane().add(selectYear);
		
		bntCompile = new JButton("Compile Report");
		bntCompile.setBounds(10, 96, 464, 23);
		frame.getContentPane().add(bntCompile);
		
		JLabel lblTransactionReport = new JLabel("Transaction Report");
		lblTransactionReport.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTransactionReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransactionReport.setBounds(10, 11, 464, 45);
		frame.getContentPane().add(lblTransactionReport);
		
		//Table
		
		table = new JTable(){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 130, 464, 270);
		frame.getContentPane().add(scrollPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bntCompile){
			loadDataFromDataBase();
			System.out.println("Load");
		}
	}

	public void addListeners() {
		bntCompile.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row= table.getSelectedRow();
				int id = Integer.parseInt(table.getValueAt(row, 0).toString()) ;
				selectedTransactionID = table.getValueAt(row, 0).toString();
				selectedTransactionDatel = table.getValueAt(row, 1).toString();
				selectedTransactionVoucherID = table.getValueAt(row, 2).toString();
				selectedTransactionEmployeeID = table.getValueAt(row, 3).toString();
				selectedTransactionPayment = table.getValueAt(row, 4).toString();
				viewTransactionDetail(id);
			}
		});
	}

	void configureTable(){
		Vector<String> header = new Vector<>();
		header.add("Transactio ID");
		header.add("Purchase Date");
		header.add("Voucher ID");
		header.add("Emplyee ID");
		header.add("Payment Type");
	}

	private void loadDataFromDataBase(){
		System.out.println("Load Database");
		Vector<String> header = new Vector<>();
		header.add("Transactio ID");
		header.add("Purchase Date");
		header.add("Voucher ID");
		header.add("Emplyee ID");
		header.add("Payment Type");
		DefaultTableModel dataModel = new DefaultTableModel(header, 0);

		Vector<Transaction> list = TransactionHandler.getInstance().getTransactionReport(selectMonth.getSelectedItem().toString(), selectYear.getSelectedItem().toString());
		if(list == null){
			JOptionPane.showMessageDialog(null,"Please insert month and date first.");
			return;
		}
		for(int i = 0, l = list.size(); i < l; i++){
			Vector<Object> row = new Vector<>();
			row.add(list.get(i).getTransactionID());
			row.add(list.get(i).getpurchase_datetime());
			row.add(list.get(i).getVoucherID());
			row.add(list.get(i).getEmployeeID());
			row.add(list.get(i).getPaymentType());
			dataModel.addRow(row);
		}
		table.setModel(dataModel);
	}

	/**Search and display Details */
	public void viewTransactionDetail(int transactionID){
		Vector<TransactionItem> details = TransactionHandler.getInstance().getTransactionDetail(transactionID);
		
		//Set Table
		JTable detailTable = createTransactionDetailTable(details);

		createTransactionDetailWindow(transactionID, detailTable);
	}

	/**Create TransactionDetailWindow using WindowBuilder */
	public void createTransactionDetailWindow(int transactionID, JTable table){
		JFrame frameDetail;
		JTextField transID;
		JTextField transDate;
		JTextField voucID;
		JTextField empID;
		JTextField paymentType;

		frameDetail = new JFrame("Transaction Detail");
		frameDetail.setBounds(100, 100, 400, 400);
		frameDetail.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameDetail.getContentPane().setLayout(null);
		frameDetail.setLocationRelativeTo(null);
		
		JLabel lblTransactionDetail = new JLabel("Transaction Detail");
		lblTransactionDetail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTransactionDetail.setBounds(10, 11, 157, 20);
		frameDetail.getContentPane().add(lblTransactionDetail);
		
		JLabel lblTransactionId = new JLabel("Transaction ID :");
		lblTransactionId.setBounds(20, 42, 98, 14);
		frameDetail.getContentPane().add(lblTransactionId);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(20, 70, 98, 14);
		frameDetail.getContentPane().add(lblDate);
		
		JLabel lblPaymentType = new JLabel("Payment Type:");
		lblPaymentType.setBounds(20, 98, 98, 14);
		frameDetail.getContentPane().add(lblPaymentType);
		
		transID = new JTextField(selectedTransactionID);
		transID.setEditable(false);
		transID.setBounds(115, 39, 71, 20);
		frameDetail.getContentPane().add(transID);
		transID.setColumns(10);
		
		transDate = new JTextField(selectedTransactionDatel);
		transDate.setEditable(false);
		transDate.setBounds(115, 67, 71, 20);
		frameDetail.getContentPane().add(transDate);
		transDate.setColumns(10);
		
		paymentType = new JTextField(selectedTransactionPayment);
		paymentType.setEditable(false);
		paymentType.setBounds(115, 95, 71, 20);
		frameDetail.getContentPane().add(paymentType);
		paymentType.setColumns(10);
		
		JLabel lblEmployeeId = new JLabel("Employee ID:");
		lblEmployeeId.setBounds(205, 42, 98, 14);
		frameDetail.getContentPane().add(lblEmployeeId);
		
		JLabel lblVoucherId = new JLabel("Voucher ID :");
		lblVoucherId.setBounds(205, 70, 98, 14);
		frameDetail.getContentPane().add(lblVoucherId);
		
		empID = new JTextField(selectedTransactionEmployeeID);
		empID.setEditable(false);
		empID.setColumns(10);
		empID.setBounds(290, 39, 71, 20);
		frameDetail.getContentPane().add(empID);
		
		voucID = new JTextField(selectedTransactionVoucherID);
		voucID.setEditable(false);
		voucID.setColumns(10);
		voucID.setBounds(290, 67, 71, 20);
		frameDetail.getContentPane().add(voucID);
				
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 123, 364, 227);
		frameDetail.getContentPane().add(scrollPane);
		frameDetail.setVisible(true);


	}

	public JTable createTransactionDetailTable(Vector<TransactionItem> details){
		JTable detailTable = new JTable();
		Vector<String> header = new Vector<String>();
		header.add("Transaction ID");
		header.add("Product ID");
		header.add("Quantity");

		DefaultTableModel dataModel = new DefaultTableModel(header, 0);
		for(int i = 0, l = details.size(); i < l; i++){
			Vector<Object> row = new Vector<>();
			row.add(details.get(i).getTransactionID());
			row.add(details.get(i).getProductID());
			row.add(details.get(i).getQuantity());

			dataModel.addRow(row);
		}
		detailTable.setModel(dataModel);
		return detailTable;
	}
}
