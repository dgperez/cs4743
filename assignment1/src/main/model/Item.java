package main.model;

import java.sql.Timestamp;
import java.util.Map.Entry;

public class Item {
	private int id;
	private int quantity;
	
	private Part part;
	
	private ProductTemplate productTemplate;

	private Entry<Integer, String> location;
	
	private Timestamp lastModified;
	
	public Item(int id, Part part, int quantity, 
			Entry<Integer, String> location) {
		this.id = id;
		this.part = part;
		this.quantity = quantity;
		this.location = location;
	}
	
	public Item(int id, ProductTemplate productTemplate, int quantity, 
			Entry<Integer, String> location) {
		this.id = id;
		this.productTemplate = productTemplate;
		this.quantity = quantity;
		this.location = location;
	}
	
	public Entry<Integer, String> getLocation(){
		return this.location;
	}
	
	public void setLocation(Entry<Integer, String> location){
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Part getPart(){
		return this.part;
	}
	
	public void setPart(Part part){
		this.part = part;
	}
	
	public ProductTemplate getProductTemplate(){
		return this.productTemplate;
	}
	
	public void setProductTemplate(ProductTemplate productTemplate){
		this.productTemplate = productTemplate;
	}
	
	public Timestamp getLastModified(){
		return this.lastModified;
	}
	
	public void setLastModified(Timestamp lastModified){
		this.lastModified = lastModified;
	}
	
	@Override
	public String toString(){
		String temp = (this.part != null) ?
				"part#: " + this.getPart().getPartNumber() + ", " 
				: "product#: " + 
					this.getProductTemplate().getProductNumber() + ", ";
		return "id: " + this.getId() + ", " +
				temp
				+ "quantity: " + this.quantity + ", " +
				"location: " + this.getLocation().getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((part == null) ? 0 : part.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		if (productTemplate == null) {
			if (other.productTemplate != null)
				return false;
		} else if (!productTemplate.equals(other.productTemplate))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
	
}