import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

//	This is the main class for the Sales Order/invoicing process
public class Invoice {
	private String invoiceNumber;
	private String invoiceDate;
	private String invoiceCustomerName;
	private String invoiceCustomerAddress;
	private String invoiceCustomerCountry;
	private String invoiceCustomerContactNumber;
	private static int numberOfInvoices;
//	Set up the object arrays for invoice lines and fees
	private static final int maxNumberOfInvoiceLines = 6;
	private int numberOfInvoiceLines;
	private InvoiceLine[] invoiceline = 
			new InvoiceLine[maxNumberOfInvoiceLines];
	private static final int maxNumberOfInvoiceFeeLines = 2;
	private int numberOfInvoiceFeeLines;
	private InvoiceFeeLine[] invoicefeeline = 
			new InvoiceFeeLine[maxNumberOfInvoiceFeeLines];

//	Constructor for Invoice class. All ready for future enhancement for
//	multiple Invoices and storing Invoices in the database. Initially,
//	there is specific requirement for a single Invoice at any one time.
//	
	Invoice(String newInvoiceNumber) {
//		Eventually, this will become the code for multiple Invoices!
		clearInvoice(newInvoiceNumber);
	}

//	These methods are the basic getter and setter for the class
//	
	public int getNumberOfInvoices() {
		return this.numberOfInvoices;
	}
	
	private int putNumberOfInvoices(int newNumber) {
		this.numberOfInvoices = newNumber; 
		return this.numberOfInvoices;
	}
	
	public int getNumberOfInvoiceLines() {
		return this.numberOfInvoiceLines;
	}
	
	private int putNumberOfInvoiceLines(int newNumber) {
		this.numberOfInvoiceLines = newNumber;
		return this.numberOfInvoiceLines;
	}
	
	public int getNumberOfInvoiceFeeLines() {
		return this.numberOfInvoiceFeeLines;
	}
	
	private int putNumberOfInvoiceFeeLines(int newNumber) {
		this.numberOfInvoiceFeeLines = newNumber;
		return this.numberOfInvoiceFeeLines;
	}
	
