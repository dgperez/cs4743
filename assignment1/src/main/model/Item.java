package main.model;

public class Item {
	private int id;
	private int quantity;
	private boolean toEdit = false;
	
	private Part part;

	private String location;
	
	public Item(Part part, int quantity, String location) {
		this.part = part;
		this.quantity = quantity;
		this.location = location;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public int getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
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
		return null;
	}
}