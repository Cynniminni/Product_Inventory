package models.productinventory;

public class Product {

	private String name;
	private int quantity;
	private double price;
	private int id;
	private String category;
	
	public Product (String name, int quantity, double price, int id, String category) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.id = id;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}//end class
