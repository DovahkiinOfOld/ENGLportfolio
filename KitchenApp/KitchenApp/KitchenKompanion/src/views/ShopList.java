package views;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ShopList {
	String name;
	DefaultListModel listOfItemNames;
	ArrayList<FoodItem> items;
	ArrayList<FoodItem> cart;
	
	
	public ShopList(String name) {
		this.name = name;
		items = new ArrayList<FoodItem>();
		cart = new ArrayList<FoodItem>();
		listOfItemNames = new DefaultListModel();
	}
	
	public void addToList(FoodItem item) {
		listOfItemNames.addElement(item.name);
		items.add(item);
	}
	
	public void removeFromList(FoodItem item) {
		if(this.itemIsInCart(item)) {
			this.removeFromCart(item);
		}
		listOfItemNames.removeElement(item.name);
		items.remove(item);
	}
	
	public void editItem(FoodItem oldItem, FoodItem newItem, boolean wasInCart) {
		int index = items.indexOf(oldItem);
		if(wasInCart) {
			this.removeFromCart(oldItem);
			this.removeFromList(oldItem);
			this.addToList(newItem);
			this.addToCart(newItem);
		} else {
			this.removeFromList(oldItem);
			this.addToList(newItem);
		}
	}
	
	public FoodItem getItem(String name) {
		for(FoodItem x : items) {
			if(x.name.equals(name)) {
				return x;
			}
		}
		return null;
	}
	
	public void addToCart(FoodItem item) {
		cart.add(item);
		int index = items.indexOf(item);
		String temp  = ((String) listOfItemNames.get(index)).concat("(IN CART)");
		listOfItemNames.set(index, temp);
	}
	
	public void removeFromCart(FoodItem item) {
		cart.remove(item);
		int index = items.indexOf(item);
		String temp = ((String) listOfItemNames.get(index)).replace("(IN CART)", "");
		listOfItemNames.set(index, temp);
	}
	
	public ArrayList<FoodItem> getCart(){
		return this.cart;
	}
	
	public boolean itemIsInCart(FoodItem item) {
		for(FoodItem x : cart) {
			if((x.name.equals(item.name)) && (x.category.equals(item.category)) && (x.quantity == item.quantity)) {
				return true;
			}
		}
		return false;
	}
}