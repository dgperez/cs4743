package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.PartsDetailController;
import main.model.Part;
import main.model.UnitsOfQuantity;

public class PartsDetailView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881822137172571826L;

	private JTextField id;
	
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

	/** Alphanumeric and symbols. Max length is 50. 
	 */
	private JTextField externalPartNumber;
	
	private JComboBox<String> unitOfQuantity;

	private JLabel idLabel = new JLabel("Id: ");
	
	private JLabel partNumberLabel = new JLabel("Part Number: ");
	
	private JLabel partNameLabel = new JLabel("Part Name: ");
	
	private JLabel vendorLabel = new JLabel("Vendor: ");
	
	private JLabel partUnitOfQuantity = new JLabel("Unit Of Quantity");

	private JLabel externalPartNumberLabel = new JLabel("External Part Number: ");
	
	private Part part;
	
	private JPanel controls;
	
	private JPanel inputs;
	
	private JButton savePart;
	
	private UnitsOfQuantity unitsOfQuantityTypes;
	
	public PartsDetailView() {
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		this.id = new JTextField(10);
		id.setEditable(false);
		
		this.partNumber = new JTextField(10);
		
		this.partName = new JTextField(10);
		
		this.vendor = new JTextField(10);
		
		this.partQuantity = new JTextField(10);
		
		this.externalPartNumber = new JTextField(10);
		
		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		this.unitOfQuantity = 
				new JComboBox<String>(
						unitsOfQuantityTypes.getUnitsOfQuantity());

		labelsPanel.add(this.idLabel);
		fieldsPanel.add(this.id);
		
		labelsPanel.add(this.partNumberLabel);
		fieldsPanel.add(this.partNumber);
		
		labelsPanel.add(this.partNameLabel);
		fieldsPanel.add(this.partName);
		
		labelsPanel.add(this.vendorLabel);
		fieldsPanel.add(this.vendor);
		
		labelsPanel.add(this.partUnitOfQuantity);
		fieldsPanel.add(this.unitOfQuantity);

		labelsPanel.add(this.externalPartNumberLabel);
		fieldsPanel.add(this.externalPartNumber);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
		
		this.savePart = new JButton("Save Part");
		this.savePart.setActionCommand("savePart");
		
		this.controls.add(this.savePart);
	
		JPanel gui = new JPanel(new BorderLayout(10, 10));
		gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gui.add(this.inputs, BorderLayout.CENTER);
		gui.add(this.controls, BorderLayout.SOUTH);
		
		add(gui);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Part Detail");
		setLocationRelativeTo(null);
	}
	
	public void showPartsDetailView(){
		this.setVisible(true);
	}
	
	public void setItem(Part part){
		this.part = part;
		this.refreshObserver();
	}
	
	/*
	public Part getPart(){
		return new Part()
		return new Item(this.getPartNumber(),
				this.getPartName(),
				this.getVendor(),
				this.getQuantity(),
				this.getUnitOfQuantity(),
				this.getPartLocation(),
				this.getExternalPartNumber());
	}
	*/
	
	public void refreshObserver(){
		this.id.setText(Integer.toString(this.part.getId()));
		this.partNumber.setText(this.part.getPartNumber());
		this.partName.setText(this.part.getPartName());
		this.vendor.setText(this.part.getVendor().getValue());
		this.unitOfQuantity.setSelectedItem(this.part.getUnitOfQuantity());
		this.externalPartNumber.setText(this.part.getExternalPartNumber());
	}
	
	public void registerListener(PartsDetailController listener){
		
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(listener);
			}
		}
	}

	public String getPartNumber(){
		return this.partNumber.getText();
	}
	
	public String getPartName(){
		return this.partName.getText();
	}
	
	public String getVendor(){
		return this.vendor.getText();
	}
	
	public int getQuantity(){
		String temp = this.partQuantity.getText();
		return (temp != null && temp.matches("^[0-9]+$")) 
				? Integer.parseInt(temp) : -1;
	}
	
	public String getUnitOfQuantity(){
		return (String)this.unitOfQuantity.getSelectedItem();
	}
	
	public String getExternalPartNumber(){
		return this.externalPartNumber.getText();
	}
	
	public boolean containsItem(Part part){
		return (this.part == part);
	}
}