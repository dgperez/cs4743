package main.model;

import java.util.ArrayList;
import java.util.List;

import main.view.InventoryListView;
import main.view.PartsDetailView;

public class Inventory {
	private List<Item> inventory;
	
	private InventoryListView view;
	
	private ArrayList<PartsDetailView> observers = new ArrayList<PartsDetailView>();
	
 	public List<Item> getInventory(){
 		return inventory;
 	}
 	
 	public void createInventory() {
 		inventory = new ArrayList<Item>();
 	}
	
 	/**
 	 * Adds an item to the inventory if its part number is not in the list.
 	 * @throws Exception - only if the item's part number already exists in the inventory
 	 */
	public void addItem(Item item, List<Item> inventory) throws Exception {
		boolean partExists = false;
		for(Item i : inventory) {
			if(i.getPartName().equals(item.getPartName())) {
				System.out.println(i.getPartName() + " " + item.getPartName());
				partExists = true;
				if(!i.canEditPart()){
					throw new Exception("Part number already exists in the list.");
				} else {
					i.setPartNumber(item.getPartNumber());
					i.setPartName(item.getPartName());
					i.setVendor(item.getVendor());
					i.setQuantity(item.getQuantity());
				}
			}
		}
		if(!partExists){
			inventory.add(item);
		}
		this.updateView();
	}
	
	/**
	 * Removes item from the inventory list.
	 * @param item - List<Item> to be removed from the inventory.
	 * @param inventory
	 */
	public void removeItem(Item item, List<Item> inventory) {
		inventory.remove(item);
		this.updateView();
	}
	
	/**
	 * Removes item located at index from the inventory list.
	 * @param index - index of item to be removed from the inventory list
	 * @param inventory 
	 */
	public void removeItem(int index, List<Item> inventory) {
		inventory.remove(index);
	}
	
	public void registerView(InventoryListView inventoryListView){
		this.view = inventoryListView;
	}
	
	public void registerObservers(PartsDetailView partsDetailView){
		this.observers.add(partsDetailView);
	}
	
	private void updateObservers(){
		for(PartsDetailView partsDetailView : this.observers){
			partsDetailView.refreshObserver();
		}
	}
	
	public void updateView(){
		this.view.refreshList(this);
		this.updateObservers();
	}
}
