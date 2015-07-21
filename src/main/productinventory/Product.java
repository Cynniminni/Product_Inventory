package main.productinventory;

public class Product {
	private String name;
	private String category;
	private int quantity;
	private double price;
	private String format = "%15s %5s %5s %15s %n";
	
	public Product() {
		name = "Default Name";
		quantity = 1;
		price = 0.99;
		category = "Misc";
	}
	
	public Product(String name, int quantity, double price, String category) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
	}
	
	@Override
	public String toString() {
		return String.format(format, name, quantity, price, category);
	}
	
	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getQuantity() {		
		return quantity;		
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}//end class
