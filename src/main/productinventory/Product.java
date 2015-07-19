package main.productinventory;

public class Product {
	public String name;
	public String category;
	public int quantity;
	public double price;
	
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
