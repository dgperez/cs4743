package main.model;

import java.util.Map.Entry;

public class Item {
	private int id;
	private int quantity;
	private boolean toEdit = false;
	
	private Part part;

	private Entry<Integer, String> location;
	
	public Item(int id, Part part, int quantity, 
			Entry<Integer, String> location) {
		this.id = id;
		this.part = part;
		this.quantity = quantity;
		this.location = location;
	}
	
	public Entry<Integer, String> getLocation(){
		return this.location;
	}
	
	public void setLocation(Entry<Integer, String> location){
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Part getPart(){
		return this.part;
	}
	
	public void setEditPart(boolean editPart){
		this.toEdit = editPart;
	}

	public boolean canEditPart(){
		return this.toEdit;
	}
	
	@Override
	public String toString(){
		return "id: " + this.getId() + ", " +
				"part#: " + this.getPart().getPartNumber() + ", " +
				"quantity: " + this.quantity + ", " +
				"location: " + this.getLocation().getValue();
	}
}