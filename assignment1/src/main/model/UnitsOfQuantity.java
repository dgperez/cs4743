package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class UnitsOfQuantity {
	
	private static HashMap<Integer, String> unitsOfQuantity = new HashMap<Integer, String>();
	
	static {
		unitsOfQuantity.put(0, "Unknown");
		unitsOfQuantity.put(1, "Linear Feet");
		unitsOfQuantity.put(2, "Pieces");
	}
	
	public int getIdForQuantity(String quantity){
		for(Entry<Integer, String> id : UnitsOfQuantity.unitsOfQuantity.entrySet()){
			if(id.getValue().equals(quantity)){
				return id.getKey();
			}
		}
		return -1;
	}
	
	public String[] getUnitsOfQuantity(){
		return UnitsOfQuantity.unitsOfQuantity.values().toArray(
				new String[UnitsOfQuantity.unitsOfQuantity.size()]);
	}
	
	public void resetUnitsOfQuantity(HashMap<Integer, String> unitsOfQuantity){
		UnitsOfQuantity.unitsOfQuantity.clear();
		UnitsOfQuantity.unitsOfQuantity.putAll(unitsOfQuantity);
	}

}
