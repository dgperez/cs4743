package main.model;

import java.util.ArrayList;

public class PartsInventory {

	private ArrayList<Part> allParts;
	
	public PartsInventory() {
		this.allParts = new ArrayList<Part>();
	}

	public void addPart(Part part){
		if(!this.allParts.contains(part)){
			this.allParts.add(part);
		}
	}
	
}
