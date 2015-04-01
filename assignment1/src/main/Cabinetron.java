package main;

import java.util.ArrayList;

import main.controller.InventoryListController;
import main.controller.LoginController;
import main.controller.PartsListController;
import main.controller.ProductTemplateListController;
import main.dao.AbstractDao;
import main.dao.ConnectionGateway;
import main.dao.TypeDao;
import main.model.Inventory;
import main.model.Locations;
import main.model.PartsInventory;
import main.model.ProductTemplateInventory;
import main.model.ProductTemplates;
import main.model.Session;
import main.model.UnitsOfQuantity;
import main.model.User;
import main.view.InventoryListView;
import main.view.LoginView;
import main.view.PartsListView;
import main.view.ProductTemplateListView;

public class Cabinetron {

	/** CS 4743 Assignment 4 by Josh Smith and Kyle Haley
	 * 
	 * */
	public static void main(String[] args){

		try {
			ConnectionGateway connGateway = new ConnectionGateway();
			
			User user1 = new User("Tom Jones", "tom.jones@test.com", 
					"Production Manager");
			User user2 = new User("Sue Smith", "sue.smith@test.com", 
					"Inventory Manager");
			User user3 = new User("Ragnar Nelson", "ragnar.nelson@test.com", 
					"Admin");
			ArrayList<User> users = new ArrayList<User>();
			
			users.add(user1);
			users.add(user2);
			users.add(user3);
			
			Session session;
			
			LoginView loginView = new LoginView(users);
			LoginController loginController = 
					new LoginController(loginView, connGateway);
			loginView.registerListener(loginController);
			
			boolean validLogin = false;
			while(!validLogin){
				validLogin = loginView.getValidLogin();
				// The while loop needs a pause for this assignment, 
				//otherwise it seems a race condition prevents the 
				//assignment from working.
				// C# event handling is superior.
				Thread.sleep(100);
			}

			session = loginView.getSession();
			
			loginView.closeView();
			
			TypeDao typeDao = new TypeDao(connGateway);

			Inventory inventory = new Inventory(connGateway);
			inventory.loadInitialInventory();
			
			PartsInventory partsInventory = new PartsInventory(connGateway, 
					inventory);
			partsInventory.loadParts();
			ProductTemplates productTemplates = 
					new ProductTemplates(connGateway);
			productTemplates.loadInitialProductTemplates();
			ProductTemplateInventory productInventory = 
					new ProductTemplateInventory(connGateway);
			productInventory.loadInitialInventory();
			if(session.canViewProductTemplates()){
				ProductTemplateListView productTemplatesListView = 
						new ProductTemplateListView(productTemplates);
				productTemplates.registerView(productTemplatesListView);

				ProductTemplateListController productTemplatesListController = 
						new ProductTemplateListController(productTemplates, 
								productTemplatesListView, connGateway);
				productTemplatesListView
				.registerListener(productTemplatesListController);
			}
			
			
			InventoryListView inventoryListView = null;
			InventoryListController inventoryListController = null;
			if(session.canViewInventory()){
				Locations locations = new Locations();
				locations.resetLocations(typeDao.getTypeList(
						AbstractDao.TableType.LOCATIONS.getType()));
				
				inventoryListView = new InventoryListView(inventory, productInventory, session);
				inventoryListController = new 
						InventoryListController(inventoryListView, 
								inventory, locations, partsInventory, session, 
								productTemplates);
				inventoryListView.registerListener(inventoryListController);
				inventory.registerView(inventoryListView);
			}
			
			if(session.canViewParts()){
				UnitsOfQuantity unitsOfQuantity = new UnitsOfQuantity();
				unitsOfQuantity.resetUnitsOfQuantity(typeDao.getTypeList(
						AbstractDao.TableType.UNITS_OF_QUANTITY.getType()));
				
				PartsListView partsListView = 
						new PartsListView(partsInventory, session);
	
				PartsListController partsListController = 
						new PartsListController(partsInventory, unitsOfQuantity,
								partsListView, session);
				partsListView.registerListener(partsListController);
	
				partsInventory.registerView(partsListView);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
