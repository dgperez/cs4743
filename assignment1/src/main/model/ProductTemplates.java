package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.ProductTemplateDao;
import main.dao.ProductTemplatePartsDao;
import main.view.ProductTemplateDetailView;
import main.view.ProductTemplatesView;

public class ProductTemplates {

	private ConnectionGateway connGateway;
	
	private ProductTemplateDao productTemplateDao;
	
	private ProductTemplatePartsDao productTemplatePartsDao;
	
	private List<ProductTemplate> productTemplates;
	
	private boolean viewCreated = false;
	
	private ProductTemplatesView productTemplatesView;
	
	private ArrayList<ProductTemplateDetailView> observers = 
			new ArrayList<ProductTemplateDetailView>();
	
	public ProductTemplates(ConnectionGateway connGateway) {
		this.connGateway = connGateway;
		this.productTemplateDao = new ProductTemplateDao(this.connGateway);
		this.productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
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
	
	public void addProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		ProductTemplate tempTemplate = 
				this.productTemplateDao.addProductTemplate(productTemplate);
		this.productTemplates.add(tempTemplate);
	}
	
	public void deleteProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		if(this.productTemplates.contains(productTemplate)){
			this.productTemplateDao.deleteProductTemplate(productTemplate);
			this.productTemplates.remove(productTemplate);
		}
	}

	public void registerView(ProductTemplatesView productTemplatesView){
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
	
	public void closeOpenObservers(ProductTemplatePart productTemplatePart){
		ArrayList<ProductTemplateDetailView> templatesToRemove = 
				new ArrayList<ProductTemplateDetailView>();
		for(ProductTemplateDetailView ptdv : this.observers){
			if(ptdv.containsProductTemplatePart(productTemplatePart)){
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
	
}
