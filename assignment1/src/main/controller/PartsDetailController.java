package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.model.Part;
import main.model.UnitsOfQuantity;
import main.view.PartsDetailView;

public class PartsDetailController implements ActionListener {

	private PartsDetailView view;
	
	private Part part;
	
	private Inventory inventory;
	
	private boolean newItem = false;
	
	private boolean editPart = false;
	
	public PartsDetailController(PartsDetailView view
			, Part item
			, Inventory inventory) {
		this.view = view;
		this.part = item;
		this.inventory = inventory;
		view.showPartsDetailView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("savePart")){
			Item tempItem = (Item)e.getSource();
			if(validateSavedItem(tempItem)){
				try {
					this.inventory.addItem(tempItem);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
		}
	}
	
	private boolean validateSavedItem(Item item){
		boolean valid = true;
		String message = "";
		String title = "Form Entry Error";
		if(item.getPart().getPartNumber().length() <= 0 
				|| item.getPart().getPartNumber().length() > 20){
			valid = false;
			message += "Part Number must be between 0 and 20 characters long.\n";
		}
		if(item.getPart().getPartName().length() <= 0 
				|| item.getPart().getPartName().length() > 255){
			valid = false;
			message += "Part Name must be between 0 and 255 characters long.\n";
		}
		if(item.getPart().getVendor().getValue().length() > 255){
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
		if(item.getPart().getUnitOfQuantity().getValue().equals("Unknown")){
			valid = false;
			message += "Quantity cannot be 'Unknown'.\n";
		}
		if(item.getLocation().equals("Unknown")){
			valid = false;
			message += "Location cannot be 'Unknown'.\n";
		}
		if(!item.getPart().getExternalPartNumber().isEmpty() && 
				item.getPart().getExternalPartNumber().length() > 50){
			valid = false;
			message += "External Part Number must be between 0 and 50 characters long.\n";
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
