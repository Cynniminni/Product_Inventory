package main.productinventory;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.productinventory.JDBCDriver;

public class InventoryGUI {

	private JFrame frame;
	
	private JPanel containerPanel;
	private JPanel invenPanel;
	private JPanel actionPanel;
	private JPanel searchPanel;
	private JPanel managePanel;
	
	private static JTextArea invenTable;
	private JTextArea info;
	
	private JTextField searchBar;
	
	private JLabel searchHeader;
	private JLabel manageHeader;
	private JLabel infoHeader;
	
	private JButton searchProduct;
	private JButton refreshInven;
	private JButton addProduct;
	private JButton editProduct;
	private JButton removeProduct;
	
	private String title = "Product Inventory";
	private String text = "Welcome to the product inventory program. " +
			"To the left is a display of the products currently in the database. " +
			"All searching and management of products are done by referencing the " +
			"product name, which is case-insensitive. " +
			"Begin by using any of the buttons above.";
		
	public InventoryGUI() {
		frame = new JFrame();
		frame.setSize(700, 500);
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
				
		invenTable = new JTextArea();
		invenTable.setSize(470, 500);
		invenTable.setEditable(false);
		invenTable.setEnabled(true);
		invenTable.setLineWrap(true);
		invenTable.setWrapStyleWord(true);
		invenTable.setFont(new Font("Monospaced", Font.PLAIN, 12));//set font for proper alignment
				
		searchHeader = new JLabel("Search");		
		searchHeader.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		manageHeader = new JLabel("Manage Products");
		manageHeader.setFont(new Font("Monospaced", Font.PLAIN, 12));		
		
		searchBar = new JTextField();
		
		searchProduct = new JButton("Go");
		refreshInven = new JButton("Refresh");
		addProduct = new JButton("Add");		
		editProduct = new JButton("Edit");
		removeProduct = new JButton("Remove");
		
		searchProduct.addActionListener(new InventoryListeners(searchBar).searchProductListener());
		refreshInven.addActionListener(InventoryListeners.refreshProductListener());
		addProduct.addActionListener(InventoryListeners.addProductListener());
		editProduct.addActionListener(InventoryListeners.editProductListener());
		removeProduct.addActionListener(InventoryListeners.removeProductListener());
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		searchPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		searchPanel.add(searchBar);
		searchPanel.add(searchProduct);
		searchPanel.add(refreshInven);				
		
		managePanel = new JPanel();
		//managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.X_AXIS));
		managePanel.setLayout(new GridLayout(1,3));
		managePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		managePanel.add(addProduct);
		managePanel.add(editProduct);
		managePanel.add(removeProduct);
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);	
		actionPanel.add(searchHeader);
		actionPanel.add(Box.createVerticalStrut(5));
		actionPanel.add(searchPanel);
		actionPanel.add(Box.createVerticalStrut(10));
		actionPanel.add(manageHeader);
		actionPanel.add(Box.createVerticalStrut(5));
		actionPanel.add(managePanel);
		actionPanel.add(Box.createVerticalStrut(10));
		
		actionPanel.add(Box.createRigidArea(new Dimension(0, 400)));
		
		invenPanel = new JPanel();
		invenPanel.setLayout(new GridLayout(1,1));
		invenPanel.add(invenTable);
		
		containerPanel = new JPanel();//has FlowLayout by default
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
		containerPanel.add(invenPanel);		
		containerPanel.add(Box.createRigidArea(new Dimension(10,0)));
		containerPanel.add(actionPanel);
		
		frame.add(containerPanel);
	}

	public JTextArea getInvenTable() {
		return invenTable;
	}
	
	public static void updateInventory() {
		invenTable.setText(JDBCDriver.selectTable());
	}
	
	public static void updateInventory(String result) {		
		String header = JDBCDriver.divider + JDBCDriver.divider + "\n";
		String titles = String.format(JDBCDriver.format, 
				"Product",
				"Quantity",
				"Price",
				"Category");
		
		invenTable.setText(header);
		invenTable.append(titles);
		invenTable.append(header);
		invenTable.append(result);
	}
}//end class