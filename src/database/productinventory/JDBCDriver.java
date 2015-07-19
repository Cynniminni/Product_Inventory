package database.productinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.productinventory.*;

public class JDBCDriver {

	//**********************************
	//	initialize
	//**********************************
	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/inventory";
	private static final String USER = "root";
	private static final String PASS = "Ajo1000mbw1s!!";
	
	public static final String SELECT_PRODUCTS = "SELECT * FROM products ORDER BY category";
	public static String divider = "==============================";
	public static String format = "%20s %10s %10s %15s %n";
	
	//**********************************
	//	test methods
	//**********************************
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
	
	public static void printTable() {		
		try {
			//get connection to the database
			Connection conn = DriverManager.getConnection(
					JDBC_CONNECTION, USER, PASS);
			
			System.out.println("[debug]: Printing table \'products\'.");
			System.out.println();
			
			//create a statement
			Statement statement = conn.createStatement();
			
			//execute a query
			ResultSet result = statement.executeQuery(SELECT_PRODUCTS);
			
			System.out.println(divider + divider);
			System.out.format(format, 
					"Product Name",
					"Quantity",
					"Price",
					"Category");
			System.out.println(divider + divider);			
			
			//process the result set
			while (result.next()) {
				System.out.format(format,
						result.getString("name"),
						result.getString("quantity"),
						result.getString("price"),
						result.getString("category")).toString();				
			}//end while
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}//end printtable
	
	//**********************************
	//	select table & return as string
	//**********************************
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
		
	//**********************************
	//	query methods
	//**********************************
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
	}

	/*
	public static void main(String[] args) {
		testConnection();
		printTable();
	}//end main
	*/
	
}//end class
