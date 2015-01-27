package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import main.model.Inventory;
import main.model.Item;
import main.view.InventoryListView;
import main.view.PartsDetailView;

public class InventoryListController implements MouseListener, ActionListener {
	
	private InventoryListView listView;
	
	private Inventory inventory;

	public InventoryListController(InventoryListView listView, Inventory inventory) {
		this.listView = listView;
		this.inventory = inventory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				JList list = (JList)e.getSource();
				PartsDetailView view = new PartsDetailView();
				view.setItem((Item)list.getSelectedValue());
				view.showPartsDetailView();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		
	}
	
}