package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.dao.ConnectionGateway;
import main.model.Inventory;
import main.model.Item;
import main.model.Locations;
import main.model.PartsInventory;
import main.view.ItemDetailView;

public class ItemDetailController implements ActionListener {
	
	private ItemDetailView view;
	
	private Item item;
	
	private Inventory inventory;
	
	private boolean newItem = false;
	
	private boolean editItem = false;
	
	public ItemDetailController(ItemDetailView view
			, Item tempItem
			, Inventory inventory) {
		this.view = view;
		this.item = tempItem;
		this.inventory = inventory;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("saveItem")){
			boolean valid = true;
			if(this.view.getPart() == null){
				valid = false;
				JOptionPane.showMessageDialog(null, 
						"Select an Part.", 
						"No Part Selected.", JOptionPane.ERROR_MESSAGE);
			}
			if(this.view.getQuantity() < 1){
				valid = false;
				JOptionPane.showMessageDialog(null, 
						"Enter a Quantity greater than or equal to zero.", 
						"Invalid Quantity.", JOptionPane.ERROR_MESSAGE);
			}
			if(this.view.getLoc() == null){
				valid = false;
				JOptionPane.showMessageDialog(null, 
						"Select a Location.", 
						"No Location Selected.", JOptionPane.ERROR_MESSAGE);
			}
			if(valid){
				ConnectionGateway connGateway = new ConnectionGateway();
				PartsInventory parts = new PartsInventory(connGateway);
				parts.getAllParts();
				Locations locations = new Locations();
				locations.getLocations();
				if(editItem){
					this.item.setQuantity(this.view.getQuantity());
					this.item.setLocation(this.view.getLoc());
					try {
						inventory.editItem(item);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					item = new Item(
							-1, 
							this.view.getPart(), 
							this.view.getQuantity(), 
							this.view.getLoc());
					try {
						inventory.addItem(item);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				view.refreshObserver();
				view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
			}
		}
		
	}

	public void editItem() {
		editItem = true;
	}

	public void itemIsNew() {
		newItem = true;
	}

}
