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
	
	private Part part;
	
	private PartsInventory partsInventory;
	
	public PartsDetailController(PartsDetailView view, Part part
			, PartsInventory partsInventory) {
		this.view = view;
		this.part = part;
		this.partsInventory = partsInventory;
		view.showPartsDetailView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("savePart")){
			
		} else if (e.getActionCommand().equals("addPart")){
			
		} else if (e.getActionCommand().equals("deletePart")){
			
		}
	}
}
