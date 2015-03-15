package main.model;

public class ProductTemplatePart {

	private int id;
	
	private int productTemplateId;
	
	private Part part;
	
	private int partQuantity;
	
	public ProductTemplatePart(int id, int productTemplateId, Part part, int partQuantity) {
		this.id = id;
		this.productTemplateId = productTemplateId;
		this.part = part;
		this.partQuantity = partQuantity;
	}

	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getProductTemplateId(){
		return this.productTemplateId;
	}
	
	public void setProductTemplateId(int productTemplateId){
		this.productTemplateId = productTemplateId;
	}
	
	public Part getPart(){
		return this.part;
	}
	
	public void setPart(Part part){
		this.part = part;
	}
	
	public int getPartQuantity(){
		return this.partQuantity;
	}
	
	public void setPartQuantity(int partQuantity){
		this.partQuantity = partQuantity;
	}
	
}
