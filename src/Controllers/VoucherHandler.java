package Controllers;

import java.sql.Date;
import java.util.Vector;

import Models.Voucher;
import Views.ManageVoucherForm;

public class VoucherHandler {

	private static VoucherHandler controller;
	private Voucher voucher;
	

	private VoucherHandler() {
		voucher = new Voucher();
	}

	public static VoucherHandler getInstance(){
		if(controller == null){
			controller = new VoucherHandler();
		}
		return controller;
	}


	/*--- DEFINE Method in Class Diagram ---*/
	public void viewManageVoucherForm(){
		new ManageVoucherForm();
	}

	public Vector<Voucher> getAllVoucher(){
		return voucher.getAllVoucher();
	}

	public Voucher getVoucher(int voucherID){
		return voucher.getVoucher(voucherID);
	}

	/** discount in String in 1 - 100 size */
	public Voucher addVoucher(String discount, String validDate){
		//Validation
		System.out.println("addVoucher Controller Called");
		if(validateDiscount(discount)) return null;
		if(validateValidDate(validDate)) return null;

		float disc = Float.parseFloat(discount) / 100;
		//Set Value
		Voucher voucher = new Voucher();
		voucher.setDiscount(disc);
		voucher.setValidDate(validDate);
		voucher.setStatus("not used");

		// Access DB
		return voucher.insertVoucher();
	}

	public Voucher updateVoucher(String voucherID, String discount, String validDate){
		// Validation
		if(validateVoucherID(voucherID)) return null;
		if(validateDiscount(discount)) return null;
		if(validateValidDate(validDate)) return null;

		int id = Integer.parseInt(voucherID);
		float disc = Float.parseFloat(discount) / 100;
		//Get Previouse Data
		Voucher voucher = this.voucher.getVoucher(id);
		//Update Value
		voucher.setDiscount(disc);
		voucher.setValidDate(validDate);
		//Update DB
		return voucher.updateVoucher();
	}

	public boolean deleteVoucher(int voucherID){
		Voucher voucher = this.voucher.getVoucher(voucherID);
		return voucher.deleteVoucher(); 
	}

	public void useVoucher(int voucherID){
		Voucher voucher = this.voucher.getVoucher(voucherID);
		voucher.setStatus("used");
		voucher.updateVoucher();
	}



	// Validating Function
	/** Check for
	 * <ul>
	 * 	<li> Cannot be Null</li>
	 * 	<li> Must Existl</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateVoucherID(String voucherID){
		// Cannot be empty
		if(voucherID.equals("")) return true;

		int id;
		try {
			id = Integer.parseInt(voucherID);
		} catch (Exception e) {
			return true;
		}

		// Must be exist
		if( voucher.getVoucher(id) == null) return true;
		System.out.println("Finish ID Validation");
		return false;
	}

	/** Check for
	 * <ul>
	 * 	<li> Cannot be Empty</li>
	 * 	<li> Must be Numeric</li>
	 * 	<li> Must be between 1-100 (inclusivee)</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateDiscount(String discount){
		// Cannot be empty
		if(discount.equals("")) return true;
		System.out.println("discount empty pass");

		// Must be numberic
		float disc;
		try {
			disc = Float.parseFloat(discount);
			System.out.println("discount Integer pass");
		} catch (Exception e) {
			return true;
		}
		// Must be between 1-100 (inclusive)
		if(disc < 1 || disc > 100) return true;
		System.out.println("Finish Discount Validation");
		return false;
	}

	/** Check for
	 * <ul>
	 * 	<li> Must be in the future</li>
	 * </ul>
	 * <br>
	 */
	private boolean validateValidDate(String validDate){
		// Cannot be empty
		if(validDate.equals("")) return true;

		//Must be future
		//	is	      validDate    before								Today ? ? ?
		if(Date.valueOf(validDate).before((new Date(new java.util.Date().getTime()))))return true;
		System.out.println("Finish Date Validation");
		return false;
	}
}
