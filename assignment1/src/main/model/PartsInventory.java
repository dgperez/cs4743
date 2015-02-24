package main.model;

import java.util.ArrayList;

import main.dao.ConnectionGateway;

public class PartsInventory {

	private ArrayList<Part> allParts;
	
	private ConnectionGateway connGateway;
	
	public PartsInventory(ConnectionGateway connGateway) {
		this.allParts = new ArrayList<Part>();
		this.connGateway = connGateway;
	}

	public void addPart(Part part){
		if(!this.allParts.contains(part)){
			this.allParts.add(part);
		}
	}
	
	public void addAllParts(ArrayList<Part> parts){
		this.allParts.clear();
		this.allParts = parts;
	}
	
	public Part getPartBy_Id(int id){
		for(Part part: this.allParts){
			if(part.getId() == id){
				return part;
			}
		}
		return null;
	}
	
	public Part getPartBy_PartNumber(String partNumber){
		for(Part part: this.allParts){
			if(part.getPartNumber().equals(partNumber)){
				return part;
			}
		}
		return null;
	}
	
	public boolean validatePartNumber(String partNumber){
		for(Part part: this.allParts){
			if(part.getPartNumber().equals(partNumber)){
				return false;
			}
		}
		return true;
	}
	
}
