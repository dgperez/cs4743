package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.view.InventoryListView;
import main.view.PartsDetailView;

public class InventoryListController implements MouseListener, ActionListener {
	
	private InventoryListView listView;
	
	private Inventory inventory;

	public InventoryListController(InventoryListView listView, 
			Inventory inventory) {
		this.listView = listView;
		this.inventory = inventory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				JList list = (JList)e.getSource();
				Item tempItem = (Item)list.getSelectedValue();
				PartsDetailView view = new PartsDetailView();
				this.inventory.registerObservers(view);
				view.setItem(tempItem);
				PartsDetailController partController = 
						new PartsDetailController(view, 
								tempItem, this.inventory);
				partController.editPart();
				view.registerListener(partController);
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
			PartsDetailView view = new PartsDetailView();
			PartsDetailController partController = 
					new PartsDetailController(view, 
							null, this.inventory);
			this.inventory.registerObservers(view);
			partController.itemIsNew();
			view.registerListener(partController);
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
