
//	Class to contain the data for each invoice product line, set at a
//	maximum of six for testing purposes
//
public class InvoiceLine {
//	Class for lines on our Toys Universe invoice, for products
	private String invoiceNumber;
	private int invoiceLineNumber;
	private String productID;
	private String productDescription;
	private int productQuantity;
	private double productTotalPrice;
	
//	Constructor to create an Invoice Line from supplied data
//	
	public InvoiceLine(String invoiceNumber, int invoiceLineNumber,
			String productID, String productDescription, 
			int productQuantity, double productTotalPrice) {
//		Create the class information from the parameters
		this.invoiceNumber = invoiceNumber;
		this.invoiceLineNumber = invoiceLineNumber;
		this.productID = productID;
		this.productDescription = productDescription;
		this.productQuantity = productQuantity;
		this.productTotalPrice = productTotalPrice;
	}
	
//	unformatted output of lines for general display to user
//	
	public String getInvoiceLine() {
		return 	this.invoiceNumber + ", " +
		this.invoiceLineNumber + ", " +
		this.productID + ", " +
		this.productQuantity +  ", " +
		this.productTotalPrice;
	}
	
//	Header of formal Invoice product line 
//	
	public static String toInvoiceLineHeaderString() {
		return "\n! Line   ! ID ! Description            " +
		"                  ! Qty ! Price           !" +
		"\n+--------+----+------------------------" +
		"------------------+-----+-----------------+";
	}
	
//	Final formatted detail of products to output to Invoice 
//	
	public String toInvoiceLineString() {
		return 
			String.format("\n!%-4s %-2d ! %2s ! %-40s ! %3d ! "
					+ "$%,8.2f ! %-3s !",
					" ",
					this.invoiceLineNumber + 1,
					this.productID,
					this.productDescription,
					this.productQuantity,
					this.productTotalPrice,
					" ");
		
	}

}
