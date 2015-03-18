package main;

import java.sql.Connection;
import java.sql.SQLException;

import main.controller.InventoryListController;
import main.controller.PartsListController;
import main.controller.ProductTemplateListController;
import main.dao.ConnectionGateway;
import main.dao.ItemDao;
import main.dao.PartDao;
import main.dao.TypeDao;
import main.model.Inventory;
import main.model.Item;
import main.model.Locations;
import main.model.Part;
import main.model.PartsInventory;
import main.model.ProductTemplates;
import main.model.UnitsOfQuantity;
import main.view.InventoryListView;
import main.view.PartsListView;
import main.view.ProductTemplateListView;

public class Cabinetron {

	/** CS 4743 Assignment 1 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){
		
		// Testing
		/*
		try {
			ConnectionGateway connGateway = new ConnectionGateway();
			TypeDao typeDao = new TypeDao(connGateway);
			
			UnitsOfQuantity unitsOfQuantity = new UnitsOfQuantity();
			unitsOfQuantity.resetUnitsOfQuantity(typeDao.getTypeList(3));
			
			Locations locations = new Locations();
			locations.resetLocations(typeDao.getTypeList(1));
			
			Inventory inventory = new Inventory(connGateway);
			inventory.loadInitialInventory();
			
			PartsInventory partsInventory = new PartsInventory(connGateway, 
					inventory);
			partsInventory.loadParts();
			
			InventoryListView inventoryListView = 
					new InventoryListView(inventory);
			
			PartsListView partsListView = 
					new PartsListView(partsInventory);
			
			InventoryListController inventoryListController = 
					new InventoryListController(inventoryListView, inventory, 
							locations, partsInventory);
			
			PartsListController partsListController = 
					new PartsListController(partsInventory, unitsOfQuantity,
							partsListView);
			
			inventoryListView.registerListener(inventoryListController);
			partsListView.registerListener(partsListController);
			
			inventory.registerView(inventoryListView);
			partsInventory.registerView(partsListView);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		*/
		
		try{
			ConnectionGateway connGateway = new ConnectionGateway();
			ProductTemplates productTemplates = 
					new ProductTemplates(connGateway);
			productTemplates.loadInitialProductTemplates();
			
			ProductTemplateListView productTemplatesListView = 
					new ProductTemplateListView(productTemplates);
			productTemplates.registerView(productTemplatesListView);
			
			ProductTemplateListController productTemplatesListController = 
					new ProductTemplateListController(productTemplates, 
							productTemplatesListView, connGateway);
			productTemplatesListView
				.registerListener(productTemplatesListController);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
