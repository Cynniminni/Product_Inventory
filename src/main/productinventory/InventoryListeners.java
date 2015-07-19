package main.productinventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.productinventory.JDBCDriver;


public class InventoryListeners {	
	
	private static JTextField searchBar;
	
	public InventoryListeners(JTextField searchBar) {
		this.searchBar = searchBar;
	}
	
	public static ActionListener searchProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				System.out.println("[debug]: Test search product!");
				Product product = JDBCDriver.selectProduct(searchBar.getText());
				
				String result = String.format(JDBCDriver.format, 
						product.getName(),
						product.getQuantity(),
						product.getPrice(),
						product.getCategory());
				
				InventoryGUI.updateInventory(result);
			}
			
		};
	}//end editproduct
	
	public static ActionListener refreshProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				System.out.println("[debug]: Test refresh product!");
				InventoryGUI.updateInventory();
			}
			
		};
	}//end editproduct
	
	public static ActionListener addProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database				
				int result = InventoryPopups.showInputPopup(InventoryPopups.ADD, null);
				
				if (result == JOptionPane.OK_OPTION) {
					//get user input and save as a product
					Product product = new Product(InventoryPopups.name.getText(),
							Integer.valueOf(InventoryPopups.quantity.getText()),
							Double.valueOf(InventoryPopups.price.getText()),
							InventoryPopups.category.getText());
					
					//insert into database
					JDBCDriver.insertProduct(product);
					
					InventoryGUI.updateInventory();
					System.out.println("[debug]: Product added successfully.");
					System.out.println("[debug]: name = " + InventoryPopups.name.getText());
					System.out.println("[debug]: quantity = " + InventoryPopups.quantity.getText());
					System.out.println("[debug]: price = " + InventoryPopups.price.getText());
					System.out.println("[debug]: category = " + InventoryPopups.category.getText());
				}//end if
			}			
		};
	}//end addproduct
	
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
					String oldName = InventoryPopups.name.getText();
					
					//query the selected product
					Product product = JDBCDriver.selectProduct(oldName);
									
					//populate fields and prompt user to edit them
					int editResult = InventoryPopups.showInputPopup(InventoryPopups.EDIT, product);
					
					if (editResult == JOptionPane.OK_OPTION) {
						Product update = new Product(InventoryPopups.name.getText(),
								Integer.valueOf(InventoryPopups.quantity.getText()),
								Double.valueOf(InventoryPopups.price.getText()),
								InventoryPopups.category.getText());						
						
						//update the product with new info 
						JDBCDriver.updateProduct(oldName, update);
						
						//update inventory table
						InventoryGUI.updateInventory();
						
						System.out.println("[debug]: name = " + InventoryPopups.name.getText());
						System.out.println("[debug]: quantity = " + InventoryPopups.quantity.getText());
						System.out.println("[debug]: price = " + InventoryPopups.price.getText());
						System.out.println("[debug]: category = " + InventoryPopups.category.getText());
					}//end inner if
				}//end if
			}			
		};
	}//end editproduct
		
	public static ActionListener removeProductListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add something to the database
				System.out.println("[debug]: Test remove product!");
				int choice = InventoryPopups.showSelectPopup();
				
				if (choice == JOptionPane.OK_OPTION) {					
					String name = InventoryPopups.name.getText();
					int result = InventoryPopups.showConfirmationPopup(name);
					
					if (result == JOptionPane.OK_OPTION) {
						JDBCDriver.deleteProduct(name);
						InventoryGUI.updateInventory();
					}//end inner if
				}//end if
			}
			
		};
	}//end editproduct	
}//end class
