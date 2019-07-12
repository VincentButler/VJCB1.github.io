
//	Class to contain the Fees for Invoice, set at max 2 currently
//
public class InvoiceFeeLine {

//	Class for lines on our Toys Universe invoice, for products
	private String invoiceNumber;
	private int invoiceFeeLineNumber;
	private String feeID;
	private String feeDescription;
	private double feePrice;
	
//	Constructor to create an Invoice Line from supplied data
	public InvoiceFeeLine(String invoiceNumber, int invoiceLineNumber,
			String feeID, String feeDescription, double feePrice) {
//		Create the class information from the parameters
		this.invoiceNumber = invoiceNumber;
		this.invoiceFeeLineNumber = invoiceLineNumber;
		this.feeID = feeID;
		this.feeDescription = feeDescription;
		this.feePrice = feePrice;
	}
	
	public String getInvoiceFeeLineDescription() {
		return this.feeDescription;
	}
	
	public double getInvoiceFeeLineTotalPrice() {
		return this.feePrice;
	}
	
//	unformatted output of lines for user output
	public String getInvoiceFeeLineString() {
		return 		
		this.invoiceFeeLineNumber + ", " +
		this.feeDescription + ", " +
		this.feePrice;
	}
	
//	Header of formal Invoice fees line 
//	
	public static String toInvoiceFeeLineHeaderString() {
		return "\n! Line !   ID ! Description            " +
		"                        ! Price           !" +
		"\n+------+------+------------------------" +
		"------------------------+-----------------+";
	}
	
//	Final formatted output to Invoice 
//	
	public String toInvoiceFeeLineString() {
		return 
			String.format("\n! %4d ! %4s ! %-46s ! $%,8.2f%-6s !",
					this.invoiceFeeLineNumber + 1,
					this.feeID,
					this.feeDescription,
					this.feePrice,
					" ");
		
	}

}
