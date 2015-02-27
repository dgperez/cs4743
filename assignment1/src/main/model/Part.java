package main.model;

import java.util.Map.Entry;

public class Part {
	private int id;
	private String partNumber;
	private String partName;
	private String vendor;
	private boolean toEdit = false;
	private String externalPartNumber;
		
	private Entry<Integer, String> currentUnit;
	
	public Part(int id, String partNumber, String partName, String vendor,
			Entry<Integer, String> unitOfQuantity, String externalPartNumber) {
		this.id = id;
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.currentUnit = unitOfQuantity;
		this.setExternalPartNumber(externalPartNumber);
	}
	
	public Entry<Integer, String> getUnitOfQuantity(){
		return this.currentUnit;
	}
	
	public void setUnitOfQuantity(Entry<Integer, String> unitOfQuantity){
		this.currentUnit = unitOfQuantity;
	}
	
	public int getId() {
		return id;
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
	
	public String getExternalPartNumber() {
		return externalPartNumber;
	}

	public void setExternalPartNumber(String externalPartNumber) {
		this.externalPartNumber = externalPartNumber;
	}

	public boolean canEditPart(){
		return this.toEdit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Part other = (Part) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "Id: " + this.id + ", Part Name: " + this.partName + ", " +
				"Part#: " + this.partNumber + ", " +
				((this.vendor != null) 
						? "Vendor: " + this.vendor + ", " : "")  
				+ " " + this.currentUnit.getValue()
				+ ((!this.externalPartNumber.isEmpty()) 
					? ", External Part Number: " + this.externalPartNumber: "");
	}
}