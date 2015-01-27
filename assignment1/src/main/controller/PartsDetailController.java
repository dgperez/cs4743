package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.model.Inventory;
import main.model.Item;
import main.view.PartsDetailView;

public class PartsDetailController implements ActionListener {

	private PartsDetailView view;
	
	private Item item;
	
	private Inventory inventory;
	
	public PartsDetailController(PartsDetailView view
			, Item item
			, Inventory inventory) {
		this.view = view;
		this.item = item;
		this.inventory = inventory;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("save")){
			
		}
	}

}
