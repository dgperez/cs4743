package main.model;

public class User {
	private String fullName;
	
	private String role;
	
	private String email;
	
	public User(String fullName, String email){
		this.fullName = fullName;
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setInventoryManager(Session session){
		session.setCanViewProductTemplates(false);
		session.setCanAddProductTemplates(false);
		session.setCanDeleteProductTemplates(false);
		session.setCanCreateProducts(false);
		session.setCanViewInventory(true);
		session.setCanAddInventory(true);
		session.setCanViewParts(true);
		session.setCanAddParts(true);
		session.setCanDeleteParts(false);
		session.setCanDeleteInventory(false);
		this.role = "Inventory Manager";
	}
	
	public void setProductionManager(Session session){
		session.setCanViewProductTemplates(true);
		session.setCanAddProductTemplates(true);
		session.setCanDeleteProductTemplates(true);
		session.setCanCreateProducts(true);
		session.setCanViewInventory(true);
		session.setCanAddInventory(false);
		session.setCanViewParts(true);
		session.setCanAddParts(false);
		session.setCanDeleteParts(false);
		session.setCanDeleteInventory(false);
		this.role = "Production Manager";
	}
	
	public void setAdmin(Session session){
		session.setCanViewProductTemplates(true);
		session.setCanAddProductTemplates(true);
		session.setCanDeleteProductTemplates(true);
		session.setCanCreateProducts(true);
		session.setCanViewInventory(true);
		session.setCanAddInventory(true);
		session.setCanViewParts(true);
		session.setCanAddParts(true);
		session.setCanDeleteParts(true);
		session.setCanDeleteInventory(true);
		this.role = "Admin";
	}
	
	public String getRole(){
		return role;
	}
	
	@Override
	public String toString(){
		return "user: " + this.fullName
				+ ", login: " + this.email;
	}
	
}
