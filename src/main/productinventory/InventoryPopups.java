package main.productinventory;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * InventoryPopups produces the dialog windows that takes in and 
 * confirms user input when adding, editing, or removing a product.
 * 
 * @author Cynthia
 *
 */
public class InventoryPopups {
	
	private static JPanel 		inputPanel,
								containerPanel;
	private static JTextField 	name,
								quantity,
								price,
								category;
	private static JLabel		nameLabel,
								quantityLabel,
								priceLabel,
								categoryLabel,
								messageLabel;
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	
	/**
	 * Initializes the panel that will allow users to send
	 * multiple inputs through the JTextFields.
	 */
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
		
	/**
	 * Initializes the panel that allows users to select a product by 
	 * entering a name.
	 */
	private static void initSelectProductPanel() {
		messageLabel = new JLabel("Select the product by name:");		
		name = new JTextField();
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(messageLabel);
		containerPanel.add(Box.createVerticalStrut(5));
		containerPanel.add(name);
	}//end init select product panel
		
	public static String getName() {
		return name.getText();
	}
	
	public static String getQuantity() {
		return quantity.getText();
	}
	
	public static int getQuantityInt() {
		return Integer.valueOf(getQuantity());
	}
	
	public static String getPrice() {
		return price.getText();
	}
	
	public static double getPriceDouble() {
		return Double.valueOf(getPrice());
	}
	
	public static String getCategory() {
		return category.getText();
	}
	
	/**
	 * Used to populate the product fields when querying it from the database.
	 * @param n The name of the product.
	 * @param q The quantity of the product.
	 * @param p The price of the product.
	 * @param c The category of the product.
	 */
	private static void setInputFields(String n, String q, String p, String c) {
		name.setText(n);
		quantity.setText(q);
		price.setText(p);
		category.setText(c);
	}//end set input fields
	
	/**
	 * Creates the error dialog if something goes wrong.
	 */
	public static void showErrorPopup(String message) {
		JOptionPane.showMessageDialog(
				null, 
				"Unable to make changes. " + message, 
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}//end show error dialog
	
	/**
	 * Creates the confirmation dialog when the user wants to delete a product.
	 * @param name The name of the selected product to delete.
	 * @return An integer representing the user's choice. 
	 */
	public static int showConfirmationPopup(String name) {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to delete \'" + name + "\'?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);
		return result;
	}//end show confirmation dialog
	
	/**
	 * Creates a dialog that lets users make multiple inputs to create 
	 * or edit a product.
	 * @param mode A selector to determine if the user is adding or editing a product.
	 * @param product The product to be added or edited.
	 * @return An integer representing the user's choice.
	 */
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
	
	/**
	 * Creates a dialog that allows users to select a product by name.
	 * @return An integer representing the user's choice.
	 */
	public static int showSelectPopup() {
		initSelectProductPanel();
		int result = JOptionPane.showConfirmDialog(
				null,
				containerPanel,
				"Select Product",
				JOptionPane.OK_CANCEL_OPTION);
		return result;
	}//end show select popup
}//end class
