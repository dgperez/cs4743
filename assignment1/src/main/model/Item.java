package main.model;

public class Item {
	private static int memberNumber = 1;
	private int id;
	private String partNumber;
	private String partName;
	private String vendor;
	private int quantity;
	private boolean toEdit = false;
	private String externalPartNumber;
		
	private UnitOfQuantity currentUnit;

	private Location location;
	
	public Item(String partNumber, String partName, String vendor,
			int quantity, UnitOfQuantity unit, Location location, 
			String externalPartNumber) {
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.quantity = quantity;
		this.currentUnit = unit;
		this.location = location;
		this.setExternalPartNumber(externalPartNumber);
	}
	
	public Item.Location getLocation(){
		return this.location;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Item.UnitOfQuantity getUnitOfQuantity(){
		return this.currentUnit;
	}
	
	public void setUnitOfQuantity(Item.UnitOfQuantity unit){
		this.currentUnit = unit;
	}

	public void incrementId(){
		this.id = Item.memberNumber++;
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
		result = prime * result
				+ ((partName == null) ? 0 : partName.hashCode());
		result = prime * result
				+ ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + quantity;
		result = prime * result + (toEdit ? 1231 : 1237);
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		Item other = (Item) obj;
		if (partName == null) {
			if (other.partName != null)
				return false;
		} else if (!partName.equals(other.partName))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (quantity != other.quantity)
			return false;
		if (toEdit != other.toEdit)
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "Id: " + this.id + ", Part Name: " + this.partName + ", " +
				"Part#: " + this.partNumber + ", " +
				((!this.vendor.isEmpty()) 
						? "Vendor: " + this.vendor + ", " : "")  
				+ "Quantity: " + this.quantity
				+ " " + this.currentUnit
				+ ", Location: " + this.location +
			((!this.externalPartNumber.isEmpty()) 
					? ", External Part Number: " + this.externalPartNumber: "");
	}

	public enum UnitOfQuantity{
		UNKNOWN("Unknown"),
		LINEAR_FEET("Linear Feet"),
		PIECES("Pieces");
		
		private final String unit;
		
		UnitOfQuantity(String unit){
			this.unit = unit;
		}
		
		public static String[] getUnitValues(){
			UnitOfQuantity[] units = UnitOfQuantity.values();
			String[] tempArray = new String[units.length];
			for(int i = 0; i < units.length; i++){
				tempArray[i] = units[i].toString();
			}
			return tempArray;
		}
		
		@Override
		public String toString(){
			return this.unit;
		}
	}
	
	public enum Location {
		UNKNOWN("Unknown"),
		FACILITY_1_WAREHOUSE_1("Facility 1 Warehouse 1"),
		FACILITY_1_WAREHOUSE_2("Facility 1 Warehouse 2"),
		FACILITY_2("Facility 2");
		
		private final String location;
		
		Location(String location){
			this.location = location;
		}
		
		public static String[] getLocationValues(){
			Location[] locations = Location.values();
			String[] tempArray = new String[locations.length];
			for(int i = 0; i < locations.length; i++){
				tempArray[i] = locations[i].toString();
			}
			return tempArray;
		}
		
		@Override
		public String toString(){
			return this.location;
		}
	}
}