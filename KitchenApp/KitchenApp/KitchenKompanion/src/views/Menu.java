package views;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Menu extends JPanel {
	public Menu(){
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Kitchen");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTitle.setBounds(69, 148, 146, 61);
		add(lblTitle);
		
		JLabel lblTItle2 = new JLabel("Kompanion");
		lblTItle2.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTItle2.setBounds(41, 212, 223, 61);
		add(lblTItle2);
		
		JLabel lblRefrigerator = new JLabel("");
		lblRefrigerator.setBounds(289, 85, 97, 141);
		lblRefrigerator.setIcon(new ImageIcon(this.getClass().getResource("/refrigerator.png")));
		add(lblRefrigerator);
		
		JLabel lblRecipe = new JLabel("");
		lblRecipe.setBounds(116, 296, 168, 174);
		lblRecipe.setIcon(new ImageIcon(this.getClass().getResource("/recipe.png")));
		add(lblRecipe);
		
		JLabel lblCart = new JLabel("");
		lblCart.setBounds(69, 47, 87, 80);
		lblCart.setIcon(new ImageIcon(this.getClass().getResource("/cart.png")));
		add(lblCart);
		
	}
}
