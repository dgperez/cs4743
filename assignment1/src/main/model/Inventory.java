package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.ItemDao;
import main.view.InventoryListView;
import main.view.ItemDetailView;

public class Inventory {
	private List<Item> inventory;
	
	private InventoryListView view;
	
	private boolean viewCreated = false;
	
	private ArrayList<ItemDetailView> observers = 
			new ArrayList<ItemDetailView>();
	
	private ConnectionGateway connGateway;
	
	private ItemDao itemDao;
	
	public Inventory(ConnectionGateway connGateway){
		this.connGateway = connGateway;
		this.inventory = new ArrayList<Item>();
		this.itemDao = new ItemDao(this.connGateway);
	}
	
	public void loadInitialInventory() throws Exception{
		ArrayList<Item> items = this.itemDao.getItems();
		this.inventory = items;
	}
	
 	public List<Item> getInventory(){
 		return this.inventory;
 	}
	
 	public void addItem(Item item) throws Exception{
 		for(Item i : this.inventory){
 			if(i.equals(item)){
 				throw new Exception("Part number already " +
						"exists in the list.");
 			}
 		}
 		this.inventory.add(item);
 		this.itemDao.addItem(item);
 		this.updateView();
 	}
	
	public void editItem(Item item) throws SQLException{
		for(Item i : this.inventory){
			if(i.getId() == item.getId()){
				i = item;
			}
		}
		this.itemDao.editItem(item);
	}
	
	/**
	 * Removes item from the inventory list.
	 * @param item - List<Item> to be removed from the inventory.
	 * @param inventory
	 * @throws SQLException 
	 */
	public void removeItem(Item item, List<Item> inventory) throws SQLException {
		inventory.remove(item);
		this.itemDao.deleteItem(item);
		this.updateView();
		this.closeOpenObservers(item);
	}
	
	/**
	 * Removes item located at index from the inventory list.
	 * @param index - index of item to be removed from the inventory list
	 * @param inventory 
	 * @throws SQLException 
	 */
	public void removeItem(int index, List<Item> inventory) throws SQLException {
		Item temp = inventory.remove(index);
		this.itemDao.deleteItem(temp);
		this.updateView();
		this.closeOpenObservers(temp);
	}
	
	public void registerView(InventoryListView inventoryListView){
		this.view = inventoryListView;
		this.viewCreated = true;
	}
	
	public void registerObservers(ItemDetailView itemDetailView){
		this.observers.add(itemDetailView);
	}
	
	private void updateObservers(){
		for(ItemDetailView itemDetailView : this.observers){
			itemDetailView.refreshObserver();
		}
	}
	
	public boolean validateItem(Item item) throws Exception{
		String message = "";
		boolean valid = true;
		if(item.getLocation().getValue().equals("Unknown")){
			message += "Location cannot be unknown.\n";
			valid = false;
		}
		for(Item i : this.inventory){
			if(i.getPart().equals(item.getPart()) &&
					i.getLocation().getValue().equals(
							item.getLocation().getValue())){
				message += "No two parts can have the same " +
						"Location and Part Number.\n";
				valid = false;
				break;
			}
		}
		if(!valid){
			throw new Exception(message);
		}
		return valid;
	}
	
	private void closeOpenObservers(Item item){
		ArrayList<ItemDetailView> itemsToRemove = 
				new ArrayList<ItemDetailView>();
		for(ItemDetailView itemDetailView : this.observers){
			if(itemDetailView.containsItem(item)){
				itemsToRemove.add(itemDetailView);
			}
		}
		
		this.observers.removeAll(itemsToRemove);
		
		for(ItemDetailView removedViews : itemsToRemove){
			removedViews.dispose();
		}
	}
	
	public void updateView(){
		if(viewCreated) {
			this.view.refreshList(this);
		}
		this.updateObservers();
	}
}
