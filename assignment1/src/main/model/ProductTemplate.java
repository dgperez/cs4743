package main.model;

import java.util.ArrayList;
import java.util.List;

public class ProductTemplate {

	private int id;
	
	private String productNumber;
	
	private String productDescription;
	
	private List<ProductTemplatePart> productTemplateParts = 
			new ArrayList<ProductTemplatePart>();
	
	public ProductTemplate(int id, String productNumber, 
			String productDescription) {
		this.id = id;
		this.productNumber = productNumber;
		this.productDescription = productDescription;
	}

	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getProductNumber(){
		return this.productNumber;
	}
	
	public void setProductNumber(String productNumber){
		this.productNumber = productNumber;
	}
	
	public String getProductDescription(){
		return this.productDescription;
	}
	
	public void setProductDescription(String productDescription){
		this.productDescription = productDescription;
	}
	
	public void addProductTemplatePart(
			ProductTemplatePart productTemplatePart) {
		this.productTemplateParts.add(productTemplatePart);
	}
	
	public void deleteProductTemplatePart(
			ProductTemplatePart productTemplatePart) {
		this.productTemplateParts.remove(productTemplatePart);
	}
	
	public void setProductTemplatePartsParentId(int productTemplateId){
		for(ProductTemplatePart ptp : this.productTemplateParts){
			ptp.setProductTemplateId(productTemplateId);
		}
	}
	
	public List<ProductTemplatePart> getProductTemplateParts(){
		return this.productTemplateParts;
	}
	
	public void setProductTemplateParts(
			List<ProductTemplatePart> productTemplatePart){
		this.productTemplateParts = productTemplatePart;
	}
	
	@Override
	public String toString(){
		return "id: " + this.id + ", " + 
				"prod#: " + this.productNumber + ", " + 
				"prod_desc: " + this.productDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime
				* result
				+ ((productDescription == null) ? 0 : productDescription
						.hashCode());
		result = prime * result
				+ ((productNumber == null) ? 0 : productNumber.hashCode());
		result = prime
				* result
				+ ((productTemplateParts == null) ? 0 : productTemplateParts
						.hashCode());
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
		ProductTemplate other = (ProductTemplate) obj;
		if (id != other.id)
			return false;
		if (productDescription == null) {
			if (other.productDescription != null)
				return false;
		} else if (!productDescription.equals(other.productDescription))
			return false;
		if (productNumber == null) {
			if (other.productNumber != null)
				return false;
		} else if (!productNumber.equals(other.productNumber))
			return false;
		if (productTemplateParts == null) {
			if (other.productTemplateParts != null)
				return false;
		} else if (!productTemplateParts.equals(other.productTemplateParts))
			return false;
		return true;
	}
}
