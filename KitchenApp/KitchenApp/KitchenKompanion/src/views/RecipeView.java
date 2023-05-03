package views;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import views.RecipeView.recipe;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class RecipeView extends JPanel {
	ArrayList<ArrayList<recipe>> recipe;
	private JComboBox<String> comboBox;
	private DefaultListModel<String> model;
	private JLabel lblKeyboard;
	private JList<String> recipeList;
	
	class recipe{
		String name;
		String ingredients;
		recipe(String name, String ingredients){
//			ArrayList<FoodItem> list = new ArrayList<FoodItem>();
			this.name = name;
			this.ingredients = ingredients;
		}
	}
	/**
	 * Create the panel.
	 * @param list 
	 * @param inv 
	 */
	public RecipeView(Inventory inv, ShoppingList list) {
		
		//On screen Keyboard
		lblKeyboard = new JLabel("");
		lblKeyboard.setBounds(65, 302, 273, 183);
		lblKeyboard.setIcon(new ImageIcon(this.getClass().getResource("/phone.png")));
		add(lblKeyboard);
		lblKeyboard.setVisible(false);
		
		recipe = new ArrayList<>();
		recipe.add(new ArrayList<recipe>());
		recipe.add(new ArrayList<recipe>());
		recipe.add(new ArrayList<recipe>());
		recipe.add(new ArrayList<recipe>());
		recipe.add(new ArrayList<recipe>());
		recipe.add(new ArrayList<recipe>());
		recipe.get(0).add(new recipe("Chicken Roast", "Chicken 1, Butter 2"));
		recipe.get(0).add(new recipe("Chicken Pot Pie", "Chicken 1, Peas 5, Carrots 2"));
		recipe.get(1).add(new recipe("Roast Beef", "Beef 1"));
		recipe.get(1).add(new recipe("Broast Reef", "Beef 1"));
		recipe.get(2).add(new recipe("Sushi", "Fish 1"));
		recipe.get(2).add(new recipe("Fried Cod", "Fish 1, Butter 2"));
		recipe.get(3).add(new recipe("Egg Fried Rice", "Rice 1, Egg 2, SoySauce 3"));
		recipe.get(3).add(new recipe("Miso Soup (Vegetable)", "Miso 1, Vegetable 2"));
		recipe.get(4).add(new recipe("Miso Soup", "Miso 1, Vegetable 2"));
		
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Select a Category:");
		lblNewLabel_1.setBounds(20, 7, 117, 27);
		add(lblNewLabel_1);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Chicken", "Beef", "Fish", "Vegetarian", "Vegan"}));
		comboBox.setBounds(141, 10, 197, 21);
		add(comboBox);
		
		model = new DefaultListModel<>();
		recipeList = new JList<String>(model);
		recipeList.setBounds(66, 107, 272, 371);
		add(recipeList);
		recipeList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					recipe tempRecipe = recipe.get(comboBox.getSelectedIndex()).get(recipeList.getSelectedIndex());
					Object[] dialog = {
							"Item Name: " + tempRecipe.name,
							"Category: " + comboBox.getModel().getElementAt(comboBox.getSelectedIndex()),
							"Ingredients: " + tempRecipe.ingredients
					};
					JOptionPane.showConfirmDialog(null, dialog, "Item Details", JOptionPane.PLAIN_MESSAGE);	
				}	
			}
		});
		comboBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	resetComboBox(comboBox,model,recipe);
		    }
		});
		
		JButton editRecipeBtn = new JButton("Edit Recipe");
		editRecipeBtn.setToolTipText("Allows you to change recipe if necessary");
		editRecipeBtn.setBounds(288, 45, 103, 21);
		add(editRecipeBtn);
		JComboBox editCombo = new JComboBox();
		editCombo.setModel(new DefaultComboBoxModel(new String[] {"Chicken", "Beef", "Fish", "Vegetarian", "Vegan"}));
		editRecipeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblKeyboard.setVisible(true);
				JTextField name = new JTextField();
				JTextField newName = new JTextField();
				JComboBox<String> category = editCombo;
				JTextField ingredientList = new JTextField();
				Object[] dialog = {
						"Category:", category,
						"Old Recipe:", name,
						"New Name:", newName,							
						"Ingredients:*", ingredientList,
						"*Note: should be in format",
						"\"itemName itemQuantity, itemName itemQuantity, …\"",
						"see preloaded recipes for example"
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "New Recipe", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					for(int i = 0; i < recipe.get(category.getSelectedIndex()).size();i++) {						
						//Found recipe to change
						if(recipe.get(category.getSelectedIndex()).get(i).name.toString().equals(name.getText())) {
							if(newName.getText() != null && ingredientList.getText() != null) {
								//Make a new recipe object
								recipe.get(category.getSelectedIndex()).set(i, new recipe(newName.getText(), 
										ingredientList.getText()));
							}
							else if(newName.getText() != null) {
								recipe.get(category.getSelectedIndex()).get(i).name = newName.getText();
							}
							else if(ingredientList.getText() != null) {
								recipe.get(category.getSelectedIndex()).set(i, new recipe(name.getText(),
										ingredientList.getText()));
							}
						}
					}
					resetComboBox(comboBox,model,recipe);
				}
				lblKeyboard.setVisible(false);
			}
		});	
			
		
		JComboBox addCombo = new JComboBox();
		addCombo.setModel(new DefaultComboBoxModel(new String[] {"Chicken", "Beef", "Fish", "Vegetarian", "Vegan"}));
		JButton addRecipeBtn = new JButton("Add Recipe");
		addRecipeBtn.setToolTipText("Opens a menu to allow you to add a new recipe.");
		addRecipeBtn.setBounds(20, 45, 103, 21);
		add(addRecipeBtn);
		addRecipeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblKeyboard.setVisible(true);
				JTextField name = new JTextField();
				JComboBox<String> category = addCombo;
				JTextField ingredientList = new JTextField();
				Object[] dialog = {
						"Recipe Name:", name,
						"Category:", category,	
						"Ingredients:*", ingredientList,
						"*Note: should be in format",
						"\"itemName itemQuantity, itemName itemQuantity, …\"",
						"see preloaded recipes for example"
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "New Recipe", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					recipe.get(category.getSelectedIndex()).add(new recipe(name.getText(),ingredientList.getText()));
						resetComboBox(comboBox,model,recipe);
				}
				lblKeyboard.setVisible(false);
			}
		});	
		
		JButton removeRecipeBtn = new JButton("Remove Recipe");
		removeRecipeBtn.setToolTipText("Removes recipe from list");
		removeRecipeBtn.setBounds(135, 45, 143, 21);
		add(removeRecipeBtn);
		removeRecipeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblKeyboard.setVisible(true);
				JTextField name = new JTextField();
				JComboBox<String> category = addCombo;
				Object[] dialog = {
						"Recipe Name:", name,
						"Category:", category													
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "Remove Recipe", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					for(int i = 0; i < recipe.get(category.getSelectedIndex()).size(); i++) {
						if(recipe.get(category.getSelectedIndex()).get(i).name.equals(name.getText())) {
							recipe.get(category.getSelectedIndex()).remove(i);
							resetComboBox(comboBox,model,recipe);
						}						
					}
					
				}
				lblKeyboard.setVisible(false);
			}
		});
		
		JButton btnCheck = new JButton("Check Ingredients");
		btnCheck.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				checker(inv, list, recipe.get(comboBox.getSelectedIndex()).get(recipeList.getSelectedIndex()));
			}
		});
		btnCheck.setBounds(50, 77, 143, 21);
		add(btnCheck);	
		
		JButton btnCook = new JButton("Cook Recipe");
		btnCook.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				recipe rec = recipe.get(comboBox.getSelectedIndex()).get(recipeList.getSelectedIndex());
				if (inv.checkInv(rec.ingredients.split(", ")).isEmpty())  {
					inv.cook(rec.ingredients.split(", "));
					JOptionPane.showConfirmDialog(null, "Enjoy your " + rec.name + "!", "YUM!", JOptionPane.OK_CANCEL_OPTION);
				} else {
					JOptionPane.showConfirmDialog(null, "Missing one or more items", "Notice", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		});
		btnCook.setBounds(216, 77, 143, 21);
		add(btnCook);
	}	
	
	void resetComboBox(JComboBox<String> cb, DefaultListModel<String> model, ArrayList<ArrayList<recipe>> recipe) {
		int prevVal = -2;
		if(cb.getSelectedIndex() != -1) {
			if(prevVal != cb.getSelectedIndex()) {
				//Clear list for previous category
				model.clear();
				prevVal = cb.getSelectedIndex();
				//Insert Recipes
				for(int i = 0; i < recipe.get(cb.getSelectedIndex()).size(); i++) {
					model.addElement(recipe.get(cb.getSelectedIndex()).get(i).name);
					//model.append("\n");
				}
			}			
		}
	}
	
	protected void setCombo() {
		comboBox.setSelectedIndex(0);
		resetComboBox(comboBox, model, recipe);
	}
	private void checker (Inventory inv, ShoppingList list, recipe rec) {
		lblKeyboard.setVisible(true);
		list.addMissing(inv.checkInv(rec.ingredients.split(", ")), rec.name);
		lblKeyboard.setVisible(false);
	}
}