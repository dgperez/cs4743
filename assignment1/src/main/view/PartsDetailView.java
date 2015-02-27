package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map.Entry;

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

public class PartsDetailView extends JFrame implements IObserver {

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
	
	private boolean newPart = false;
	
	public PartsDetailView(UnitsOfQuantity unitsOfQuantityTypes, boolean newPart) {
		this.unitsOfQuantityTypes = unitsOfQuantityTypes;
		this.newPart = newPart;
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		this.id = new JTextField(10);
		id.setEditable(false);
		
		this.partNumber = new JTextField(10);
		
		this.partName = new JTextField(10);
		
		this.vendor = new JTextField(10);
		
		this.externalPartNumber = new JTextField(10);
		
		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		this.unitOfQuantity = 
				new JComboBox<String>(
						this.unitsOfQuantityTypes.getUnitsOfQuantity());
		if(!this.newPart){
			labelsPanel.add(this.idLabel);
			fieldsPanel.add(this.id);
		}
		
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
	
	public void setPart(Part part){
		this.part = part;
		this.refreshObserver();
	}
	
	public Part getPart(){
		return new Part((!this.newPart) ? this.getId() : -1, 
				this.getPartNumber(), 
				this.getPartName(), 
				this.getVendor(), 
				this.getUnitOfQuantity(), 
				this.getExternalPartNumber());
	}
	
	public void refreshObserver(){
		if(!this.newPart){
			this.id.setText(Integer.toString(this.part.getId()));
			this.partNumber.setText(this.part.getPartNumber());
			this.partName.setText(this.part.getPartName());
			this.vendor.setText(this.part.getVendor());
			this.unitOfQuantity.setSelectedItem(this.part.getUnitOfQuantity());
			this.externalPartNumber.setText(this.part.getExternalPartNumber());
		} else {
			this.unitOfQuantity.setSelectedItem(1);
		}
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
	
	public int getId(){
		return Integer.parseInt(this.id.getText());
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
	
	public Entry<Integer, String> getUnitOfQuantity(){
		String quantity = (String)this.unitOfQuantity.getSelectedItem();
		return this.unitsOfQuantityTypes.getEntryForQuantity(quantity);
	}
	
	public String getExternalPartNumber(){
		return this.externalPartNumber.getText();
	}
	
	public boolean containsItem(Part part){
		return (this.part == part);
	}
}