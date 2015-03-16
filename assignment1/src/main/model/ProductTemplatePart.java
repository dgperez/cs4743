package main.model;

public class ProductTemplatePart {

	private int id;
	
	private int productTemplateId;
	
	private Part part;
	
	private int partQuantity;
	
	public ProductTemplatePart(int id, Part part, int partQuantity) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((part == null) ? 0 : part.hashCode());
		result = prime * result + productTemplateId;
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
		ProductTemplatePart other = (ProductTemplatePart) obj;
		if (id != other.id)
			return false;
		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		if (productTemplateId != other.productTemplateId)
			return false;
		return true;
	}
}
