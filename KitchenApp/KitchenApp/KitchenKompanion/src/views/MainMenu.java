package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 430, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		Menu menu = new Menu();
		tabbedPane.addTab("Main Menu", null, menu, null);
		menu.setBackground(Color.CYAN);
		
		Inventory inv = new Inventory();
		tabbedPane.addTab("Inventory", null, inv, null);
		inv.setBackground(Color.CYAN);
		
		ShoppingList list = new ShoppingList();
		tabbedPane.addTab("ShoppingList", null, list, null);
		list.setBackground(Color.CYAN);
		
		RecipeView recipe = new RecipeView(inv, list);
		tabbedPane.addTab("Recipes", null, recipe, null);
		recipe.setBackground(Color.CYAN);
		recipe.setCombo();
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				inv.shoppingList(list.getPurchases());
				list.clearPurchases();
				list.addRemoved(inv.getRemoved());
				inv.clearRemoved();
			}
		});
	}

}
