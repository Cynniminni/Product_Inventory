package main.productinventory;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InventoryPopups {
	
	public static JPanel 		inputPanel,
								containerPanel;
	public static JTextField 	name,
								quantity,
								price,
								category;
	public static JLabel		nameLabel,
								quantityLabel,
								priceLabel,
								categoryLabel,
								messageLabel;
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	
	//initialize panel to hold multiple input fields
	private static void initInputPanel() {		
		name = new JTextField();
		quantity = new JTextField();
		price = new JTextField();
		category = new JTextField();
				
		nameLabel = new JLabel("Name");
		quantityLabel = new JLabel("Quantity");
		priceLabel = new JLabel("Price");
		categoryLabel = new JLabel("Category");
		messageLabel = new JLabel("Please enter product information:");
				
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 4));
		inputPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		inputPanel.add(nameLabel);
		inputPanel.add(quantityLabel);
		inputPanel.add(priceLabel);
		inputPanel.add(categoryLabel);		
		inputPanel.add(name);
		inputPanel.add(quantity);
		inputPanel.add(price);
		inputPanel.add(category);
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		containerPanel.add(messageLabel);
		containerPanel.add(Box.createVerticalStrut(5));
		containerPanel.add(inputPanel);
	}//end init multi input panel
	
	private static void initSelectProductPanel() {
		messageLabel = new JLabel("Select the product by name:");		
		name = new JTextField();
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(messageLabel);
		containerPanel.add(Box.createVerticalStrut(5));
		containerPanel.add(name);
	}//end init select product panel
	
	public static void setInputFields(String n, String q, String p, String c) {
		name.setText(n);
		quantity.setText(q);
		price.setText(p);
		category.setText(c);
	}//end set input fields
	
	public static void showErrorPopup() {
		JOptionPane.showMessageDialog(
				null, 
				"Unable to make changes.", 
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}//end show error dialog
	
	public static int showConfirmationPopup(String name) {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to delete \'" + name + "\'?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);
		return result;
	}//end show confirmation dialog
	
	public static int showInputPopup(String mode, Product product) {
		String title = "";
		initInputPanel();
		
		if (mode.equalsIgnoreCase(ADD)) {
			title = "Add Product";
			setInputFields("","","","");//init fields to blank
		} else if (mode.equalsIgnoreCase(EDIT)){
			title = "Edit Product";
			setInputFields(
					product.getName(),
					Integer.toString(product.getQuantity()),					
					Double.toString(product.getPrice()),
					product.getCategory());
		}
				
		int result = JOptionPane.showConfirmDialog(
				null,
				containerPanel,//panel to display
				title,//title
				JOptionPane.OK_CANCEL_OPTION);//user options
		return result;
	}//end show add popup dialog
	
	public static int showSelectPopup() {
		initSelectProductPanel();
		int result = JOptionPane.showConfirmDialog(
				null,
				containerPanel,
				"Select Product",
				JOptionPane.OK_CANCEL_OPTION);
		return result;
	}
}//end class
