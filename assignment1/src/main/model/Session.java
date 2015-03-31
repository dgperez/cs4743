package main.model;

public class Session {
	private User user;
	
	private boolean canViewProductTemplates;
	
	private boolean canAddProductTemplates;
	
	private boolean canDeleteProductTemplates;
	
	private boolean canCreateProducts;
	
	private boolean canViewInventory;
	
	private boolean canAddInventory;
	
	private boolean canViewParts;
	
	private boolean canAddParts;
	
	private boolean canDeleteParts;
	
	private boolean canDeleteInventory;
	
	public Session(User user){
		this.user = user;
	}
	
	public Session(User user, boolean canViewProductTemplates,
			boolean canAddProductTemplates, boolean canDeleteProductTemplates,
			boolean canCreateProducts, boolean canViewInventory,
			boolean canAddInventory, boolean canViewParts, boolean canAddParts,
			boolean canDeleteParts, boolean canDeleteInventory) {
		this.user = user;
		this.canViewProductTemplates = canViewProductTemplates;
		this.canAddProductTemplates = canAddProductTemplates;
		this.canDeleteProductTemplates = canDeleteProductTemplates;
		this.canCreateProducts = canCreateProducts;
		this.canViewInventory = canViewInventory;
		this.canAddInventory = canAddInventory;
		this.canViewParts = canViewParts;
		this.canAddParts = canAddParts;
		this.canDeleteParts = canDeleteParts;
		this.canDeleteInventory = canDeleteInventory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean canViewProductTemplates() {
		return canViewProductTemplates;
	}

	public void setCanViewProductTemplates(boolean canViewProductTemplates) {
		this.canViewProductTemplates = canViewProductTemplates;
	}

	public boolean canAddProductTemplates() {
		return canAddProductTemplates;
	}

	public void setCanAddProductTemplates(boolean canAddProductTemplates) {
		this.canAddProductTemplates = canAddProductTemplates;
	}

	public boolean canDeleteProductTemplates() {
		return canDeleteProductTemplates;
	}

	public void setCanDeleteProductTemplates(boolean canDeleteProductTemplates) {
		this.canDeleteProductTemplates = canDeleteProductTemplates;
	}

	public boolean canCreateProducts() {
		return canCreateProducts;
	}

	public void setCanCreateProducts(boolean canCreateProducts) {
		this.canCreateProducts = canCreateProducts;
	}

	public boolean canViewInventory() {
		return canViewInventory;
	}

	public void setCanViewInventory(boolean canViewInventory) {
		this.canViewInventory = canViewInventory;
	}

	public boolean canAddInventory() {
		return canAddInventory;
	}

	public void setCanAddInventory(boolean canAddInventory) {
		this.canAddInventory = canAddInventory;
	}

	public boolean canViewParts() {
		return canViewParts;
	}

	public void setCanViewParts(boolean canViewParts) {
		this.canViewParts = canViewParts;
	}

	public boolean canAddParts() {
		return canAddParts;
	}

	public void setCanAddParts(boolean canAddParts) {
		this.canAddParts = canAddParts;
	}

	public boolean canDeleteParts() {
		return canDeleteParts;
	}

	public void setCanDeleteParts(boolean canDeleteParts) {
		this.canDeleteParts = canDeleteParts;
	}

	public boolean canDeleteInventory() {
		return canDeleteInventory;
	}

	public void setCanDeleteInventory(boolean canDeleteInventory) {
		this.canDeleteInventory = canDeleteInventory;
	}
}
