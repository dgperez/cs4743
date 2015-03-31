package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.ProductTemplateDao;
import main.view.InventoryListView;
import main.view.ProductTemplateDetailView;

public class ProductTemplateInventory {
	private List<ProductTemplate> inventory;
	
	private InventoryListView view;
	
	private boolean viewCreated = false;
	
	private ArrayList<ProductTemplateDetailView> observers = 
			new ArrayList<ProductTemplateDetailView>();
	
	private ConnectionGateway connGateway;
	
	private ProductTemplateDao productDao;
	
	public ProductTemplateInventory(ConnectionGateway connGateway){
		this.connGateway = connGateway;
		this.inventory = new ArrayList<ProductTemplate>();
		this.productDao = new ProductTemplateDao(this.connGateway);
	}
	
	public void loadInitialInventory() throws Exception{
		ArrayList<ProductTemplate> products = this.productDao.getProductTemplates();
		this.inventory = products;
	}
	
 	public List<ProductTemplate> getInventory(){
 		return this.inventory;
 	}
 	
 	public ProductTemplate getProduct(int id) throws SQLException{
 		ProductTemplate temp = this.productDao.getProductTemplate(id);
 		this.replaceProduct(temp);
 		return temp;
 	}
	
 	public ProductTemplate addProduct(ProductTemplate product) 
 			throws Exception{
 		ProductTemplate temp = this.productDao.addProductTemplate(product);
 		this.inventory.add(temp);
 		this.updateView();
 		return temp;
 	}
	
	public ProductTemplate editProduct(ProductTemplate product) throws SQLException{
		ProductTemplate temp = this.productDao.editProductTemplate(product);
		this.replaceProduct(product);
		this.updateView();
		return temp;
	}
	
	private void replaceProduct(ProductTemplate product){
		for(ProductTemplate p : this.inventory){
			if(p.getId() == product.getId()){
				p.setProductDescription(product.getProductDescription());
				p.setProductNumber(product.getProductNumber());
				p.setProductTemplateParts(product.getProductTemplateParts());
			}
		}
	}
	
	public void removeProduct(ProductTemplate product, 
			List<ProductTemplate> inventory) throws SQLException {
		inventory.remove(product);
		this.productDao.deleteProductTemplate(product);
		this.updateView();
		this.closeOpenObservers(product);
	}
	
	public void removeProduct(int index, List<ProductTemplate> inventory) 
			throws SQLException {
		ProductTemplate temp = inventory.remove(index);
		this.productDao.deleteProductTemplate(temp);
		this.updateView();
		this.closeOpenObservers(temp);
	}
	
	public void registerView(InventoryListView inventoryListView){
		this.view = inventoryListView;
		this.viewCreated = true;
	}
	
	public void registerObservers(ProductTemplateDetailView productDetailView){
		this.observers.add(productDetailView);
	}
	
	private void updateObservers(){
		for(ProductTemplateDetailView productDetailView : this.observers){
			productDetailView.refreshObserver();
		}
	}
	
	private void closeOpenObservers(ProductTemplate product){
		ArrayList<ProductTemplateDetailView> productsToRemove = 
				new ArrayList<ProductTemplateDetailView>();
		for(ProductTemplateDetailView productDetailView : this.observers){
			if(productDetailView.containsProductTemplate(product)){
				productsToRemove.add(productDetailView);
			}
		}
		
		this.observers.removeAll(productsToRemove);
		
		for(ProductTemplateDetailView removedViews : productsToRemove){
			removedViews.dispose();
		}
	}
	
	public void updateView(){
		if(viewCreated) {
			this.view.refreshList(this);
		}
		this.updateObservers();
	}

}
