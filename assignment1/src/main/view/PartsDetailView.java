package main.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.model.Item;

public class PartsDetailView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881822137172571826L;

	/** Alphanumeric and symbols. Max length is 20. 
	 */
	private JTextField partNumber;
	
	/** Alphanumeric and symbols. Max length is 255. 
	 */
	private JTextField partName;
	
	/** Alphanumeric and symbols. Max length is 255. 
	 */
	private JTextField vendor;
	
	/** Unsigned integer, initial entry must be greater than 0.
	 * */
	private JTextField partQuantity;
	
	private JLabel partNumberLabel = new JLabel("Part Number: ");
	
	private JLabel partNameLabel = new JLabel("Part Name: ");
	
	private JLabel vendorLabel = new JLabel("Vendor: ");
	
	private JLabel partQuantityLabel = new JLabel("Quantity: ");
	
	private Item item;
	
	public PartsDetailView() {
		
		JPanel inputs = new JPanel(new BorderLayout(5, 5));
		
		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		this.partNumber = new JTextField(10);
		
		this.partName = new JTextField(10);
		
		this.vendor = new JTextField(10);
		
		this.partQuantity = new JTextField(10);
		
		inputs.add(labelsPanel, BorderLayout.WEST);
		inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		labelsPanel.add(this.partNumberLabel);
		fieldsPanel.add(this.partNumber);
		
		labelsPanel.add(this.partNameLabel);
		fieldsPanel.add(this.partName);
		
		labelsPanel.add(this.vendorLabel);
		fieldsPanel.add(this.vendor);
		
		labelsPanel.add(this.partQuantityLabel);
		fieldsPanel.add(this.partQuantity);
		
		JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
		
		controls.add(new JButton("Save Part"));
	
		JPanel gui = new JPanel(new BorderLayout(10, 10));
		gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gui.add(inputs, BorderLayout.CENTER);
		gui.add(controls, BorderLayout.SOUTH);
		
		add(gui);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Part Detail");
		setLocationRelativeTo(null);
	}
	
	public void showPartsDetailView(){
		this.setVisible(true);
	}
	
	public void setItem(Item item){
		this.item = item;
		this.refreshItemDisplayed();
	}
	
	public void refreshItemDisplayed(){
		this.partNumber.setText(this.item.getPartNumber());
		this.partName.setText(this.item.getPartName());
		this.vendor.setText(this.item.getVendor());
		this.partQuantity.setText(Integer.toString(this.item.getQuantity()));
	}

}
