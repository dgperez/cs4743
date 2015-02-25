package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class Locations {

	private static HashMap<Integer, String> locations = new HashMap<Integer, String>();
	
	static {
		locations.put(0, "Unknown");
		locations.put(1, "Facility 1 Warehouse 1");
		locations.put(2, "Facility 1 Warehouse 2");
		locations.put(3, "Facility 2");
	}
	
	public int getIdForLocation(String quantity){
		for(Entry<Integer, String> id : Locations.locations.entrySet()){
			if(id.getValue().equals(quantity)){
				return id.getKey();
			}
		}
		return -1;
	}
	
	public String[] getLocations(){
		return Locations.locations.values().toArray(
				new String[Locations.locations.size()]);
	}

}
