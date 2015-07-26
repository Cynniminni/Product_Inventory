package main.productinventory;


/**
 * Initializes the GUI, establishes connection, and prints
 * the list of products into the text area. 
 * 
 * @author Cynthia
 *
 */
public class Main {

	
	public static void main(String[] args) {
		// initialize GUI
		InventoryGUI gui = new InventoryGUI();
		
		// establish database connection
		JDBCDriver.testConnection();
				
		// print inventory into text area				
		gui.updateInventory();		
	}//end main
	
		
}//end class
