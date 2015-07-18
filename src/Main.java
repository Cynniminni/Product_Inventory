import database.productinventory.JDBCDriver;


public class Main {

	public static void main(String[] args) {
		//runs all components for project
		// initialize GUI
		InventoryGUI gui = new InventoryGUI();
		
		// establish database connection
		JDBCDriver.testConnection();
				
		// query database  
		// print inventory into text area				
		gui.updateText(JDBCDriver.selectTable());
		
		// user can add, edit, or remove a product
		// 
		// to close, the user clicks on the X button
	}//end main
	
}//end class
