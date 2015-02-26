package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import main.dao.ConnectionGateway;
import main.dao.PartDao;
import main.view.PartsDetailView;

public class PartsInventory {

	private ArrayList<Part> allParts;
	
	private ConnectionGateway connGateway;
	
	private PartDao partDao;
	
	private ArrayList<PartsDetailView> observers = 
			new ArrayList<PartsDetailView>();
	
	public PartsInventory(ConnectionGateway connGateway) {
		this.allParts = new ArrayList<Part>();
		this.connGateway = connGateway;
		this.partDao = new PartDao(this.connGateway);
	}
	
	public void loadParts() throws SQLException{
		ArrayList<Part> parts = this.partDao.getParts();
		this.replaceAllParts(parts);
	}

	public void addPart(Part part){
		if(!this.allParts.contains(part)){
			this.allParts.add(part);
		}
	}
	
	public void replaceAllParts(ArrayList<Part> parts){
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
	
	public ArrayList<Part> getAllParts(){
		return this.allParts;
	}
	
	public String[] getAllPartsToString(){
		String[] temp = new String[allParts.size()];
		int i = 0;
		for(Part p: allParts){
			temp[i++] = p.toString();
		}
		return temp;
	}
	
	public boolean validatePartNumber(String partNumber){
		for(Part part: this.allParts){
			if(part.getPartNumber().equals(partNumber)){
				return false;
			}
		}
		return true;
	}
	
	public void removePart(Part part) throws SQLException{
		try{
			this.partDao.deletePart(part);
		} catch(SQLException e){
			throw e;
		}
		this.allParts.remove(part);
	}
	
	public boolean validateSavedPart(Part part) throws Exception{
		boolean valid = true;
		String message = "";
		if(part.getPartNumber().length() <= 0 
				|| part.getPartNumber().length() > 20){
			valid = false;
			message += "Part Number must be between 0 and 20 characters long.\n";
		}
		if(part.getPartName().length() <= 0 
				|| part.getPartName().length() > 255){
			valid = false;
			message += "Part Name must be between 0 and 255 characters long.\n";
		}
		if(part.getVendor().length() > 255){
			valid = false;
			message += "Vendor must be between 0 and 255 characters long.\n";
		}
		if(part.getUnitOfQuantity().getValue().equals("Unknown")){
			valid = false;
			message += "Quantity cannot be 'Unknown'.\n";
		}
		if(!part.getExternalPartNumber().isEmpty() && 
				part.getExternalPartNumber().length() > 50){
			valid = false;
			message += "External Part Number must be between 0 and 50 characters long.\n";
		}
		if(!valid){
			throw new Exception(message);
		}
		return valid;
	}
	
}
