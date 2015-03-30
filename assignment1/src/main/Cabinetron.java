package main;

import main.controller.InventoryListController;
import main.controller.PartsListController;
import main.controller.ProductTemplateListController;
import main.dao.AbstractDao;
import main.dao.ConnectionGateway;
import main.dao.TypeDao;
import main.model.Authenticator;
import main.model.Inventory;
import main.model.Locations;
import main.model.PartsInventory;
import main.model.ProductTemplates;
import main.model.Session;
import main.model.UnitsOfQuantity;
import main.view.InventoryListView;
import main.view.PartsListView;
import main.view.ProductTemplateListView;

public class Cabinetron {

	/** CS 4743 Assignment 4 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){
		
		// Testing
		
		try {
			ConnectionGateway connGateway = new ConnectionGateway();
			TypeDao typeDao = new TypeDao(connGateway);

			Authenticator auth = new Authenticator("tjones", "tjonespass", connGateway);
			Session session = auth.Authenticate();
			
			UnitsOfQuantity unitsOfQuantity = new UnitsOfQuantity();
			unitsOfQuantity.resetUnitsOfQuantity(typeDao.getTypeList(
					AbstractDao.TableType.UNITS_OF_QUANTITY.getType()));
			
			Locations locations = new Locations();
			locations.resetLocations(typeDao.getTypeList(
					AbstractDao.TableType.LOCATIONS.getType()));
			
			Inventory inventory = new Inventory(connGateway);
			inventory.loadInitialInventory();
			
			PartsInventory partsInventory = new PartsInventory(connGateway, 
					inventory);
			partsInventory.loadParts();
			
			InventoryListView inventoryListView = 
					new InventoryListView(inventory, session);
			
			PartsListView partsListView = 
					new PartsListView(partsInventory);
			
			InventoryListController inventoryListController = 
					new InventoryListController(inventoryListView, inventory, 
							locations, partsInventory, session);
			
			PartsListController partsListController = 
					new PartsListController(partsInventory, unitsOfQuantity,
							partsListView);
			
			inventoryListView.registerListener(inventoryListController);
			partsListView.registerListener(partsListController);
			
			inventory.registerView(inventoryListView);
			partsInventory.registerView(partsListView);
			
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
