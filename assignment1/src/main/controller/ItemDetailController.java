package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.model.Session;
import main.view.ItemDetailView;

public class ItemDetailController implements ActionListener {
	
	private ItemDetailView view;
	
	private Inventory inventory;
	
	private boolean newItem = false;
	
	private Session session;
	
	public ItemDetailController(ItemDetailView view
			,Inventory inventory, Session session) {
		this.view = view;
		this.inventory = inventory;
		this.session = session;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("saveItem") 
				&& this.session.canAddInventory()){
			Item item = this.view.getItem();
			boolean itemRefreshed = false;
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
				if(message.contains(
						"This item has been modified by another user")){
					try{
						this.view.setItem(this.inventory.getItem(item.getId()));
						itemRefreshed = true;
					} catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Error: " + 
								ex.getMessage());
					}
				}
				e1.printStackTrace();
			}
			this.view.refreshObserver();
			if(!itemRefreshed){
				this.view.dispatchEvent(new WindowEvent(this.view, 
						WindowEvent.WINDOW_CLOSING));
			}
		}
		
	}

	public void itemIsNew() {
		newItem = true;
	}

}
