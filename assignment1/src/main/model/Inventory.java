package main.model;

import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.PartDao;
import main.view.InventoryListView;
import main.view.PartsDetailView;

public class Inventory {
	private List<Item> inventory;
	
	private InventoryListView view;
	
	private boolean viewCreated = false;
	
	private ArrayList<PartsDetailView> observers = 
			new ArrayList<PartsDetailView>();
	
	private ConnectionGateway connGateway;
	
	public Inventory(ConnectionGateway connGateway){
		this.connGateway = connGateway;
		this.inventory = new ArrayList<Item>();
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
 		this.updateView();
 	}
 	
 	/**
 	 * Adds an item to the inventory if its part number is not in the list.
 	 * @throws Exception - only if the item's part number already 
 	 * exists in the inventory
 	 */
	public void addItem(Item item, List<Item> inventory) throws Exception {
		boolean partExists = false;
		for(Item i : inventory) {
			if(i.getPartName().equals(item.getPartName())) {
				partExists = true;
				if(!i.canEditPart()){
					throw new Exception("Part number already " +
							"exists in the list.");
				} else {
					i.setPartNumber(item.getPartNumber());
					i.setPartName(item.getPartName());
					i.setVendor(item.getVendor());
					i.setQuantity(item.getQuantity());
					i.setUnitOfQuantity(item.getUnitOfQuantity());
					i.setLocation(item.getLocation());
					i.setExternalPartNumber(item.getExternalPartNumber());
				}
			}
		}
		if(!partExists){
			item.incrementId();
			inventory.add(item);
		}
		this.updateView();
	}
	
	public void editItem(Item item){
		
	}
	
	/**
	 * Removes item from the inventory list.
	 * @param item - List<Item> to be removed from the inventory.
	 * @param inventory
	 */
	public void removeItem(Item item, List<Item> inventory) {
		inventory.remove(item);
		this.updateView();
		this.closeOpenObservers(item);
	}
	
	/**
	 * Removes item located at index from the inventory list.
	 * @param index - index of item to be removed from the inventory list
	 * @param inventory 
	 */
	public void removeItem(int index, List<Item> inventory) {
		Item temp = inventory.remove(index);
		this.updateView();
		this.closeOpenObservers(temp);
	}
	
	public void registerView(InventoryListView inventoryListView){
		this.view = inventoryListView;
		this.viewCreated = true;
	}
	
	public void registerObservers(PartsDetailView partsDetailView){
		this.observers.add(partsDetailView);
	}
	
	private void updateObservers(){
		for(PartsDetailView partsDetailView : this.observers){
			partsDetailView.refreshObserver();
		}
	}
	
	private void closeOpenObservers(Item item){
		ArrayList<PartsDetailView> itemsToRemove = 
				new ArrayList<PartsDetailView>();
		for(PartsDetailView partsDetailView : this.observers){
			if(partsDetailView.containsItem(item)){
				itemsToRemove.add(partsDetailView);
			}
		}
		
		this.observers.removeAll(itemsToRemove);
		
		for(PartsDetailView removedViews : itemsToRemove){
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
