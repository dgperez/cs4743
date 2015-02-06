package test;

import static org.junit.Assert.*;
import main.model.Inventory;
import main.model.Item;
import main.model.Item.UnitOfQuantity;

import org.junit.Test;

public class TestModel {

	@Test
	public void createItemTest1() {
		Item i = new Item("1", "partNameGoesHere", "", 10, 
				UnitOfQuantity.PIECES);
		assertTrue(i.getPartName().equals("partNameGoesHere") && 
		   i.getVendor().equals("") && 
		   i.getQuantity() == 10 &&
		   i.getUnitOfQuantity().equals(UnitOfQuantity.PIECES));
	}
	
	@Test
	public void createItemTest2() {
		Item i = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		assertTrue(i.getPartName().equals("partNameGoesHere") && 
		   i.getVendor().equals("vendorNameGoesHere") && 
		   i.getQuantity() == 10 && 
		   i.getUnitOfQuantity().equals(UnitOfQuantity.PIECES));
	}
	
	

	@Test
	public void editpartNumberTest() {
		Item i = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		i.setPartNumber("2");
		assertTrue(i.getPartNumber().equals("2"));
	}

	@Test
	public void editpartNameTest() {
		Item i = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		i.setPartName("newPartName");
		assertTrue(i.getPartName().equals("newPartName"));
	}

	@Test
	public void editVendorTest() {
		Item i = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		i.setVendor("newVendor");
		assertTrue(i.getVendor().equals("newVendor"));
	}

	@Test
	public void editQuantityTest() {
		Item i = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		i.setQuantity(1);
		assertTrue(i.getQuantity() == 1);
	}

	@Test
	public void createInventoryTest() {
		Inventory i = new Inventory();
		i.createInventory();
		assertEquals(0, i.getInventory().lastIndexOf(i)+1);
	}

	@Test
	public void addItemToInventoryTest() {
		Inventory i = new Inventory();
		i.createInventory();
		Item o = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		try {
			i.addItem(o, i.getInventory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(i.getInventory().contains(o));
	}

	@Test
	public void removeItemFromInventoryTest() {
		Inventory i = new Inventory();
		i.createInventory();
		Item o = new Item("1", "partNameGoesHere", "vendorNameGoesHere", 10,
				UnitOfQuantity.PIECES);
		try {
			i.addItem(o, i.getInventory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(i.getInventory().contains(o));
	}

	
	
}
