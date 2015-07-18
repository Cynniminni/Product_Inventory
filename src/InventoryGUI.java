import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InventoryGUI {

	public JFrame frame;
	
	public JPanel containerPanel;
	public JPanel invenPanel;
	public JPanel actionPanel;
	public JPanel searchPanel;
	public JPanel managePanel;
	
	public JTextArea invenTable;
	public JTextField searchBar;
	
	public JLabel searchHeader;
	public JLabel manageHeader;
	
	public Button searchProduct;
	public Button refreshInven;
	public Button addProduct;
	public Button editProduct;
	public Button removeProduct;
	
	public String title = "Product Inventory";
		
	public InventoryGUI() {
		frame = new JFrame();
		frame.setSize(700, 500);
		frame.setTitle(title);
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
				
		invenTable = new JTextArea();
		invenTable.setSize(470, 500);
		invenTable.setEditable(false);
		invenTable.setEnabled(true);
		invenTable.setLineWrap(true);
		invenTable.setWrapStyleWord(true);
		invenTable.setFont(new Font("Monospaced", Font.PLAIN, 12)); //set font for proper alignment
				
		searchHeader = new JLabel("Search");		
		searchHeader.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		manageHeader = new JLabel("Manage Products");
		manageHeader.setFont(new Font("Monospaced", Font.PLAIN, 12));		
		
		searchBar = new JTextField();
		
		searchProduct = new Button("Go");
		refreshInven = new Button("Refresh");
		addProduct = new Button("Add");
		editProduct = new Button("Edit");
		removeProduct = new Button("Remove");
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		searchPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		searchPanel.add(searchBar);
		searchPanel.add(searchProduct);
		searchPanel.add(refreshInven);				
		
		managePanel = new JPanel();
		managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.X_AXIS));
		managePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		managePanel.add(addProduct);
		managePanel.add(editProduct);
		managePanel.add(removeProduct);
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);		
		actionPanel.add(searchHeader);
		actionPanel.add(Box.createRigidArea(new Dimension(0,5)));
		actionPanel.add(searchPanel);
		actionPanel.add(manageHeader);
		actionPanel.add(Box.createRigidArea(new Dimension(0,5)));
		actionPanel.add(managePanel);
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

	public void updateText(String text) {
		invenTable.setText(text);
	}
}//end class