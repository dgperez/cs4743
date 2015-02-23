package main.model;

public class Part {
	private static int memberNumber = 1;
	private int id;
	private String partNumber;
	private String partName;
	private String vendor;
	private int quantity;
	private boolean toEdit = false;
	private String externalPartNumber;
		
	private UnitOfQuantity currentUnit;
	
	public Part(String partNumber, String partName, String vendor,
			int quantity, UnitOfQuantity unit, 
			String externalPartNumber) {
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.quantity = quantity;
		this.currentUnit = unit;
		this.setExternalPartNumber(externalPartNumber);
	}
	
	public Part.UnitOfQuantity getUnitOfQuantity(){
		return this.currentUnit;
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
	public String toString(){
		return "Id: " + this.id + ", Part Name: " + this.partName + ", " +
				"Part#: " + this.partNumber + ", " +
				((!this.vendor.isEmpty()) 
						? "Vendor: " + this.vendor + ", " : "")  
				+ "Quantity: " + this.quantity
				+ " " + this.currentUnit
				+ ((!this.externalPartNumber.isEmpty()) 
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
}