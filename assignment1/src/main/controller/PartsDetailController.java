package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.view.PartsDetailView;

public class PartsDetailController implements ActionListener {

	private PartsDetailView view;
	
	private Item item;
	
	private Inventory inventory;
	
	private boolean newItem = false;
	
	private boolean editPart = false;
	
	public PartsDetailController(PartsDetailView view
			, Item item
			, Inventory inventory) {
		this.view = view;
		this.item = item;
		this.inventory = inventory;
		view.showPartsDetailView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("save")){
			try{
				Item tempItem = this.view.getItem();
				tempItem.setEditPart(editPart);
				if(this.item == null){
					this.item = tempItem;
					this.view.setItem(this.item);
				}
				this.item.setEditPart(editPart);
				if(this.validateSavedItem(tempItem)){
					this.inventory.addItem(tempItem, 
							this.inventory.getInventory());
					this.view.refreshObserver();
					this.newItem = false;
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, 
						"A part already exists with that part name.", 
						"Part Exists", JOptionPane.ERROR_MESSAGE);
				//ex.printStackTrace();
			}
		}
	}
	
	private boolean validateSavedItem(Item item){
		boolean valid = true;
		String message = "";
		String title = "Form Entry Error";
		if(item.getPartNumber().length() <= 0 
				|| item.getPartNumber().length() > 20){
			valid = false;
			message += "Part Number must be between 0 and 20 characters long.\n";
		}
		if(item.getPartName().length() <= 0 
				|| item.getPartName().length() > 255){
			valid = false;
			message += "Part Name must be between 0 and 255 characters long.\n";
		}
		if(item.getVendor().length() > 255){
			valid = false;
			message += "Vendor must be between 0 and 255 characters long.\n";
		}
		if(item.getQuantity() < 0 && !this.newItem){
			valid = false;
			message += "Quantity must be at least zero.\n";
		}
		if(item.getQuantity() <= 0 && this.newItem){
			valid = false;
			message += "Initial quantity must be greater than zero.\n";
		}
		if(!valid){
			System.out.println(message);
			JOptionPane.showMessageDialog(null, 
					message, 
					title, JOptionPane.ERROR_MESSAGE);
		}
		return valid;
	}

	public void itemIsNew(){
		this.newItem = true;
	}
	
	public void editPart(){
		this.editPart = true;
	}
}
