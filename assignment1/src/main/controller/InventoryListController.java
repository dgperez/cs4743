package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.Inventory;
import main.model.Item;
import main.model.Locations;
import main.model.PartsInventory;
import main.model.ProductTemplate;
import main.model.ProductTemplates;
import main.model.Session;
import main.view.InventoryListView;
import main.view.ItemDetailView;
import main.view.ProductTemplateDetailView;

public class InventoryListController implements MouseListener, ActionListener {

	private InventoryListView listView;

	private Inventory inventory;

	private ProductTemplates productTemplates;

	private Locations locations;

	private PartsInventory partsInventory;

	private Session session;

	public InventoryListController(InventoryListView listView, 
			Inventory inventory, Locations locations, 
			PartsInventory partsInventory, Session session, ProductTemplates productTemplates) {
		this.locations = locations;
		this.listView = listView;
		this.inventory = inventory;
		this.partsInventory = partsInventory;
		this.session = session;
		this.productTemplates = productTemplates;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2 && session.canAddInventory()){
			if(e.getSource() instanceof JList){
				@SuppressWarnings("unchecked")
				JList<Object> list = (JList<Object>)e.getSource();
				Item tempItem = (Item)list.getSelectedValue();
				boolean isProduct = false;
				if(tempItem.getPart() == null){
					isProduct = true;
				}
				ItemDetailView view = new 
						ItemDetailView(this.locations, this.partsInventory, 
								false, isProduct);
				this.inventory.registerObservers(view);
				view.setItem(tempItem);
				ItemDetailController itemController = 
						new ItemDetailController(view, this.inventory, 
								this.session);
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
		boolean isProduct = false;
		if ("addProduct".equals(e.getActionCommand())){
			isProduct = true;
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
		if(e.getActionCommand().startsWith("add")){
			ItemDetailView view = new ItemDetailView(this.locations, 
					this.partsInventory, true, isProduct);
			ItemDetailController itemController = 
					new ItemDetailController(view, this.inventory, 
							this.session);
			this.inventory.registerObservers(view);
			itemController.itemIsNew();
			view.registerListener(itemController);
			view.showItemDetailView();
		}
	}
}
