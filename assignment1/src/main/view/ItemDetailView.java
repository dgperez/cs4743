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
import java.sql.SQLException;

import main.controller.ItemDetailController;
import main.dao.ConnectionGateway;
import main.model.Item;
import main.model.Locations;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import main.model.PartsInventory;

public class ItemDetailView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -297591929291152377L;

	private JTextField id;

	private JComboBox<String> parts;

	private PartsInventory partInventory;

	private JTextField quantity;

	private JComboBox<String> location;

	private Locations locations;

	private JLabel idLabel = new JLabel("Id: ");

	private JLabel quantityLabel = new JLabel("Quantity: ");

	private JLabel partLabel = new JLabel("Part: ");

	private JLabel locationLabel = new JLabel("Location: ");

	private JPanel controls;

	private JPanel inputs;

	private JButton saveItem;

	private Item item;

	public ItemDetailView(Locations locations) {
		this.locations = locations;
		this.inputs = new JPanel(new BorderLayout(5,5));

		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		ConnectionGateway connGateway = new ConnectionGateway();
		this.partInventory = new PartsInventory(connGateway);
		try {
			this.partInventory.loadParts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.id = new JTextField(10);
		this.parts = new JComboBox<String>(
				partInventory.getAllPartsToString());
		this.quantity = new JTextField(10);
		this.location = new JComboBox<String>(
				locations.getLocations());

		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.EAST);

		labelsPanel.add(this.idLabel);
		fieldsPanel.add(id);
		id.setEditable(false);

		labelsPanel.add(this.partLabel);
		fieldsPanel.add(this.parts);

		labelsPanel.add(quantityLabel);
		fieldsPanel.add(quantity);

		labelsPanel.add(locationLabel);
		fieldsPanel.add(location);

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
		if(partInventory.getPartBy_PartNumber(item.getPart().getPartNumber()) != null){
			return true;
		}
		return false;
	}

	public void setItem(Item tempItem) {
		this.item = tempItem;
		this.refreshObserver();
	}

	public void refreshObserver(){
		if(this.item != null){
			this.id.setText(Integer.toString(this.item.getId()));
			this.parts.setSelectedItem(this.item.getPart());
			this.quantity.setText(Integer.toString(this.item.getQuantity()));
			this.location.setSelectedItem(this.item.getLocation());
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
		
	public int getPartIndex(){
		return this.parts.getSelectedIndex();
	}
	
	public int getQuantity(){
		int i = -1;
		try{
			i = Integer.parseInt(this.quantity.getText());
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		return i;
	}
	
	public int getLocationIndex(){
		return this.location.getSelectedIndex() + 1;
	}
}
