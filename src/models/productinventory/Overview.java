package models.productinventory;

import java.util.ArrayList;

public class Overview {
	public ArrayList<Product> products;
	
	public Overview() {
		products = new ArrayList<Product>();//create empty overview
	}
	
	public void addProduct(Product p) {
		products.add(p);
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public ArrayList<Product> searchProductsByPrice(double price) {
		//do some searching
		//get result in form of arraylist
		//return it
		return products;
	}
	
	public void sortByCategory() {
		
	}
}//end class
