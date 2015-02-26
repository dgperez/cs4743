package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			if(this.view.getPartIndex() < 0){
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
			if(this.view.getLocationIndex() < 0){
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
				System.out.printf("PartIndex: %d\n", view.getPartIndex());
				//System.out.printf("%f\n", parts.getPartBy_Id(this.view.getPartIndex()+2).toString());
				System.out.printf("Quantity:  %d\n", view.getQuantity());
				System.out.printf("Location:  %d\n", view.getLocationIndex());
				if(editItem){
					this.item.setQuantity(this.view.getQuantity());
					this.item.setLocation(
							locations.getLocationById(this.view.getLocationIndex()));
					try {
						inventory.editItem(item);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					item = new Item(
							12, 
							parts.getPartBy_Id(this.view.getPartIndex()), 
							this.view.getQuantity(), 
							locations.getLocationById(this.view.getLocationIndex()));
					try {
						inventory.addItem(item);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
	}

	public void editItem() {
		// TODO Auto-generated method stub
		editItem = true;
	}

	public void itemIsNew() {
		// TODO Auto-generated method stub
		newItem = true;
	}

}
