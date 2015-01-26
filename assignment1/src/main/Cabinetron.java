package main;

import main.controller.InventoryListController;
import main.model.Inventory;
import main.model.Item;
import main.view.InventoryListView;
import main.view.PartsDetailView;

public class Cabinetron {

	/** CS 4743 Assignment 1 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){
		Inventory inventoryList = new Inventory();
		inventoryList.createInventory();

		Item demo1 = new Item("0001", "Part1", "Vendor1", 2);
		Item demo2 = new Item("0003", "Part3", "Vendor2", 3);
		Item demo3 = new Item("0014", "Part19", "Vendor4", 5);
		
		try {
			inventoryList.addItem(demo1, inventoryList.getInventory());
			inventoryList.addItem(demo2, inventoryList.getInventory());
			inventoryList.addItem(demo3, inventoryList.getInventory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InventoryListView test1 = new InventoryListView(inventoryList);
		
		PartsDetailView test2 = new PartsDetailView();
		
		InventoryListController controller = new InventoryListController(test1, inventoryList);
		
		test1.registerListener(controller);
		
		test2.setItem(demo1);
		test2.showPartsDetailView();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		demo1.setPartNumber("9999");
		demo1.setPartName("part-test");
		demo1.setVendor("noVendor");
		demo1.setQuantity(100);
		test2.refreshItemDisplayed();
		
	}

}
