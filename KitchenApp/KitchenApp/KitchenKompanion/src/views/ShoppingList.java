package views;

import javax.swing.JPanel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

public class ShoppingList extends JPanel {
	ArrayList<FoodItem> purchasedItems;
	ArrayList<ShopList> lists;
	JComboBox comboBox;
	private JButton btnFinish;
	private JLabel lblKeyboard;
	/**
	 * Create the panel.
	 */
	public ShoppingList() {

		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		lists = new ArrayList<ShopList>();
		purchasedItems = new ArrayList<>();
		lists.add(null);
		setLayout(null);
		
		//On screen Keyboard
		lblKeyboard = new JLabel("");
		lblKeyboard.setBounds(65, 302, 273, 183);
		lblKeyboard.setIcon(new ImageIcon(this.getClass().getResource("/phone.png")));
		add(lblKeyboard);
		lblKeyboard.setVisible(false);
		
		
		//Lists Dropdown
		comboBox = new JComboBox();
		comboBox.setBounds(85, 11, 178, 23);
		comboBox.addItem("Welcome");
		JTextArea welcomeText = new JTextArea();
		welcomeText.setBounds(75, 81, 259, 353);
			
		//List Area 
		JList list = new JList();
		list.setBounds(75, 81, 259, 353);
		DefaultListModel lm = new DefaultListModel();
		list.setModel(lm);
		JButton btnCart = new JButton("Add to Cart");
		btnCart.setBounds(148, 44, 118, 23);
		btnCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShopList shoppingList = lists.get(comboBox.getSelectedIndex());
				FoodItem item = shoppingList.getItem(((String)list.getSelectedValue()).replace("(IN CART)", ""));
				if(shoppingList.itemIsInCart(item)) {
					shoppingList.removeFromCart(item);
				} else {
					shoppingList.addToCart(item);
				}
			}
		});
		btnCart.setEnabled(false);
		add(btnCart);
		
		JButton btnRemove = new JButton("Remove Item");
		btnRemove.setBounds(276, 44, 118, 23);
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShopList shoppingList = lists.get(comboBox.getSelectedIndex());
				FoodItem item = shoppingList.getItem(((String)list.getSelectedValue()).replace("(IN CART)", ""));
				shoppingList.removeFromList(item);
			}
		});
		btnRemove.setEnabled(false);
		add(btnRemove);
		//Double Click to view Item in List 
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(list.getSelectedIndex() == -1) {
					btnCart.setEnabled(false);
					btnRemove.setEnabled(false);
				} else {
					btnCart.setEnabled(true);
					btnRemove.setEnabled(true);
				}
				if(e.getClickCount() == 2) {
					ShopList currList = lists.get(comboBox.getSelectedIndex());
					FoodItem item = currList.getItem(((String) list.getSelectedValue()).replace("(IN CART)" , ""));
					JTextField name = new JTextField(item.name);
					JTextField quantity = new JTextField(String.valueOf(item.quantity));
					JTextField category = new JTextField(item.category);
					Object[] dialog = {
							"Item Name:", name,
							"Category:", category,
							"Quantity: (numerical)", quantity
					};
					int option = JOptionPane.showConfirmDialog(null, dialog, "Item Details", JOptionPane.OK_CANCEL_OPTION);
					if(option == JOptionPane.OK_OPTION) {
						currList.editItem(item, new FoodItem(name.getText(),category.getText(),Integer.parseInt(quantity.getText())), currList.itemIsInCart(item));
					}
				}	
			}
		});
		add(list);
		list.setVisible(false);
		
		//Default welcome text if no list selected
		welcomeText.append("Manage your shopping lists here.\r\n"
				+ "Create a list, then add or remove items.\r\n"
				+ "Double click an item to view and edit it's info.\r\n"
				+ "Add items to your cart as you shop.\r\n"
				+ "When you are finished shopping,\r\n"
				+ "select \"Finish Shopping!\" to add the items\r\n"
				+ "to your inventory automatically!");
		add(welcomeText);
		//Switching lists 
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//If welcome, show welcome text
				if((String) comboBox.getSelectedItem() == "Welcome") {
					if(list.isVisible()) {
						list.setVisible(false);
					}
					welcomeText.setVisible(true);
				} else {
					welcomeText.setVisible(false);
					ShopList listChoice = lists.get(comboBox.getSelectedIndex());
					list.setModel(listChoice.listOfItemNames);
					list.setVisible(true);
				}	
			}
		});
		add(comboBox);
		JButton btnList = new JButton("Create List");
		btnList.setBounds(273, 11, 118, 23);
		btnList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblKeyboard.setVisible(true);
				String newListName =  JOptionPane.showInputDialog("New List Name: ");
				lists.add(new ShopList(newListName));
				comboBox.addItem(newListName);
				comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
				lblKeyboard.setVisible(false);
			}
		});
		add(btnList);
		
		JLabel lblList = new JLabel("Select a list:");
		lblList.setBounds(10, 15, 75, 14);
		add(lblList);
		
		JButton btnAdd = new JButton("Add Item");
		btnAdd.setBounds(20, 44, 118, 23);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() != 0) {
					lblKeyboard.setVisible(true);
					JTextField name = new JTextField();
					JTextField quantity = new JTextField();
					JTextField category = new JTextField();
					Object[] dialog = {
							"Item Name:", name,
							"Category:", category,
							"Quantity: (numerical)", quantity
					};
					int option = JOptionPane.showConfirmDialog(null, dialog, "New Item", JOptionPane.OK_CANCEL_OPTION);
					if(option == JOptionPane.OK_OPTION) {
						lists.get(comboBox.getSelectedIndex()).addToList(new FoodItem(name.getText(),category.getText(), Integer.parseInt(quantity.getText())));
					}
					lblKeyboard.setVisible(false);
				} else {
					JOptionPane.showConfirmDialog(null, "Please select valid list", "Notice", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		});
		add(btnAdd);
		
		btnFinish = new JButton("Finish Shopping!");
		btnFinish.setBounds(95, 445, 214, 40);
		btnFinish.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JCheckBox keep = new JCheckBox();
				Object[] dialog = {
						"Click OK to Add Purchased items to your Inventory",
						" ",
						"Would you like to keep bought items on list? (check to keep)",
						keep
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "Finish Shopping?", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					purchasedItems.addAll(lists.get(comboBox.getSelectedIndex()).getCart());
				} ShopList shoppingList = lists.get(comboBox.getSelectedIndex());
				if (keep.isSelected()) {
					for (FoodItem item : shoppingList.items) {
						if (shoppingList.itemIsInCart(item)) {
							shoppingList.removeFromCart(item);
						}
					}
				} else {
					for (int i = 0; i < shoppingList.items.size(); i++) {
						if (shoppingList.itemIsInCart(shoppingList.items.get(i))) {
							shoppingList.removeFromList(shoppingList.items.get(i));
						}
					}
				}
			}
		});
		add(btnFinish);
	}
	
	protected ArrayList<FoodItem> getPurchases(){
		return purchasedItems;
	}
	
	protected void clearPurchases() {
		purchasedItems.clear();
	}

	public void addRemoved(ArrayList<FoodItem> removed) {
		if (!removed.isEmpty()) {
			lblKeyboard.setVisible(true);
			boolean found;
			JTextField name = new JTextField();
			JTextField quantity = new JTextField();
			JTextField category = new JTextField();
			JTextField list = new JTextField();
			while (!removed.isEmpty()) {
				found = false;
				name.setText(removed.get(0).name);
				category.setText(removed.get(0).category);
				quantity.setText("");
				Object[] dialog = {
						"Item Name:", name,
						"Category: ", category,
						"Quantity: (numerical)", quantity,
						"List to add to:", list
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "Looks like you ran out of " + name.getText(), JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					for (int i = 1; i < comboBox.getItemCount(); i++) {
						if (list.getText().equals(comboBox.getItemAt(i).toString())) {
							found = true;
							comboBox.setSelectedIndex(i);
							lists.get(comboBox.getSelectedIndex()).addToList(new FoodItem(name.getText(),category.getText(), Integer.parseInt(quantity.getText())));
						}
					} if (!found) {
						lists.add(new ShopList(list.getText()));
						comboBox.addItem(list.getText());
						comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
						lists.get(comboBox.getSelectedIndex()).addToList(new FoodItem(name.getText(),category.getText(), Integer.parseInt(quantity.getText())));
					}
				}
				removed.remove(0);
			}
			lblKeyboard.setVisible(false);
		}
	}

	protected void addMissing(ArrayList<FoodItem> checkInv, String n) {
		if (!checkInv.isEmpty()) {
			boolean found;
			JTextField name = new JTextField();
			JTextField quantity = new JTextField();
			JTextField category = new JTextField();
			JTextField list = new JTextField();
			while (!checkInv.isEmpty()) {
				found = false;
				name.setText(checkInv.get(0).name);
				category.setText("");
				quantity.setText(Integer.toString(checkInv.get(0).quantity));
				Object[] dialog = {
						"Looks like you ran out of " + name.getText(), " ",
						"Item Name:", name,
						"Category: ", category,
						"Quantity: (numerical)", quantity,
						"List to add to:", list
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "Checked Recipe: " + n, JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					for (int i = 1; i < comboBox.getItemCount(); i++) {
						if (list.getText().equals(comboBox.getItemAt(i).toString())) {
							found = true;
							comboBox.setSelectedIndex(i);
							lists.get(comboBox.getSelectedIndex()).addToList(new FoodItem(name.getText(),category.getText(), Integer.parseInt(quantity.getText())));
						}
					} if (!found) {
						lists.add(new ShopList(list.getText()));
						comboBox.addItem(list.getText());
						comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
						lists.get(comboBox.getSelectedIndex()).addToList(new FoodItem(name.getText(),category.getText(), Integer.parseInt(quantity.getText())));
					}
				}
				checkInv.remove(0);
			}
			JOptionPane.showConfirmDialog(null, "All Missing Ingredients Added to List", "Checked Recipe: " + n, JOptionPane.OK_CANCEL_OPTION);
		} else {
			JOptionPane.showConfirmDialog(null, "All Ingredients Accounted For", "Checked Recipe: " + n, JOptionPane.OK_CANCEL_OPTION);
		}
	}
}