package main.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class Locations {

	private HashMap<Integer, String> locations = 
			new HashMap<Integer, String>();
		
	public int getIdForLocation(String quantity){
		for(Entry<Integer, String> id : this.locations.entrySet()){
			if(id.getValue().equals(quantity)){
				return id.getKey();
			}
		}
		return -1;
	}
	
	public Entry<Integer, String> getEntryForLocation(String location){
		for(Entry<Integer, String> set : this.locations.entrySet()){
			if(set.getValue().equals(location)){
				return set;
			}
		}
		return null;
	}
	
	public String[] getLocations(){
		return this.locations.values().toArray(
				new String[this.locations.size()]);
	}

	public void resetLocations(HashMap<Integer, String> locations){
		this.locations.clear();
		this.locations.putAll(locations);
	}
	
	public Entry<Integer, String> getLocationById(int id){
		if(this.locations.containsKey(id)){
			for(Entry<Integer, String> set : this.locations.entrySet()){
				if(set.getKey() == id){
					return set;
				}
			}
		}
		return null;
	}
}
