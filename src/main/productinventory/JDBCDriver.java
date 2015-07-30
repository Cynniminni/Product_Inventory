package main.productinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

/**
 * JDBCDriver establishes a connection to the database and provides the methods to 
 * perform specific queries to.
 * 
 * @author Cynthia
 *
 */

public class JDBCDriver {

	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/inventory";
	private static final String USER = "root";
	private static final String PASS = "RootPass2015";
	
	public static final String SELECT_PRODUCTS = "SELECT * FROM products ORDER BY category";
	private static final Object[] COL_NAMES = new Object[] { 
		"Product Name",
		"Quantity",
		"Price",
		"Category"
	};	
	public static String divider = "==============================";
	public static String format = "%20s %10s %10s %15s %n";
	
	public static DefaultTableModel productToModel(Product product) {
		NonEditableModel model = new NonEditableModel();
		Object[] row = new Object[COL_NAMES.length];
		row[InventoryConstants.NAME] = product.getName();
		row[InventoryConstants.QUANTITY] = product.getQuantity();
		row[InventoryConstants.PRICE] = product.getPrice();
		row[InventoryConstants.CATEGORY] = product.getCategory();
		
		model.setColumnIdentifiers(COL_NAMES);
		model.addRow(row);
		
		return model;
	}
	
	/**
	 * Selects all from the database, takes the ResultSet and 
	 * converts it into a DefaultTableModel for use with a JTable.
	 * @return The DefaultTableModel representing the database.
	 */
	public static DefaultTableModel getDataModel() {				
		NonEditableModel model = new NonEditableModel();
		
		try {
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);		
			Statement statement = conn.createStatement();			
			ResultSet rs = statement.executeQuery(SELECT_PRODUCTS);					
			
			//get metadata from resultset, which has column info
			ResultSetMetaData md = rs.getMetaData();
			int numOfCol = md.getColumnCount();
			
			//set the column names in the model
			model.setColumnIdentifiers(COL_NAMES);			
			
			//process resultset into rows and add to model 
			while (rs.next()) {
				Object[] row = new Object[numOfCol];
				
				for (int i = 0; i < numOfCol; i++) {
					row[i] = rs.getObject(i + 1);
				}//end for
				
				model.addRow(row);
			}//end while					
												
			//close connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end try
		
		return model;
	}//end selectall
	
	/**
	 * A class that overrides the isCellEditable method so that users cannot
	 * edit the cells of the JTable by double-clicking
	 *
	 */
	private static class NonEditableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	/**
	 * Tests the connection to the database and prints the result.
	 */
	public static void testConnection() {
		try {			
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
			
			//System.out.println("[debug]: Connection successful.");				
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}//end try
	}//end testconnection	
		
	public static ResultSet getResultSet() {
		ResultSet rs = null;
		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
		
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			rs = statement.executeQuery(SELECT_PRODUCTS);					
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	/**
	 * Queries the database and returns all products as a formatted String.
	 * @return A String representing all products currently in the database.
	 */
	public static String selectTable() {
		String resultText = "";
		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
		
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			ResultSet result = statement.executeQuery(SELECT_PRODUCTS);
			
			resultText = divider + divider + "\n";
			resultText += String.format(format, 
					"Product",
					"Quantity",
					"Price",
					"Category");
			resultText += divider + divider + "\n";
						
			//process the result set
			while (result.next()) {
				resultText += String.format(format, 
						result.getString("name"),
						result.getString("quantity"),
						result.getString("price"),
						result.getString("category"));					
			}//end while
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return resultText;
	}			
	
	
	/**
	 * Select a product by name.
	 * @param name The name of the product.
	 * @return A product matching that of the given name, if any.
	 */
	public static Product selectProduct(String name) {
		Product product = new Product();
		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
		
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			ResultSet result = statement.executeQuery(
					"SELECT * FROM products WHERE name = \'" + name + "\'");			
			
			while (result.next()) {
				product = new Product(
						result.getString("name"),
						Integer.parseInt(result.getString("quantity")),
						Double.parseDouble(result.getString("price")),					
						result.getString("category"));
			}					
			
			conn.close();
					
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return product;		
	}//end select product
	
	/**
	 * Select a product by quantity.
	 * @param quantity
	 * @return A product that matches the quantity.
	 */
	public static Product selectProduct(int quantity) {
		Product product = new Product();
		Product[] products = null;
		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
		
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			ResultSet result = statement.executeQuery(
					"SELECT * FROM products WHERE quantity = " + quantity);			
			
			products = new Product[result.getMetaData().getColumnCount()];
			
			while (result.next()) {
//				product = new Product(
//						result.getString("name"),
//						Integer.parseInt(result.getString("quantity")),
//						Double.parseDouble(result.getString("price")),					
//						result.getString("category"));
				
				
			}					
			
			conn.close();
					
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return product;		
	}//end select product by quantity
	
	/**
	 * Select a product by price.
	 * @param price
	 * @return A product that matches the price.
	 */
	public static Product selectProduct(double price) {
		Product product = new Product();
		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
		
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			ResultSet result = statement.executeQuery(
					"SELECT * FROM products WHERE price = " + price);			
			
			while (result.next()) {
				product = new Product(
						result.getString("name"),
						Integer.parseInt(result.getString("quantity")),
						Double.parseDouble(result.getString("price")),					
						result.getString("category"));
			}					
			
			conn.close();
					
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return product;		
	}//end select product by quantity
	
	/**
	 * Adds a new product to the database.
	 * @param product The new product to add.
	 */
	public static void insertProduct(Product product) throws SQLException {		
		//get connection to the database
		Connection conn = DriverManager.getConnection(
				JDBC_CONNECTION, USER, PASS);
	
		//create a statement
		Statement statement = conn.createStatement();
		
		//execute a query
		statement.executeUpdate(
				"INSERT INTO products " + 
				"VALUES (\'" + product.getName() + "\'" +
				", " + product.getQuantity() + 
				", " + product.getPrice() + 
				", \'" + product.getCategory() + "\')");					
		
		conn.close();							
	}//end insert product
	
	/**
	 * Deletes a selected product that matches the name.
	 * @param name The name of the product to be deleted.
	 */
	public static void deleteProduct(String name)  throws SQLException {		
		//get connection to the database
		Connection conn = DriverManager.getConnection(
				JDBC_CONNECTION, USER, PASS);
	
		//create a statement
		Statement statement = conn.createStatement();
		
		//execute a query
		statement.executeUpdate(
				"DELETE FROM products " + 
				"WHERE name = \'" + name + "\'");					
		
		conn.close();		
	}//end delete product
	
	/**
	 * Updates a selected product with new information given by the user.
	 * @param oldName The product to be updated. Since the user may change the name,
	 * 'oldName' will be used to refer to the original product before alteration.
	 * @param product The 'new' product that will be used to update the old one.
	 */
	public static void updateProduct(String oldName, Product product) throws SQLException {	
		//get connection to the database
		Connection conn = DriverManager.getConnection(
				JDBC_CONNECTION, USER, PASS);
	
		//create a statement
		Statement statement = conn.createStatement();
		
		//execute a query
		statement.executeUpdate(
				"UPDATE products SET name = " + "\'" + product.getName() + "\'" + 
				", quantity = " + product.getQuantity() + 
				", price = " + product.getPrice() + 
				", category = " + "\'" + product.getCategory() + "\'" +
				" WHERE name = " + "\'" + oldName + "\'");					
		
		conn.close();		
	}//end update product
}//end class
