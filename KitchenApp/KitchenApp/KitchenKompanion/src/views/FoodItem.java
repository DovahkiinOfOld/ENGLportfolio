package views;

public class FoodItem {
	String name;
	String category;
	int quantity;
	boolean isInCart;
	
	public FoodItem(String name, String category, int quantity) {
		this.name = name;
		this.category = category;
		this.quantity = quantity; 
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(int quantity) {
		this.quantity = this.quantity + quantity;
		//If you pass a negative (you use an item up) make sure you can't have negative of an item.
		if(this.quantity < 0) {
			this.quantity = 0;
		}
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isInCart() {
		return isInCart;
	}
	
	public String toString() {
		return "Item: " + this.name + "\n" + "Category: " + this.category + "\n" + "Quantity: " + this.quantity;
	}

}