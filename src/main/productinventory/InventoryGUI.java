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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * InventoryGUI creates and assembles the user interface and attaches
 * the necessary action listeners to the buttons.
 * 
 * @author Cynthia
 *
 */

public class InventoryGUI {

	private JFrame frame;
	
	private JPanel containerPanel;
	private JPanel invenPanel;
	private JPanel actionPanel;
	private JPanel searchPanel;
	private JPanel managePanel;
	
	private static JTextArea invenTable;
	
	private static JScrollPane scrollPane;
	
	private JTextField searchBar;
	
	private JLabel searchHeader;
	private JLabel manageHeader;
	
	private JButton searchProduct;
	private JButton refreshInven;
	private JButton addProduct;
	private JButton editProduct;
	private JButton removeProduct;
	
	private String title = "Product Inventory";
	
	private static JTable table;	
		
	/**
	 * Initializes and assembles the GUI, and attaches the appropriate listeners to
	 * each component.
	 */
	public InventoryGUI() {
		frame = new JFrame();
		frame.setSize(750, 300);
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
					
		table = new JTable(JDBCDriver.getDataModel());
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.addPropertyChangeListener(new TableCellListener(table, InventoryListeners.editRowAction()));
		scrollPane = new JScrollPane(table);
		
//		invenTable = new JTextArea();
//		invenTable.setSize(470, 300);
//		invenTable.setEditable(false);
//		invenTable.setEnabled(true);
//		invenTable.setLineWrap(true);
//		invenTable.setWrapStyleWord(true);
//		invenTable.setFont(new Font("Monospaced", Font.PLAIN, 12));//set font for proper alignment
				
//		scrollPane = new JScrollPane(invenTable);
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		
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
//		managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.X_AXIS));
		managePanel.setLayout(new GridLayout(1,3));
		managePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		managePanel.add(addProduct);
		managePanel.add(editProduct);
		managePanel.add(removeProduct);
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);	
		actionPanel.add(Box.createVerticalStrut(10));
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
		invenPanel.add(scrollPane);
		
		containerPanel = new JPanel();//has FlowLayout by default
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
		containerPanel.add(invenPanel);		
		containerPanel.add(Box.createHorizontalStrut(10));
		containerPanel.add(actionPanel);
		containerPanel.add(Box.createHorizontalStrut(10));
		
		frame.add(containerPanel);
		frame.setVisible(true);
	}	
	
	/**
	 * Updates the text area with the current listing of products
	 * from the database.
	 */
	public static void updateInventory() {
//		invenTable.setText(JDBCDriver.selectTable());
		table.setModel(JDBCDriver.getDataModel());
		table.updateUI();		
	}
	
	/**
	 * Updates the text area with any search results (given by
	 * InventoryListeners).
	 * @param result The search result from InventoryListeners.
	 */
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