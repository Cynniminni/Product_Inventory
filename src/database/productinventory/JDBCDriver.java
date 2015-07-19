package database.productinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.productinventory.*;

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
	public static String divider = "==============================";
	public static String format = "%20s %10s %10s %15s %n";
	
	/**
	 * Tests the connection to the database and prints the result.
	 */
	public static void testConnection() {
		try {			
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
			
			System.out.println("[debug]: Connection successful.");				
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}//end try
	}//end testconnection	
		
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
	 * Queries the database for a product using the product name.
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
	 * Adds a new product to the database.
	 * @param product The new product to add.
	 */
	public static void insertProduct(Product product) {
		try {
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
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}//end insert product
	
	/**
	 * Deletes a selected product that matches the name.
	 * @param name The name of the product to be deleted.
	 */
	public static void deleteProduct(String name) {
		try {
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
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}//end delete product
	
	/**
	 * Updates a selected product with new information given by the user.
	 * @param oldName The product to be updated. Since the user may change the name,
	 * 'oldName' will be used to refer to the original product before alteration.
	 * @param product The 'new' product that will be used to update the old one.
	 */
	public static void updateProduct(String oldName, Product product) {
		try {
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
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}//end update product
}//end class
