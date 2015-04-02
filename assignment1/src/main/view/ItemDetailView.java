package main.view;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map.Entry;

import main.controller.ItemDetailController;
import main.model.Item;
import main.model.Locations;
import main.model.Part;
import main.model.PartsInventory;
import main.model.ProductTemplate;
import main.model.ProductTemplates;

public class ItemDetailView extends JFrame {

	private static final long serialVersionUID = -297591929291152377L;

	private JTextField id;

	private JComboBox<Object> partsOrProductTemplates;

	private PartsInventory partInventory;
	
	private ProductTemplates productTemplates;

	private JTextField quantity;

	private JComboBox<String> location;

	private Locations locations;

	private JLabel idLabel = new JLabel("Id: ");

	private JLabel quantityLabel = new JLabel("Quantity: ");

	private JLabel partOrProductLabel;

	private JLabel locationLabel = new JLabel("Location: ");

	private JPanel controls;

	private JPanel inputs;

	private JButton saveItem;

	private Item item;
	
	private boolean isNewItem;
	
	private boolean isProduct;

	public ItemDetailView(Locations locations, PartsInventory partsInventory, 
			boolean isNewItem, boolean isProduct, 
			ProductTemplates productTemplates) {
		this.locations = locations;
		this.inputs = new JPanel(new BorderLayout(5,5));
		this.isNewItem = isNewItem;
		this.isProduct = isProduct;
		this.productTemplates = productTemplates;

		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		this.partInventory = partsInventory;
		
		this.id = new JTextField(10);
		if(!this.isProduct){
			this.partOrProductLabel = new JLabel("Part: ");
			this.partsOrProductTemplates = new JComboBox<Object>(
					this.partInventory.getAllParts().toArray());
		} else {
			this.partOrProductLabel = new JLabel("Product: ");
			this.partsOrProductTemplates = new JComboBox<Object>(
					this.productTemplates.getProductTemplates().toArray());
		}
		
		this.quantity = new JTextField(10);
		this.location = new JComboBox<String>(this.locations.getLocations());

		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.EAST);

		if(this.isNewItem){
			this.idLabel.setVisible(false);
			this.id.setVisible(false);
		}
		
		labelsPanel.add(this.idLabel);
		fieldsPanel.add(this.id);
		this.id.setEditable(false);

		labelsPanel.add(this.partOrProductLabel);
		fieldsPanel.add(this.partsOrProductTemplates);

		labelsPanel.add(this.quantityLabel);
		fieldsPanel.add(this.quantity);

		labelsPanel.add(this.locationLabel);
		fieldsPanel.add(this.location);

		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));

		this.saveItem = new JButton("Save Item");
		this.saveItem.setActionCommand("saveItem");

		this.controls.add(this.saveItem);

		JPanel gui = new JPanel(new BorderLayout(10,10));
		gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gui.add(this.inputs, BorderLayout.CENTER);
		gui.add(this.controls, BorderLayout.SOUTH);

		add(gui);

		pack();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Item Detail");
		setLocationRelativeTo(null);
	}

	public void showItemDetailView(){
		this.setVisible(true);
	}

	public boolean containsItem(Item item){
		return this.item.getId() == item.getId();
	}

	public void setItem(Item tempItem) {
		this.item = tempItem;
		this.refreshObserver();
	}

	public void refreshObserver(){
		if(!this.isNewItem){
			this.id.setText(Integer.toString(this.item.getId()));
			if(!this.isProduct){
				this.partsOrProductTemplates.setSelectedItem(
						this.item.getPart());
			} else {
				this.partsOrProductTemplates.setSelectedItem(
						this.item.getProductTemplate());
			}
			
			this.quantity.setText(Integer.toString(this.item.getQuantity()));
			this.location.setSelectedItem(this.item.getLocation().getValue());
		} else {
			this.partsOrProductTemplates.setSelectedIndex(0);
		}
	}


	public void registerListener(ItemDetailController listener) {
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(listener);
			}
		}
	}
	
	public Item getItem(){
		Item temp = null;
		if(!this.isProduct){
			temp = new Item((this.isNewItem) ? -1 : this.getId(), 
					this.getPart(), 
					this.getQuantity(), 
					this.getItemLocation());
		} else {
			temp = new Item((this.isNewItem) ? -1 : this.getId(), 
					this.getProductTemplate(), 
					this.getQuantity(), 
					this.getItemLocation());
		}
		if(!this.isNewItem){
			temp.setLastModified(this.item.getLastModified());
		}
		return temp;
	}
		
	public int getId(){
		return Integer.parseInt(this.id.getText());
	}
	
	public Part getPart(){
		Part part = (Part)this.partsOrProductTemplates.getSelectedItem();
		return part;
	}
	
	public ProductTemplate getProductTemplate(){
		ProductTemplate productTemplate = 
				(ProductTemplate)this.partsOrProductTemplates
					.getSelectedItem();
		return productTemplate;
	}
	
	public int getQuantity(){
		int i = -1;
		try{
			i = Integer.parseInt(this.quantity.getText());
		} catch(NumberFormatException e){
			i = -1;
			e.printStackTrace();
		}
		return i;
	}
	
	public Entry<Integer, String> getItemLocation(){
		String location = (String)this.location.getSelectedItem();
		return this.locations.getEntryForLocation(location);
	}
	
	public void setNewItem(boolean isNew){
		this.isNewItem = isNew;
		this.id.setVisible(!isNew);
		this.idLabel.setVisible(!isNew);
	}
}
