package main.productinventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	private static Product editProduct;
	
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
	
	public static MouseListener makeSelectionListener(final JTable table) {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//get old product								
				int selectedRow = table.getSelectedRow();
				
				String oldKey = (String) table.getValueAt(selectedRow, InventoryConstants.NAME);
				int quantity = (int) table.getValueAt(selectedRow, InventoryConstants.QUANTITY);
				double price = (double) table.getValueAt(selectedRow, InventoryConstants.PRICE);
				String category = (String) table.getValueAt(selectedRow, InventoryConstants.CATEGORY);
				
				editProduct = new Product(
						oldKey,
						quantity,
						price,
						category);
				
				System.out.println("[debug]: row = " + selectedRow + ", edit product = " + editProduct);
			}
			
		};
	}//end make selection listener
	
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
				if (userInput.equalsIgnoreCase("")) {
					InventoryPopups.showErrorPopup("Please enter a product name.");
				} else if (isDouble(userInput)) {//search by price
					double inputNum = Double.valueOf(userInput);										
					DefaultTableModel result = JDBCDriver.selectProducts(inputNum);					
					InventoryGUI.updateInventory(result);
				} else {
					DefaultTableModel result = JDBCDriver.selectProducts(userInput);					
					InventoryGUI.updateInventory(result);
				}//end if-else				
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
	 * Edits the selected product in a popup window.
	 * @return The ActionListener for editing an existing product.
	 */
	public static ActionListener editProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("[debug]: Test edit product!");
					//populate fields and prompt user to edit them
					int editResult = InventoryPopups.showInputPopup(InventoryPopups.EDIT, editProduct);
					String oldName = editProduct.getName();
					
					if (editResult == JOptionPane.OK_OPTION) {						
						try {
							Product update = new Product(InventoryPopups.getName(),
									InventoryPopups.getQuantityInt(),
									InventoryPopups.getPriceDouble(),
									InventoryPopups.getCategory());						

							//update the product with new info 
							JDBCDriver.updateProduct(oldName, update);
							InventoryGUI.updateInventory();
							System.out.println("[debug]: Updating product");
						} catch (SQLException|NumberFormatException ex) {
							InventoryPopups.showErrorPopup("Please fill all fields correctly.");
							ex.printStackTrace();
						}						
					}//end inner if
			}			
		};
	}//end editproduct
		
	/**
	 * Deletes the product after confirming user's selection.
	 * @return The ActionListener for removing an existing product.
	 */
	public static ActionListener removeProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				int result = 0;
				String name = "";
				
				try {
					name = editProduct.getName();
					result = InventoryPopups.showConfirmationPopup(name);					
				} catch (NullPointerException ex) {
					InventoryPopups.showErrorPopup("Please select a product.");
					ex.printStackTrace();
				}
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						JDBCDriver.deleteProduct(name);
						InventoryGUI.updateInventory();
					} catch (SQLException ex) {
						InventoryPopups.showErrorPopup("Product must exist to be deleted.");
						ex.printStackTrace();
					}						
				}//end inner if
			}
			
		};
	}//end editproduct	
}//end class
