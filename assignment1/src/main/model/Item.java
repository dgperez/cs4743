package main.model;

public class Item {
	private String partNumber;
	private String partName;
	private String vendor;
	private int quantity;
	private boolean toEdit = false;
	
	public Item(String partNumber, String partName, String vendor,
			int quantity) {
		super();
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPartNumber() {
		return partNumber;
	}
	
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}
	
	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getVendor() {
		return vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public void setEditPart(boolean editPart){
		this.toEdit = editPart;
	}
	
	public boolean canEditPart(){
		return this.toEdit;
	}
	
	@Override
	public String toString(){
		return "Part Name: " + this.partName + ", " +
				"Part#: " + this.partNumber + ", " +
				((!this.vendor.isEmpty()) ? "Vendor: " + this.vendor + ", " : "")  
				+ "Quantity: " + this.quantity;
	}

}