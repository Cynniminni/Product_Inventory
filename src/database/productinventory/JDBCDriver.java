package database.productinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDriver {

	//**********************************
	//	initialize
	//**********************************
	static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/inventory";
	static final String SELECT_PRODUCTS = "SELECT * FROM products";
	static final String USER = "root";
	static final String PASS = "Ajo1000mbw1s!!";
	
	public static String divider = "=======================================";
	public static String format = "%3s %20s %10s %10s %15s %n";
	
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
					"ID",
					"Product Name",
					"Quantity",
					"Price",
					"Category");
			System.out.println(divider + divider);
			
			//process the result set
			while (result.next()) {
				System.out.format(format, 
						result.getString("id"),
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
	}//end printtable
		
	//**********************************
	//	query methods
	//**********************************
	public static void insertProduct() {
		
	}
	
	public static void deleteProduct() {
		
	}
	
	public static void updateProduct() {
		
	}

	public static void main(String[] args) {
		testConnection();
		printTable();
	}//end main
	
}//end class
