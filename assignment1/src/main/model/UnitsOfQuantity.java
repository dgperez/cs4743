package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class UnitsOfQuantity {
	
	private HashMap<Integer, String> unitsOfQuantity;
	
	public UnitsOfQuantity() {
		this.unitsOfQuantity = new HashMap<Integer, String>();
	}
	
	public void addUnitOfQuantity(int id, String location){
		this.unitsOfQuantity.put(id, location);
	}
	
	public void setQuantities(HashMap<Integer, String> quantities){
		this.unitsOfQuantity.clear();
		this.unitsOfQuantity = quantities;
	}
	
	public int getIdForQuantity(String quantity){
		for(Entry<Integer, String> id : this.unitsOfQuantity.entrySet()){
			if(id.getValue().equals(quantity)){
				return id.getKey();
			}
		}
		return -1;
	}
	
	public String[] getUnitsOfQuantity(){
		return this.unitsOfQuantity.values().toArray(
				new String[this.unitsOfQuantity.size()]);
	}

}
