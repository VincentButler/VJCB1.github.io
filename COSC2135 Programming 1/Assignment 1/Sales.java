import java.util.Scanner;
import java.io.IOException;
//import jline.console.ConsoleReader;
/******************************************************************************
 ******************************************************************************
 * 
 *	@author Vince Butler
 *	@version 001
 *	Original program June 2019
 *	Last Modified 22 June 2019
 ****************************************************************************** 
 *	Modification Log:
 *	22/6/2019: Original Program
 * 
 ******************************************************************************
 *	Abstract:
 *	 This program is meant to represent the basics of a system that supports
 *	the ability to output an Invoice when a customer requests items from
 *	the website, or maybe, even in person or on the phone.
 *	It contains the kernel of a full supply chain system.
 *
 *	The following areas are covered, if briefly and simply:-
 *		1/	Customer Information
 *		2/	Invoice building (Header, lines, and fees)
 *		3/	Stock allocation not including backorders, etc
 *		4/	Add Inventory items through ordering
 *
 *	The following areas are not covered:-
 *		There is no storage of information between sessions
 *		Customer management
 *		Invoice Tracking and management
 *		Inventory Management
 *		Purchase Order Management and replenishment system
 *
 * Notes on coding and assumptions made.
 * 	I have assumed that this may be the basis of a larger application in
 * 	future assignments, so I have overwritten much of the code to allow
 * 	for the basis of future enhancements in areas that are weak in the
 *  specs. I have deliberately allowed for a potential of full encapsulation
 *  of the data, even in the areas that we have been directed to use
 *  techniques that are less than fully oo.
 *  
 ******************************************************************************
 *****************************************************************************/
//
//	This is the main class for the Toys universe application as per the specs.
//	In the inevitable need for more systems this can have the main peeled off
//	and changed to a constructor and a new class created which will call this
//	for the sales system.
//
public class Sales {

	private static String invoiceNumber;
	private static int numberOfInvoices;
	private static String companyName = "ToyUniverse.com.au";
	private Invoice invoice;
	private static Inventory inventory;
	private static Scanner input;

