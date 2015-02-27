package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.dao.ConnectionGateway;
import main.dao.ItemDao;
import main.model.Inventory;
import main.model.Item;
import main.model.Locations;
import main.model.PartsInventory;
import main.view.InventoryListView;
import main.view.ItemDetailView;

public class InventoryListController implements MouseListener, ActionListener {
	
	private InventoryListView listView;
	
	private Inventory inventory;
	
	private Locations locations;
	
	private PartsInventory partsInventory;

	public InventoryListController(InventoryListView listView, 
			Inventory inventory, Locations locations, 
			PartsInventory partsInventory) {
		this.locations = locations;
		this.listView = listView;
		this.inventory = inventory;
		this.partsInventory = partsInventory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				@SuppressWarnings("unchecked")
				JList<Object> list = (JList<Object>)e.getSource();
				Item tempItem = (Item)list.getSelectedValue();
				ItemDetailView view = new 
						ItemDetailView(this.locations, this.partsInventory, 
								false);
				this.inventory.registerObservers(view);
				view.setItem(tempItem);
				ItemDetailController itemController = 
						new ItemDetailController(view, this.inventory);
				view.registerListener(itemController);
				this.inventory.registerObservers(view);
				view.setVisible(true);
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
			ItemDetailView view = new ItemDetailView(this.locations, 
					this.partsInventory, true);
			ItemDetailController itemController = 
					new ItemDetailController(view, this.inventory);
			this.inventory.registerObservers(view);
			itemController.itemIsNew();
			view.registerListener(itemController);
			view.showItemDetailView();
		} else if ("delete".equals(e.getActionCommand())){
			Object temp = this.listView.getSelectedListItem();
			if(temp != null){
				Item tempItem = (Item)temp;
				if(tempItem.getQuantity() != 0){
					JOptionPane.showMessageDialog(null, 
							"Quantity must be zero to delete item.", 
							"Quantity Greater Than Zero", 
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					try {
						this.inventory.removeItem(tempItem, 
								this.inventory.getInventory());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, 
						"Select an item in the list to delete.", 
						"No Item to Delete", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
