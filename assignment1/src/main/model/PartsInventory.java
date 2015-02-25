package main.model;

import java.sql.SQLException;
import java.util.ArrayList;

import main.dao.ConnectionGateway;
import main.dao.PartDao;

public class PartsInventory {

	private ArrayList<Part> allParts;
	
	private ConnectionGateway connGateway;
	
	private PartDao partDao;
	
	public PartsInventory(ConnectionGateway connGateway) {
		this.allParts = new ArrayList<Part>();
		this.connGateway = connGateway;
		this.partDao = new PartDao(this.connGateway);
	}
	
	public void loadParts() throws SQLException{
		ArrayList<Part> parts = this.partDao.getParts();
		this.addAllParts(parts);
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
