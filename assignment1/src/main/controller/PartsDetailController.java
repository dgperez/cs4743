package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.model.Part;
import main.model.PartsInventory;
import main.view.PartsDetailView;

public class PartsDetailController implements ActionListener {

	private PartsDetailView view;
	
	private PartsInventory partsInventory;

	private Inventory inventory;

	private boolean newPart = false;

	private boolean editPart = false;
	
	public PartsDetailController(PartsDetailView view, 
			PartsInventory partsInventory) {
		this.view = view;
		this.partsInventory = partsInventory;
		view.showPartsDetailView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("savePart")){
			Part part = this.view.getPart();
			try {
				if(this.partsInventory.validateSavedPart(part)){
					this.partsInventory.addPart(part);
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		} else if (e.getActionCommand().equals("addPart")){
			
		} else if (e.getActionCommand().equals("deletePart")){
			
		}
	}

	public void partIsNew(){
		this.newPart = true;
	}
	
	public void editPart(){
		this.editPart  = true;
	}
}
