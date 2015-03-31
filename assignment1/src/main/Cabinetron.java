package main;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import main.controller.InventoryListController;
import main.controller.LoginController;
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

		// Testing

		try {
			ConnectionGateway connGateway = new ConnectionGateway();
			
			User user1 = new User("Tom Jones", "tom.jones@test.com");
			User user2 = new User("Sue Smith", "sue.smith@test.com");
			User user3 = new User("Ragnar Nelson", "ragnar.nelson@test.com");
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
			}

			session = loginView.getSession();
			loginView.closeView();
			
			TypeDao typeDao = new TypeDao(connGateway);
			
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
					new PartsListView(partsInventory, session);

			ProductTemplates productTemplates = null;
			ProductTemplateListView productTemplatesListView = null;
			ProductTemplateListController productTemplatesListController = 
					null;
			if(session.canViewProductTemplates()){
				productTemplates = new ProductTemplates(connGateway);
				productTemplates.loadInitialProductTemplates();

				productTemplatesListView = 
						new ProductTemplateListView(productTemplates);
				productTemplates.registerView(productTemplatesListView);

				productTemplatesListController = 
						new ProductTemplateListController(productTemplates, 
								productTemplatesListView, connGateway);
				productTemplatesListView
				.registerListener(productTemplatesListController);
			}
			
			InventoryListController inventoryListController = 
					new InventoryListController(inventoryListView, inventory, 
							locations, partsInventory, session, productTemplates);

			PartsListController partsListController = 
					new PartsListController(partsInventory, unitsOfQuantity,
							partsListView, session);

			inventoryListView.registerListener(inventoryListController);
			partsListView.registerListener(partsListController);

			inventory.registerView(inventoryListView);
			partsInventory.registerView(partsListView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
