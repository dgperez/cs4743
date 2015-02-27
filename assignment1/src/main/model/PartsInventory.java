package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import main.dao.ConnectionGateway;
import main.dao.PartDao;
import main.view.ItemDetailView;
import main.view.PartsDetailView;
import main.view.PartsListView;

public class PartsInventory implements IModel {

	private ArrayList<Part> allParts;
	
	private ConnectionGateway connGateway;
	
	private PartDao partDao;
	
	private PartsListView partsListView; 
	
	private ArrayList<PartsDetailView> observers = 
			new ArrayList<PartsDetailView>();
	
	private boolean viewRegistered;
	
	public PartsInventory(ConnectionGateway connGateway) {
		this.allParts = new ArrayList<Part>();
		this.connGateway = connGateway;
		this.partDao = new PartDao(this.connGateway);
	}
	
	public void loadParts() throws SQLException{
		ArrayList<Part> parts = this.partDao.getParts();
		this.replaceAllParts(parts);
	}

	public Part addPart(Part part) throws SQLException{
		if(!this.allParts.contains(part)){
			Part tempPart = this.partDao.addPart(part);
			this.allParts.add(tempPart);
			return tempPart;
		}
		return null;
	}
	
	public void editPart(Part part) throws SQLException{
		this.partDao.editPart(part);
		for(Part p : this.allParts){
			if(p.getId() == part.getId()){
				p.setExternalPartNumber(part.getExternalPartNumber());
				p.setPartName(part.getPartName());
				p.setPartNumber(part.getPartNumber());
				p.setVendor(part.getVendor());
				p.setUnitOfQuantity(part.getUnitOfQuantity());
			}
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
			this.allParts.remove(part);
			this.partDao.deletePart(part);
			this.updateView();
			this.closeOpenObservers(part);
		} catch(SQLException e){
			throw e;
		}
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
		if(!this.validatePartNumber(part.getPartNumber())){
			valid = false;
			message += "A part with that number already exists.";
		}
		if(!valid){
			throw new Exception(message);
		}
		return valid;
	}
	
	public void registerView(PartsListView partsListView){
		this.partsListView = partsListView;
		this.viewRegistered = true;
	}
	
	public void registerObservers(PartsDetailView partsDetailView){
		this.observers.add(partsDetailView);
	}
	
	public void updateView(){
		if(this.viewRegistered) {
			this.partsListView.refreshList(this);
		}
		this.updateObservers();
	}
	
	private void updateObservers(){
		for(PartsDetailView partsDetailView : this.observers){
			partsDetailView.refreshObserver();
		}
	}
	
	private void closeOpenObservers(Part part){
		ArrayList<PartsDetailView> itemsToRemove = 
				new ArrayList<PartsDetailView>();
		for(PartsDetailView partsDetailView : this.observers){
			if(partsDetailView.containsItem(part)){
				itemsToRemove.add(partsDetailView);
			}
		}
		
		this.observers.removeAll(itemsToRemove);
		
		for(PartsDetailView partsDetailView : itemsToRemove){
			partsDetailView.dispose();
		}
	}
}
