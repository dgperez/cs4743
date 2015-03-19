package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class UnitsOfQuantity {
	
	private static HashMap<Integer, String> unitsOfQuantity = 
			new HashMap<Integer, String>();
	
	public Entry<Integer, String> getEntryForQuantity(String quantity){
		for(Entry<Integer, String> id : 
				UnitsOfQuantity.unitsOfQuantity.entrySet()){
			if(id.getValue().equals(quantity)){
				return id;
			}
		}
		return null;
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
