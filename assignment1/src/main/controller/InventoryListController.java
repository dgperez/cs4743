package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.model.Locations;
import main.view.InventoryListView;
import main.view.ItemDetailView;
import main.view.PartsDetailView;

public class InventoryListController implements MouseListener, ActionListener {
	
	private InventoryListView listView;
	
	private Inventory inventory;
	
	private Locations locations;

	public InventoryListController(InventoryListView listView, 
			Inventory inventory, Locations locations) {
		this.locations = locations;
		this.listView = listView;
		this.inventory = inventory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				JList<Object> list = (JList<Object>)e.getSource();
				Item tempItem = (Item)list.getSelectedValue();
				ItemDetailView view = new ItemDetailView(this.locations);
				this.inventory.registerObservers(view);
				view.setItem(tempItem);
				ItemDetailController itemController = 
						new ItemDetailController(view, 
								tempItem, this.inventory);
				itemController.editItem();
				view.registerListener(itemController);
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
		if("add".equals(e.getActionCommand())){
			ItemDetailView view = new ItemDetailView(this.locations);
			ItemDetailController itemController = 
					new ItemDetailController(view, 
							null, this.inventory);
			this.inventory.registerObservers(view);
			itemController.itemIsNew();
			view.registerListener(itemController);
		} else if ("delete".equals(e.getActionCommand())){
			Object temp = this.listView.getSelectedListItem();
			if(temp != null){
				Item tempItem = (Item)temp;
				this.inventory.removeItem(tempItem, 
						this.inventory.getInventory());
			} else {
				JOptionPane.showMessageDialog(null, 
						"Select an item in the list to delete.", 
						"No Item to Delete", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
