package main.model;

import java.util.ArrayList;
import java.util.List;

public class ProductTemplate {

	private int id;
	
	private String productNumber;
	
	private String productDescription;
	
	private List<ProductTemplatePart> productTemplateParts = 
			new ArrayList<ProductTemplatePart>();
	
	private int quantity;
	
	public ProductTemplate(int id, String productNumber, 
			String productDescription, int quantity) {
		this.id = id;
		this.productNumber = productNumber;
		this.productDescription = productDescription;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
}
