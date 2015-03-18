package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.ProductTemplateDao;
import main.view.ProductTemplateDetailView;
import main.view.ProductTemplateListView;

public class ProductTemplates {

	private ConnectionGateway connGateway;
	
	private ProductTemplateDao productTemplateDao;
	
	private List<ProductTemplate> productTemplates;
	
	private boolean viewCreated = false;
	
	private ProductTemplateListView productTemplatesView;
	
	private ArrayList<ProductTemplateDetailView> observers = 
			new ArrayList<ProductTemplateDetailView>();
	
	public ProductTemplates(ConnectionGateway connGateway) {
		this.connGateway = connGateway;
		this.productTemplateDao = new ProductTemplateDao(this.connGateway);
	}
	
	public void loadInitialProductTemplates() throws SQLException{
		ArrayList<ProductTemplate> templates = 
				this.productTemplateDao.getProductTemplates();
		this.productTemplates = templates;
	}
	
	public List<ProductTemplate> getProductTemplates(){
		return this.productTemplates;
	}
	
	public void setProductTemplates(List<ProductTemplate> productTemplates){
		this.productTemplates = productTemplates;
	}
	
	public ProductTemplate addProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		ProductTemplate tempTemplate = 
				this.productTemplateDao.addProductTemplate(productTemplate);
		this.productTemplates.add(tempTemplate);
		this.updateViews();
		return tempTemplate;
	}
	
	public void editProductTemplate(ProductTemplate productTemplate) 
			throws Exception{
		for(ProductTemplate pt : this.productTemplates){
			if(pt.getId() == productTemplate.getId()){
				pt.setProductNumber(productTemplate.getProductNumber());
				pt.setProductDescription(
						productTemplate.getProductDescription());
			}
			this.productTemplateDao.editProductTemplate(productTemplate);
		}
		this.updateViews();
	}
	
	public void deleteProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		if(this.productTemplates.contains(productTemplate)){
			this.productTemplateDao.deleteProductTemplate(productTemplate);
			this.productTemplates.remove(productTemplate);
			this.updateViews();
			this.closeOpenObservers(productTemplate);
		}
	}

	public void registerView(ProductTemplateListView productTemplatesView){
		this.productTemplatesView = productTemplatesView;
		this.viewCreated = true;
	}
	
	public void registerObservers(
			ProductTemplateDetailView productTemplateDetailView){
		this.observers.add(productTemplateDetailView);
	}
	
	public void updateObservers(){
		for(ProductTemplateDetailView ptdv : this.observers){
			ptdv.refreshObserver();
		}
	}
	
	public void closeOpenObservers(ProductTemplate productTemplate){
		ArrayList<ProductTemplateDetailView> templatesToRemove = 
				new ArrayList<ProductTemplateDetailView>();
		for(ProductTemplateDetailView ptdv : this.observers){
			if(ptdv.containsProductTemplate(productTemplate)){
				templatesToRemove.add(ptdv);
			}
		}
		
		this.observers.removeAll(templatesToRemove);
		
		for(ProductTemplateDetailView removedViews : templatesToRemove){
			removedViews.dispose();
		}
	}
	
	public void updateViews(){
		if(this.viewCreated){
			this.productTemplatesView.refreshList(this);
		}
		this.updateObservers();
	}
	
	public boolean validateProductTemplate(ProductTemplate productTemplate) 
			throws Exception{
		String message = "";
		boolean isValid = true;
		if(!productTemplate.getProductNumber().startsWith("A")){
			message += "Product # must start with A.\n";
			isValid = false;
		}
		if(productTemplate.getProductNumber().length() > 20){
			message += "Product # cannot exceed 20 characters in length.\n";
			isValid = false;
		} else if (productTemplate.getProductNumber().length() < 1){
			message += "Product # must be entered.\n";
			isValid = false;
		}
		
		if(productTemplate.getProductDescription().length() > 255){
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
