package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

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
						this.view.setItem(this.inventory.addItem(item));
						this.newItem = false;
						this.view.setNewItem(this.newItem);
					} else {
						this.view.setItem(this.inventory.editItem(item));
					}
				}
			} catch (Exception e1) {
				String message = e1.getMessage();
				JOptionPane.showMessageDialog(null, "Error: " + 
						message);
				try{
					this.view.setItem(this.inventory.getItem(item.getId()));
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Error: " + 
							ex.getMessage());
				}
				e1.printStackTrace();
			}
			this.view.refreshObserver();
			//this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
		}
		
	}

	public void itemIsNew() {
		newItem = true;
	}

}