	public static void main(String[] args) {
//		
//		This is the menu system for Toys universe applications
//		While it's a simple application, the logic will stay here, but should
//		be moved to a class of its own if it becomes more sophisticated
//		
		input = new Scanner(System.in);
		Sales sales = new Sales();
		inventory = new Inventory(companyName);
		boolean endMenu = false;
		String validOptions = "AaBbCcXx";
		String option;
		invoiceNumber = "ABC120";
		do {
			option = sales.getMenuOption(validOptions);
			endMenu = sales.processMenuOption(option);
		} while (endMenu == false);
	}
//
//	Get valid input from the user and keep prompting until they enter a valid 
//	one
//	
	private String getMenuOption(String validOptions) {
		String option;
		do {
//			Scanner input = new Scanner(System.in);
			showMenu();
			option = getInfo(showMenu());
			if (!option.matches("[" + validOptions + "]")) {
//				showMenu();
				System.out.println("Error, You have selected an incorrect " + 
						"option: "
								+ option);
				System.out.println("Please try again from the menu list.");
				System.out.println("The valid options are " + validOptions);
			}
		} while (!option.matches("[" + validOptions + "]"));
		return option;
	}
//
//	Clearing the console discovered on StackOverflow.
//	stackoverflow.com. (2019). [online] Available at: 
//		https://stackoverflow.com/questions/2979383/java-clear-the-console 
//	[Accessed 21 Jun. 2019].
//		https://stackoverflow.com/questions/2979383/java-clear-the-console
//	
	public void clearScreen() {
//		
//		Future emhancement for the ability to clear the screen. Nothing I
//		tried seemed to work in the Eclipse console, except lots of 
//		line feeds, which looks ugly and leaves the information at the
//		bottom of the screen.
//		
//		for(int clear = 0; clear < 1000; clear++)  {
//		     System.out.println("\b") ;
//		  }
//		System.out.println(new String(new char[50]).replace("\0", "\b"));
//	    try
//	    {
//	        final String os = System.getProperty("os.name");
//	        System.out.print("os.name = " + os);
//	        if (os.contains("Windows"))  {
//	            Runtime.getRuntime().exec("cls");
//	        }  else  {
//	            Runtime.getRuntime().exec("clear");
//	        }
//	    }
//	    catch (final Exception e)
//	    {
//	        //  Handle any exceptions.
//	    }
//			throws IOException, InterruptedException {  
//		try {
//			new ProcessBuilder("cmd", "/c", 
//				"cls").inheritIO().start().waitFor();
//		} catch (Exception e) {
//		}
//		;
//////	    System.out.print("\033[H\033[2J");  
////		System.out.print('\u239A');
//	    System.out.flush();  
	}
//
//	Show the main menu	
//	
	private String showMenu() {
		return "\n\n" + companyName + " Menu System\n" +
		"\n\tA - Start new Invoice" +
		"\n\tB - View Purchase Information" +
		"\n\tC - Add Purchase" +
//		Added functionality to menu to display Stock on Hand for testing
//		"\n\tD - Display Stock on Hand for all products" +
		"\n\n\tX - Exit\n" +
		"\nPlease enter an option\n";
	}

//	After the user selects a valid option, this is where we direct the 
//	program to the appropriate method to perform the request
//	
	private boolean processMenuOption(String option) {
		boolean returnCode = false;
		switch (option) {
		case "A":
		case "a":
			invoiceNumber = getNextInvoiceNumber(invoiceNumber);
			invoice.clearInvoice(invoiceNumber);
			setCustomerInformation();
			break;
		case "B":
		case "b":
			System.out.print(invoice.toPrintString(companyName));
			break;
		case "C":
		case "c":
			addInvoiceLine(invoiceNumber);
			addInvoiceFeeLine(invoiceNumber);
			break;
//		case "D": // Stock on Hand
//		case "d":
//			System.out.println(" " + inventory.showInventory());
//			break;
		case "X": // Exit the program
		case "x":
			System.out.println("\nExit processing\n");
			returnCode = true;
			break;
		default:
			showMenu();
			break;
		}
		return returnCode;
	}

//	 Method to see if the (for testing purposes) single Invoice has been 
//	created. I have structured this code to enhance later with code
//	for Multiple Invoices if and when required. Shoual also be easy to
//	add database functions later as well
//	
	private String getNextInvoiceNumber(String invoiceNumber) {
		String returnString;
		if (numberOfInvoices > 0) {
//			if it has incrememnt the Invoice number and clear it 
//			invoiceNumber = sales.getNextInvoiceNumber(invoiceNumber);
//			Testing purposes to get a new Invoice number for us to use
			String prefix = invoiceNumber.substring(0, 5);
			int number = Integer.valueOf(invoiceNumber.substring(5, 6));
			number += 1;
			returnString = prefix + number;
			invoiceNumber = returnString;
			invoice.clearInvoice(returnString);
			numberOfInvoices += 1;
		} else {
			returnString = this.invoiceNumber;
			invoice = new Invoice(returnString);
			numberOfInvoices += 1;
			
		}
		System.out.println("Number of Invoices in Sales is " + 
				numberOfInvoices);
		return returnString;
	}
	
//	Get information from the user for each of the Invoice Header fields
//	
	private void setCustomerInformation() {
		invoice.setCustomerInformation(	this.invoiceNumber, 
										getInfo("Enter Customer Name"), 
										getInfo("Enter Customer Address, "
												+ "Use comma for lines"),
										getInfo("Enter Customer Country"),
										getInfo("Enter Contact Number"));
	}

//	This method is the main one that allows the user to add product to the
//	Invoice. 
//	
	private void addInvoiceLine(String invoiceNumber) {
		String productId;
		String response;
		String[] productArray = new String[6];
		int quantity;
		do {
//			Show the current customer information and products on the Invoice
//			This is done to allow the user access to this information via
//			the current screen without searching back
			response = "\nCustomer Invoice: " + 
					invoice.getCustomerInformation() + 
					"\n\n" + invoice.toInvoiceLinesString() +
					"\n\nSelect product to add to Invoice: " + invoiceNumber;
//			Now provide a list of products to the user to select from.
			productArray = inventory.getAllProductIDs();
			for (int x = 0; x < productArray.length; x++) {
				response += "\nProduct: " + x + ", " + productArray[x] +
						", " + inventory.getDescription(productArray[x]);
			}
			response += "\nX to exit from Add Purchase";
			System.out.println(response);
			productId = getInfo("Select Number or Product ID").toUpperCase();
//			Now process the selection, report the results of a bad selection
//			but a good selection uses the code below to provide a positive
//			result to the user
			if (productId.matches("[xX]")) break;
			if (productId.matches("[0123456]") && 
				Integer.parseInt(productId) < productArray.length)
				productId = productArray[Integer.parseInt(productId)];
			else {
				System.out.println("Invalid option selected, " + productId);
				continue;
			}
//			Finally, report the selection to the user, allocated the stock, 
//			and, if all reports back ok, create the Invoice line
			System.out.println("You selected " + 
					inventory.getDescription(productId) +
					"\nThere are " + inventory.getStockLevel(productId) +
					" left\nThey cost " + 
					String.format("$%-,10.2f", inventory.getPrice(productId)));
			quantity = Integer.valueOf(getInfo("How many do you need?"));
			response = allocateStock(productId, quantity);
			if (!(response.startsWith("Exiting") || 
					response.startsWith("Invalid"))) {
				if (quantity > inventory.getStockLevel(productId)) {
					invoice.addInvoiceLine(invoiceNumber, 
							invoice.getNumberOfInvoiceLines(),
							productId, 
							inventory.getDescription(productId), 
							quantity,
							inventory.getPrice(productId) * quantity);
					
				}
			} else break;
		} while (true);
		System.out.println(response);
	}

//	Method to take stock from Inventory class and perform simple error
//	recovery. Most of this method revolves around handling the error code
//	returned from the Inventory system, but some of this is redundent due 
//	previous checks done before coming to this point.
//	
	private String allocateStock(String productId, int quantity) {
		int stockQuantity = inventory.removeStock(productId, quantity);
		String response = "";
		switch (stockQuantity) {
			case -3: {
				response = "Invalid supply, you require more than in stock" +
						"You require " + quantity + " and there are only " +
						inventory.getStockLevel(productId) + "in stock";
				response = reorderStock(productId, quantity);
				break;
			}
			case -2: {
//				This case is slighty silly because I wanted a way for the
//				user to exit the function without looking cobbled up
				if (quantity == 0) {
					response = getInfo("Do you wish to exit Add Purchase " +
							"option (Y/N)? ");
					if (response.matches("[Yy]_*")) {
						response = "Exiting Add Purchase option";
					}
				}
				if (!response.startsWith("Exiting")) {
					response = "Invalid quantity of " +
							quantity + ".  Try again!";
				}
				break;
			}
			case -1: {
				response = "Invalid product of " +
						productId + ".  Try again!";
				break;
			}
			case 0:
			case 1: {
				response = reorderStock(productId, quantity);
			}
			default: {
//				Only create the Invoice line if the stock is good
				invoice.addInvoiceLine(this.invoiceNumber,
						invoice.getNumberOfInvoiceLines(),
						productId, 
						inventory.getDescription(productId), 
						quantity, 
						inventory.getPrice(productId) * quantity);
				System.out.println("invoice.getNumberOfInvoiceLines()" + 
						invoice.getNumberOfInvoiceLines());
			}
		}
		return response;
	}

//	This method allows the user to add stock to the stock on hand for the
//	currently selected Inventory item. It is a very simple system, but has
//	a good range of data entry checking with feedback of results. 
//	It originally had lots of confirmation prompts, but made the code really
//	heavy without much benefit to the user.
//	
	private String reorderStock(String productId, int quantity) {
		String response;
		response = getInfo("Do you wish to order more " + 
				inventory.getDescription(productId) +
				", " +
				inventory.getStockLevel(productId) +
				"? option (Y/N)? ");
		if (response.matches("[Yy]_*")) {
			quantity = Integer.valueOf(getInfo("How many " + productId +
					", " + inventory.getDescription(productId) + 
					" do you want to order?"));
			int stockQuantity = inventory.orderStock(productId, quantity);
			switch (stockQuantity) {
				case -2: {
					response = "Invalid order quantity of " +
							quantity + ".  Try again!";
					break;
				}
				case -1: {
					response = "Invalid product of " +
							productId + ".  Try again!";
					break;
				}
				default: {
					response = "You have successfully ordered " +
							productId + ", " + 
							inventory.getDescription(productId) +
							".  Stock on Hand is now " +
							stockQuantity + " items.";
					break;
				}
			} 
		} else {
			response = "No stock ordered!";
		}
		return response;
	}
		
//	This method is the main one that allows the user to add fees to the
//	Invoice. 
//	
	private void addInvoiceFeeLine(String invoiceNumber) {
		String[] feeList = {"Delivery Fee - Australia", 
				"Delivery Fee - New Zealand",
				"Delivery Fee - USA",
				"Insurance Fee"};
		double[] feeCharge = {9.95, 20.00, 37.96, 9.95};
		String feeId;
		int feeSelection;
		int feeLine;
		String response = invoice.toInvoiceFeeString();
		boolean validFee = true;
		do {
//			Show the current customer information and fees on the Invoice
//			This is done to allow the user access to this information via
//			the current screen without searching back
			response += "\n" + invoice.getCustomerInformation() + 
					"\nSelect fee to add to Invoice: " + invoiceNumber + "\n";
			for (int x = 0; x < feeList.length; x++) {
				response += "\nFee: " + x + ", " + feeList[x] +
						", " + feeCharge[x];
			}
//			Now provide a list of fees to the user to select from.
			response += "\n\nUse X to exit from Add Fee";
			System.out.println("This is the list of Fees" + response);
			feeId = getInfo("Enter the Number of Fee to add").toUpperCase();
			if (feeId.matches("[xX]")) break;
			if (feeId.matches("[0123]"))
				feeSelection = Integer.parseInt(feeId);
			else {
				System.out.println("Invalid option selected, " + feeId);
				continue;
			}
//			Now process the selection, report the results of a bad selection
//			but a good selection uses the code above to provide a positive
//			result to the user
			System.out.println("\nYou selected: " + 
					feeList[feeSelection] +
					", at the cost of " + 
					String.format("$%-,10.2f", feeCharge[feeSelection]));
			for (int x = 0; x < invoice.getNumberOfInvoiceFeeLines(); x++) {
				if (feeList[feeSelection].equalsIgnoreCase(
						invoice.getInvoiceFeeLineDescription(x))) {
					response += "\nInvalid Charge " + feeList[feeSelection] + 
							" already exists in Invoice";
					validFee = false;
				}
			}
			if (validFee)
				invoice.addInvoiceFeeLine(invoiceNumber, 
						invoice.getNumberOfInvoiceFeeLines(), feeId,
						feeList[feeSelection], feeCharge[feeSelection]);
				else
					validFee = true;
			response += invoice.toInvoiceFeeString();
			System.out.println(response);
		} while (true);
		System.out.println(response);
	}

//	Get input from the user and keep prompting until they enter a  
//	non blank one
//	
	private String getInfo(String promptForUser) {
		String userInput;
		System.out.println("\n" + promptForUser + "?\n");
		do {
			userInput = input.nextLine();
			if (userInput.isEmpty() || userInput.isBlank()) {
				System.out.println("\nError, try again. " + 
						promptForUser + "\n");
			}
		} while (userInput.isEmpty() || userInput.isBlank());
		return userInput;
	}

}