	public String getCustomerInformation() {
		// return invoice header information
				return 	this.invoiceNumber + ",\n" +
						this.invoiceCustomerName + ",\n" +
						this.invoiceCustomerAddress + ",\n" +
						this.invoiceCustomerCountry + ",\n" +
						this.invoiceCustomerContactNumber + ",\n" +
						this.invoiceDate;
		}
			
//	Take the single Invoice and clear all the information from it
//	including all the Invoice lines and fees
//	
	public void clearInvoice(String newInvoiceNumber) {
//		Routine to clear this Invoice for reuse.
		this.invoiceNumber = newInvoiceNumber;
		this.invoiceCustomerName = "";
		this.invoiceCustomerAddress = "";
		this.invoiceCustomerCountry = "";
		this.invoiceDate = "";
		for (int i = 0; i < getNumberOfInvoiceLines(); i++)
			this.invoiceline[i] = null;
		putNumberOfInvoiceLines(0);
		for (int i = 0; i < getNumberOfInvoiceFeeLines(); i++)
			this.invoicefeeline[i] = null;
		putNumberOfInvoiceFeeLines(0);
//		 Change this when requirements for multiple Invoices happen 
		putNumberOfInvoices(1);
	}

//	Populate the Invoice with customer information created in the Sales
//	class
//	
	public String setCustomerInformation( 	String invoiceNumber,
											String customerName,
											String customerAddress,
											String customerCountry,
											String customerContact) {
//		Get invoice header information from the user
		this.invoiceNumber = invoiceNumber;
		this.invoiceCustomerName = customerName;
		this.invoiceCustomerAddress = customerAddress;
		this.invoiceCustomerCountry = customerCountry;
		this.invoiceCustomerContactNumber = customerContact;
		this.invoiceDate = getCurrentDate();
//		System.out.println(invoiceNumber);
//		System.out.println(invoiceCustomerName);
//		System.out.println(invoiceCustomerAddress);
//		System.out.println(invoiceDate);
		return invoiceNumber;
	}
	
//	Create an Invoice product line with the product information selected 
//	by the user in the Sales class
//	
	public void addInvoiceLine(String invoiceNumber, int invoiceLineNumber,
			String productID, String productDescription, int productQuantity,
			double productTotalPrice) {
		System.out.println(" " + getNumberOfInvoiceLines() + ", " );
		if (invoiceLineNumber >= maxNumberOfInvoiceLines) {
			System.out.println("You have maximum lines on Invoice, "
					+ "Can't add more!");
		} else {
			invoiceline[getNumberOfInvoiceLines()] = new 
					InvoiceLine(invoiceNumber,
				invoiceLineNumber, productID, productDescription,  
				productQuantity,
				productTotalPrice);
			putNumberOfInvoiceLines(getNumberOfInvoiceLines() + 1);
		}
	}
	
//	Create an Invoice fee line with the fee information selected 
//	by the user in the Sales class
//	
	public void addInvoiceFeeLine(String invoiceNumber, int feeLineNumber,
			String feeID, String feeDescription, double feePrice) {
		if (getNumberOfInvoiceFeeLines() < this.maxNumberOfInvoiceFeeLines) {
			invoicefeeline[getNumberOfInvoiceFeeLines()] = 
					new InvoiceFeeLine(invoiceNumber,
					feeLineNumber, feeID, feeDescription,  feePrice);
				putNumberOfInvoiceFeeLines(getNumberOfInvoiceFeeLines() + 1);
		} else {
			System.out.println("You have maximum fees on Invoice, "
					+ "Can't add more!");
		}

	}
	
//	Link between Sales class and InvoiceFeeLine class to return the fee
//	description to the user
//	
	public String getInvoiceFeeLineDescription(int record) {
		return invoicefeeline[record].getInvoiceFeeLineDescription();
	}
	
//	Routine to return the current system date in the format requested
//	Technique copied from
//	Guru99.com. (2019). [online] Available at: 
//	https://www.guru99.com/java-date.html [Accessed 21 Jun. 2019].
//	
	private String getCurrentDate() {
		Date objDate = new Date();
//		Current System Date and time is assigned to objDate
//		Date format is Specified
		SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy hh:mm");
// 		Date format string is passed as an argument to the Date 
//		format object
		return objSDF.format(objDate);
//		And send it back
	}
	
//	Method to return a string containing products already selected by user
//	
	public String toInvoiceLinesString() {
		String response = "\nCurrent products on Invoice are: ";
		if (getNumberOfInvoiceLines() > 0) 
			for (int x = 0; x < getNumberOfInvoiceLines(); x++) {
				response += "\n" + invoiceline[x].getInvoiceLine();
		}
		response += "\nThere are " + getNumberOfInvoiceLines() + 
				" products on the Invoice.\n";
		return response;

	}
	
//	Method to return a string containing fees already selected by user
//	
	public String toInvoiceFeeString() {
		String response = "\nCurrent fees on Invoice are: ";
		if (getNumberOfInvoiceFeeLines() > 0) 
			for (int x = 0; x < getNumberOfInvoiceFeeLines(); x++) {
				response += "\n" + 
						invoicefeeline[x].getInvoiceFeeLineString();
		}
		response += "\nThere are " + getNumberOfInvoiceFeeLines() + 
				" fees on the Invoice.\n";
		return response;
	}

//	This is the main method for returnin the whole Invoice, including    
//	selected products and fees in a nice little formatted text bundle to
//	display to the user, or print, or create a pdf. Whatever will be
//	eventually required
//	
	public String toPrintString(String Company) {
		String blankLine = String.format("\n!%-78s!", " ".repeat(80));
		String ruleLine = String.format("\n+%-78s+", "=".repeat(80));
		String emptyLine = "\n";
		String returnLine =	emptyLine +
				ruleLine +
				blankLine +
				String.format("\n!%30s%-50s!", " ", Company) +
				blankLine +
				String.format("\n!%10s%-20s\t%-49s!",
						" ", "Invoice Number:", invoiceNumber) +
				String.format("\n!%10s%-20s\t%-49s!", 
						" ", "Invoice Date:", invoiceDate) +
				blankLine +
				ruleLine +
				blankLine +
				String.format("\n!%-80s!", "Customer Information") +
				blankLine +
				String.format("\n!%10s%-15s%-55s!", 
						" ", "Name: ", this.invoiceCustomerName);
		String[] splitAddress =  this.invoiceCustomerAddress.split(", ");
		String address = "Address: ";
		for (int x = 0; x < splitAddress.length; x++) {
			returnLine += 				String.format("\n!%10s%-15s%-55s!", 
						" ", address, splitAddress[x]);
			address = "         ";
		}
		returnLine +=
				String.format("\n!%10s%-15s%-55s!", 
						" ", "Country: ", this.invoiceCustomerCountry) +
				String.format("\n!%10s%-15s%-55s!", 
						" ", "Contact No.: ", 
						this.invoiceCustomerContactNumber) +
				blankLine +
				ruleLine + 
				blankLine +
				String.format("\n!%-80s!", "Product Order Information") +
				blankLine +
				invoiceline[0].toInvoiceLineHeaderString();
		for (int i = 0; i < getNumberOfInvoiceLines(); i++) {
					returnLine += " " + invoiceline[i].toInvoiceLineString();
				}
		returnLine +=		
				ruleLine + 
				blankLine +
				String.format("\n!%-80s!", "Fees Order Information") +
				blankLine +
				invoicefeeline[0].toInvoiceFeeLineHeaderString();
		for (int i = 0; i < getNumberOfInvoiceFeeLines(); i++) {
			returnLine += " " + invoicefeeline[i].toInvoiceFeeLineString();
		}
		returnLine +=		
				ruleLine +
				emptyLine;
		return returnLine;
	}
}