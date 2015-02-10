package main;

import main.controller.InventoryListController;
import main.model.Inventory;
import main.model.Item;
import main.model.Item.Location;
import main.model.Item.UnitOfQuantity;
import main.view.InventoryListView;

public class Cabinetron {

	/** CS 4743 Assignment 1 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){
		Inventory inventoryList = new Inventory();
		inventoryList.createInventory();
		InventoryListView test1 = new InventoryListView(inventoryList);
		inventoryList.registerView(test1);

		Item demo1 = 
				new Item("0001", "Part1", "Vendor1", 2, UnitOfQuantity.PIECES, 
						Location.FACILITY_1_WAREHOUSE_1, "");
		Item demo2 = 
				new Item("0003", "Part3", "Vendor2", 3, UnitOfQuantity.PIECES, 
						Location.FACILITY_1_WAREHOUSE_2, "");
		Item demo3 = 
				new Item("0014", "Part19", "Vendor4", 5, 
						UnitOfQuantity.LINEAR_FEET, Location.FACILITY_2, "");
		Item demo4 = 
				new Item("0099", "Eva01", "NERV", 1, UnitOfQuantity.PIECES,
						Location.FACILITY_1_WAREHOUSE_1, "");
		
		try {
			inventoryList.addItem(demo1, inventoryList.getInventory());
			inventoryList.addItem(demo2, inventoryList.getInventory());
			inventoryList.addItem(demo3, inventoryList.getInventory());
			inventoryList.addItem(demo4, inventoryList.getInventory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InventoryListController controller = new InventoryListController(test1, inventoryList);
		
		test1.registerListener(controller);
	}

}
