package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.view.ItemDetailView;

public class ItemDetailController implements ActionListener {
	
	private ItemDetailView view;
	
	private Inventory inventory;
	
	private boolean newItem = false;
	
	public ItemDetailController(ItemDetailView view
			, Inventory inventory) {
		this.view = view;
		this.inventory = inventory;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("saveItem")){
			Item item = this.view.getItem();
			try {
				if(this.inventory.validateItem(item)){
					if(this.newItem){
						this.view.setItem(inventory.addItem(item));
						this.newItem = false;
						this.view.setNewItem(this.newItem);
					} else {
						this.view.setItem(item);
						this.inventory.editItem(item);
					}
					this.view.refreshObserver();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
				e1.printStackTrace();
			}
		}
		
	}

	public void itemIsNew() {
		newItem = true;
	}

}
