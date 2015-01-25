package main;

import main.model.Inventory;
import main.model.Item;
import main.view.InventoryListView;
import main.view.PartsDetailView;

public class Cabinetron {

	/** CS 4743 Assignment 1 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){
		Inventory allItemsInventory = new Inventory();
		allItemsInventory.createInventory();

		Item demo1 = new Item("0001", "Part1", "Vendor1", 2);
		Item demo2 = new Item("0003", "Part3", "Vendor2", 3);
		Item demo3 = new Item("0014", "Part19", "Vendor4", 5);
		
		try {
			allItemsInventory.addItem(demo1, allItemsInventory.getInventory());
			allItemsInventory.addItem(demo2, allItemsInventory.getInventory());
			allItemsInventory.addItem(demo3, allItemsInventory.getInventory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InventoryListView test = new InventoryListView(allItemsInventory);
		PartsDetailView test2 = new PartsDetailView();
		
	}

}
