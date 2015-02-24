package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class Locations {

	private HashMap<Integer, String> locations;
	
	public Locations() {
		this.locations = new HashMap<Integer, String>();
	}
	
	public void addLocation(int id, String location){
		this.locations.put(id, location);
	}
	
	public void setLocations(HashMap<Integer, String> quantities){
		this.locations.clear();
		this.locations = quantities;
	}
	
	public int getIdForLocation(String quantity){
		for(Entry<Integer, String> id : this.locations.entrySet()){
			if(id.getValue().equals(quantity)){
				return id.getKey();
			}
		}
		return -1;
	}
	
	public String[] getLocations(){
		return this.locations.values().toArray(
				new String[this.locations.size()]);
	}

}
