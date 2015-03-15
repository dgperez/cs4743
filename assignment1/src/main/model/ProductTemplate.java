package main.model;

import java.util.List;

public class ProductTemplate {

	private int id;
	
	private String productNumber;
	
	private String productDescription;
	
	private List<ProductTemplatePart> productTemplateParts;
	
	public ProductTemplate(int id, String productNumber, 
			String productDescription, 
			List<ProductTemplatePart> productTemplateParts) {
		this.id = id;
		this.productNumber = productNumber;
		this.productDescription = productDescription;
		this.productTemplateParts = productTemplateParts;
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
	
	public void setProductNumber(String productNumber) throws Exception {
		if(this.validateProductNumber(productNumber)){
			this.productNumber = productNumber;
		}
	}
	
	public String getProductDescription(){
		return this.productDescription;
	}
	
	public void setProductDescription(String productDescription) 
			throws Exception{
		if(this.validateProductDescription(productDescription)){
			this.productDescription = productDescription;
		}
	}
	
	public void addProductTemplatePart(
			ProductTemplatePart productTemplatePart) {
		this.productTemplateParts.add(productTemplatePart);
	}
	
	public void deleteProductTemplatePart(
			ProductTemplatePart productTemplatePart) {
		this.productTemplateParts.remove(productTemplatePart);
	}
	
	public boolean validateProductNumber(String productNumber) 
			throws Exception {
		String message = "";
		boolean isValid = true;
		if(!productNumber.startsWith("A")){
			message += "Product # must start with A.\n";
			isValid = false;
		}
		if(productNumber.length() > 20){
			message += "Product # cannot exceed 20 characters in length.\n";
			isValid = false;
		}
		if(!isValid){
			throw new Exception(message);
		}
		return isValid;
	}
	
	public boolean validateProductDescription(String productDescription) 
			throws Exception{
		String message = "";
		boolean isValid = true;
		if(productDescription.length() > 255){
			message += "Product Description cannot exceed 255 characters " +
					"in length.\n";
			isValid = false;
		}
		if(!isValid){
			throw new Exception(message);
		}
		return isValid;
	}
}
