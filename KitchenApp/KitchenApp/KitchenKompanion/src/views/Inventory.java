package views;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import views.RecipeView.recipe;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

public class Inventory extends JPanel {
	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	ArrayList<FoodItem> removed;
	private JLabel lblKeyboard;
	private JTextField textCurItem;
	private ArrayList<FoodItem> missing;
	/**
	 * Create the panel.
	 */
	public Inventory() {
		setLayout(null);
		removed = new ArrayList<FoodItem>();
		missing = new ArrayList<FoodItem>();
		
		lblKeyboard = new JLabel("");
		lblKeyboard.setBounds(65, 302, 273, 183);
		lblKeyboard.setIcon(new ImageIcon(this.getClass().getResource("/phone.png")));
		add(lblKeyboard);
		lblKeyboard.setVisible(false);
		
		JLabel lblCurItem = new JLabel("Current Item:");
		lblCurItem.setBounds(10, 11, 90, 29);
		add(lblCurItem);
		
		JTextField txtCurItem = new JTextField("");
		txtCurItem.setBounds(93, 11, 232, 29);
		add(txtCurItem);		
		
		tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TreeSelectionModel model = tree.getSelectionModel();
				if(model.getSelectionCount()>0) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
					txtCurItem.setText(selectedNode.getUserObject().toString());
				}
			}
		});
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Location") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					DefaultMutableTreeNode node_3;
					node_1 = new DefaultMutableTreeNode("Refrigerator");
						node_2 = new DefaultMutableTreeNode("Meat");
							node_3 = new DefaultMutableTreeNode("Chicken");
							node_3.add(new DefaultMutableTreeNode("2"));
							node_2.add(node_3);
							node_3 = new DefaultMutableTreeNode("Beef");
							node_3.add(new DefaultMutableTreeNode("3"));
							node_2.add(node_3);
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Dairy");
							node_3 = new DefaultMutableTreeNode("Cheese");
							node_3.add(new DefaultMutableTreeNode("11"));
							node_2.add(node_3);
							node_3 = new DefaultMutableTreeNode("Milk");
							node_3.add(new DefaultMutableTreeNode("1"));
							node_2.add(node_3);
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Freezer");
						node_2 = new DefaultMutableTreeNode("Ice Cream");
						node_2.add(new DefaultMutableTreeNode("6"));
						node_1.add(node_2);	
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Pantry");
						node_2 = new DefaultMutableTreeNode("Bread");
						node_2.add(new DefaultMutableTreeNode("12"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Spice Rack");
						node_2 = new DefaultMutableTreeNode("Basil");
							node_2.add(new DefaultMutableTreeNode("1"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Cayenne Pepper");
							node_2.add(new DefaultMutableTreeNode("1"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Oregano");
						node_2.add(new DefaultMutableTreeNode("1"));
						node_1.add(node_2);
					add(node_1);
				}
			}
		));
		tree.setBounds(10, 121, 384, 362);
		add(tree);

		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		rootNode = (DefaultMutableTreeNode) model.getRoot();
		
		JButton AddItem = new JButton("Add Food\n");
		AddItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
					editTree(new FoodItem(name.getText(), category.getText(), Integer.parseInt(quantity.getText())));
				}
				lblKeyboard.setVisible(false);
			}
		});
		AddItem.setBounds(141, 47, 101, 29);
		add(AddItem);
		
		JButton AddCategory = new JButton("Add Category\n");
		AddCategory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblKeyboard.setVisible(true);
				JTextField name = new JTextField();
				JTextField category = new JTextField();
				Object[] dialog = {
						"New Category:", name,
						"Add Cateogry To:", category,
				};
				int option = JOptionPane.showConfirmDialog(null, dialog, "New Item", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					DefaultMutableTreeNode selectedNode = findCategory(rootNode, category.getText());
					if (selectedNode != null)
						addItem(selectedNode, name.getText(), model);
					else
						addItem(addItem(rootNode, category.getText(), model), name.getText(), model);
				}
				lblKeyboard.setVisible(false);
			}
		});
		AddCategory.setBounds(131, 83, 121, 29);
		add(AddCategory);
		
		JButton EditItem = new JButton("Edit Food\n");
		EditItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
				if (selectedNode.getChildCount() == 1 && selectedNode.getChildAt(0).isLeaf()) {
					lblKeyboard.setVisible(true);
					JTextField name = new JTextField();
					JTextField quantity = new JTextField();
					name.setText(selectedNode.toString());
					Object[] dialog = {
							"Item Name:", name,
							"Quantity: (numerical)", quantity
					};
					int option = JOptionPane.showConfirmDialog(null, dialog, "New Item", JOptionPane.OK_CANCEL_OPTION);
					if(option == JOptionPane.OK_OPTION) {
						selectedNode.setUserObject(name.getText());
						((DefaultMutableTreeNode)selectedNode.getChildAt(0)).setUserObject(quantity.getText());
						model.reload(selectedNode);
					}
					lblKeyboard.setVisible(false);
				} else {
					JOptionPane.showConfirmDialog(null, "Please select valid item", "Notice", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		});
		EditItem.setBounds(20, 47, 101, 29);
		add(EditItem);
		
		JButton Remove = new JButton("Remove\n");
		Remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
				if (!selectedNode.isLeaf())
					remove(selectedNode);
				else
					autoRemove(selectedNode);
			}
		});
		Remove.setBounds(268, 47, 101, 29);
		add(Remove);
	}
	
	private void editTree(FoodItem a){
		DefaultMutableTreeNode category = findCategory(rootNode, a.category);
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		if (category != null) {
			boolean exist = false;
			for(int i = 0 ; i < category.getChildCount() ; i++){
		        if (category.getChildAt(i).toString().equals(a.name)){
		        	exist = true;
		        	editQuantity(category.getChildAt(i), a.quantity, model);
		        }
		    } if (!exist) {addQuantity(addItem(category, a.name, model), a.quantity, model);  }
		    model.reload(category);
		} else {
			addQuantity(addItem(addItem(rootNode, a.category, model), a.name, model), a.quantity, model);
		}
	}
	
	private void addQuantity(DefaultMutableTreeNode item, int quantity, DefaultTreeModel model) {
		item.add(new DefaultMutableTreeNode(quantity));
		model.reload(item.getParent());
	}

	private DefaultMutableTreeNode addItem(DefaultMutableTreeNode item, String text, DefaultTreeModel model) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(text);
		model.insertNodeInto(newNode, item, item.getChildCount());
		model.reload(item);
		return (DefaultMutableTreeNode) item.getLastChild();
	}
	
	private void editQuantity(TreeNode item, int quantity, DefaultTreeModel model) {
		((DefaultMutableTreeNode)item.getChildAt(0)).setUserObject(quantity + Integer.parseInt(item.getChildAt(0).toString()));
		model.reload(item.getParent());
	}

	private DefaultMutableTreeNode findCategory(DefaultMutableTreeNode n, String s){
		Enumeration en = n.preorderEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode nodeTemp = (DefaultMutableTreeNode)en.nextElement();
			if (nodeTemp.toString().equals(s)) {
				return nodeTemp;
			}
		}
		return null;
	}
	protected void shoppingList(ArrayList<FoodItem> a) {
		a.forEach((e) -> editTree(e));
	}
	
	private void remove(DefaultMutableTreeNode n) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)n.getParent();
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		model.removeNodeFromParent(n);
		model.reload(parent);
	}
	private void autoRemove(DefaultMutableTreeNode n) {
		removed.add(new FoodItem(((DefaultMutableTreeNode)n.getParent()).toString(), ((DefaultMutableTreeNode)n.getParent().getParent()).toString(), 0));
		remove((DefaultMutableTreeNode)n.getParent());
	}
	protected ArrayList<FoodItem> getRemoved() {
		return removed;
	}
	protected void clearRemoved() {
		removed.clear();
	}
	
	protected  ArrayList<FoodItem> checkInv(String[] ing) {
		DefaultMutableTreeNode node;
		for (int i = 0; i < ing.length; i++) {
			node = findCategory(rootNode, ing[i].substring(0, ing[i].lastIndexOf(' ')));
			if (node != null) {
				if ((Integer.parseInt(node.getChildAt(0).toString()) - Integer.parseInt(ing[i].substring(ing[i].lastIndexOf(' ') + 1))) < 0) {
					missing.add(new FoodItem(ing[i].substring(0, ing[i].lastIndexOf(' ')), "", Integer.parseInt(ing[i].substring(ing[i].lastIndexOf(' ') + 1))));
				}
			} else {
				missing.add(new FoodItem(ing[i].substring(0, ing[i].lastIndexOf(' ')), "", Integer.parseInt(ing[i].substring(ing[i].lastIndexOf(' ') + 1))));
			}
		} return missing;
	}

	protected void cook(String[] ing) {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode node;
		for (int i = 0; i < ing.length; i++) {
			 node = findCategory(rootNode, ing[i].substring(0, ing[i].lastIndexOf(' ')));
			((DefaultMutableTreeNode)node.getChildAt(0)).setUserObject(Integer.parseInt(node.getChildAt(0).toString()) - Integer.parseInt(ing[i].substring(ing[i].lastIndexOf(' ') + 1)));
			if (Integer.parseInt(((DefaultMutableTreeNode)node.getChildAt(0)).getUserObject().toString()) == 0) {
				autoRemove((DefaultMutableTreeNode) node.getChildAt(0));
			}
			model.reload(node.getParent());
			 
		}
	}
}
