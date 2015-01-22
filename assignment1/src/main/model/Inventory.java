package main.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> inventory;
	
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
		for(Item i : inventory) {
			if(i.getPartNumber().equals(item.getPartNumber())) {
				throw new Exception("Part number already exists in the list.");
			}
		}
		inventory.add(item);
	}
	
	/**
	 * Removes item from the inventory list.
	 * @param item - List<Item> to be removed from the inventory.
	 * @param inventory
	 */
	public  void removeItem(Item item, List<Item> inventory) {
		inventory.remove(item);
	}
	
	/**
	 * Removes item located at index from the inventory list.
	 * @param index - index of item to be removed from the inventory list
	 * @param inventory 
	 */
	public static void removeItem(int index, List<Item> inventory) {
		inventory.remove(index);
	}
}
