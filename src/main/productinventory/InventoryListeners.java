package main.productinventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * InventoryListeners produces button listeners for the search,
 * refresh, add, edit, and remove product functions in the
 * InventoryGUI class.
 * 
 * @author Cynthia
 *
 */
public class InventoryListeners {	
	
	private static JTextField searchBar;
	
	public InventoryListeners(JTextField searchBar) {
		this.searchBar = searchBar;
	}	
	
	/**
	 * Check if the given string is an integer.
	 * @param s
	 * @return The boolean to indicate true or false.
	 */
	private static boolean isInteger(String s) {
		boolean isInt = false;
		
		try {
			Integer.parseInt(s);
			isInt = true;
		} catch (NumberFormatException e) {
			//not valid int
		}//end try catch
		
		return isInt;
	}//end is integer
	
	/**
	 * Check if the given string is a double.
	 * @param s
	 * @return The boolean to indicate true or false.
	 */
	private static boolean isDouble(String s) {
		boolean isDouble = false;
		
		try {
			Double.parseDouble(s);
			isDouble = true;
		} catch (NumberFormatException e) {
			//not valid int
		}//end try catch
		
		return isDouble;
	}//end is double		
	
	public static Action editRowAction() {
		return new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				TableCellListener tcl = (TableCellListener)e.getSource();
//		        System.out.println("Row   : " + tcl.getRow());
//		        System.out.println("Column: " + tcl.getColumn());
//		        System.out.println("Old   : " + tcl.getOldValue());
//		        System.out.println("New   : " + tcl.getNewValue());
			}
			
		};
	}
	
	/**
	 * This method will take the text from the searchbar, check
	 * if it is an int, double, or string, and then query the
	 * database for any matches.
	 * @return The ActionListener for searching.
	 */
	public static ActionListener searchProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				System.out.println("[debug]: Test search product!");
				
				String userInput = searchBar.getText();
				Product product = null;
				if (isInteger(userInput)) {
					//search products by quantity
					product = JDBCDriver.selectProduct(Integer.valueOf(userInput));
				} else if (isDouble(userInput)) {
					//search products by price
					product = JDBCDriver.selectProduct(Double.valueOf(userInput));
				} else {
					//search products by name
					product = JDBCDriver.selectProduct(userInput);		
				}//end if-else
				
				//format the result into a string
				String result = String.format(JDBCDriver.format, 
						product.getName(),
						product.getQuantity(),
						product.getPrice(),
						product.getCategory());
				
				//update text view with the string
				InventoryGUI.updateInventory(result);
			}
			
		};
	}//end editproduct
	
	/**
	 * Queries database to return all products and updates
	 * the inventory view.
	 * @return The ActionListener for refreshing.
	 */
	public static ActionListener refreshProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				//System.out.println("[debug]: Test refresh product!");
				InventoryGUI.updateInventory();
			}			
		};
	}//end editproduct
	
	/**
	 * Takes user input to create a new product and adds it 
	 * to the database.
	 * @return The ActionListener for adding a new product.
	 */
	public static ActionListener addProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database				
				int result = InventoryPopups.showInputPopup(InventoryPopups.ADD, null);
				
				if (result == JOptionPane.OK_OPTION) {										
					try {
						//get user input and save as a product
						Product product = new Product(InventoryPopups.getName(), 
								InventoryPopups.getQuantityInt(), 
								InventoryPopups.getPriceDouble(), 
								InventoryPopups.getCategory());
						
						//insert into database
						JDBCDriver.insertProduct(product);
						InventoryGUI.updateInventory();
					} catch (SQLException|NumberFormatException ex) {
						InventoryPopups.showErrorPopup("Product may already exist, or"
								+ "\nthere may be empty fields.");
						//ex.printStackTrace();
					}														
				}//end if
			}			
		};
	}//end addproduct
	
	/**
	 * Users select a product by name, after which they will edit
	 * the existing fields for that product. Then the selected product
	 * will be updated in the database.
	 * @return The ActionListener for editing an existing product.
	 */
	public static ActionListener editProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("[debug]: Test edit product!");
				//prompt user to choose a product by name
				int result = InventoryPopups.showSelectPopup();
				
				//if user clicked OK
				if (result == JOptionPane.OK_OPTION) {
					//save old name of product selected
					String oldName = InventoryPopups.getName();
					
					//query the selected product
					Product product = JDBCDriver.selectProduct(oldName);
									
					//populate fields and prompt user to edit them
					int editResult = InventoryPopups.showInputPopup(InventoryPopups.EDIT, product);
					
					if (editResult == JOptionPane.OK_OPTION) {						
						try {
							Product update = new Product(InventoryPopups.getName(),
									InventoryPopups.getQuantityInt(),
									InventoryPopups.getPriceDouble(),
									InventoryPopups.getCategory());						

							//update the product with new info 
							JDBCDriver.updateProduct(oldName, update);
							InventoryGUI.updateInventory();
						} catch (SQLException|NumberFormatException ex) {
							InventoryPopups.showErrorPopup("Please fill all fields correctly.");
							//ex.printStackTrace();
						}						
					}//end inner if
				}//end if
			}			
		};
	}//end editproduct
		
	/**
	 * User chooses the product to remove by name. A popup will appear
	 * confirming the user's choice, and then removes the product from
	 * the database.
	 * @return The ActionListener for removing an existing product.
	 */
	public static ActionListener removeProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				System.out.println("[debug]: Test remove product!");
				int choice = InventoryPopups.showSelectPopup();
				
				if (choice == JOptionPane.OK_OPTION) {					
					String name = InventoryPopups.getName();
					int result = InventoryPopups.showConfirmationPopup(name);
					
					if (result == JOptionPane.OK_OPTION) {
						try {
							JDBCDriver.deleteProduct(name);
							InventoryGUI.updateInventory();
						} catch (SQLException ex) {
							InventoryPopups.showErrorPopup("Product must exist to be deleted.");
							//ex.printStackTrace();
						}						
					}//end inner if
				}//end if
			}
			
		};
	}//end editproduct	
}//end class
