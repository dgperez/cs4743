package main.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.PartDao;
import main.dao.ProductTemplatePartsDao;
import main.view.ProductTemplatePartsDetailView;
import main.view.ProductTemplatePartsListView;

public class ProductTemplateParts {

	private ConnectionGateway connGateway;
	
	private ProductTemplate productTemplate;
	
	private ProductTemplatePartsDao productTemplatesPartsDao;
	
	private ProductTemplatePartsListView productTemplatePartsListView;
	
	private PartDao partDao;
	
	private boolean viewCreated = false;
	
	private ArrayList<ProductTemplatePartsDetailView> observers = 
			new ArrayList<ProductTemplatePartsDetailView>();
	
	public ProductTemplateParts(ConnectionGateway connGateway, 
			ProductTemplate productTemplate) {
		this.connGateway = connGateway;
		this.productTemplate = productTemplate;	
		this.productTemplatesPartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		this.partDao = new PartDao(this.connGateway);
	}
	
	public List<ProductTemplatePart> getProductTemplateParts(){
		return this.productTemplate.getProductTemplateParts();
	}
	
	public ProductTemplatePart addProductTemplatePart(
			ProductTemplatePart productTemplatePart) 
			throws SQLException{
			ProductTemplatePart tempTemplatePart = 
					this.productTemplatesPartsDao
						.addProductTemplatePart(productTemplatePart);
			this.productTemplate.getProductTemplateParts()
				.add(tempTemplatePart);
			this.updateViews();
			return tempTemplatePart;
	}
	
	public void removeProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		this.productTemplate.getProductTemplateParts()
			.remove(productTemplatePart);
		this.productTemplatesPartsDao
			.deleteProductTemplatePart(productTemplatePart);
		this.updateViews();
		this.closeOpenObservers(productTemplatePart);
	}
	
	public void editProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		this.productTemplatesPartsDao
			.editProductTemplatePart(productTemplatePart);
		for(ProductTemplatePart ptp : 
			this.productTemplate.getProductTemplateParts()){
			if(ptp.getId() == productTemplatePart.getId()){
				ptp.setPart(productTemplatePart.getPart());
				ptp.setPartQuantity(productTemplatePart.getPartQuantity());
				ptp.setProductTemplateId(
						productTemplatePart.getProductTemplateId());
			}
		}
		this.updateViews();
	}
	
	public List<Part> getAvailableParts() throws SQLException{
		return this.partDao.getParts();
	}
	
	public int getProductTemplateId(){
		return this.productTemplate.getId();
	}

	public void registerView(
			ProductTemplatePartsListView productTemplatePartsListView){
		this.productTemplatePartsListView = productTemplatePartsListView;
		this.viewCreated = true;
	}
	
	public void registerObservers(
			ProductTemplatePartsDetailView productTemplatePartsDetailView){
		this.observers.add(productTemplatePartsDetailView);
	}
	
	public void updateObservers(){
		for(ProductTemplatePartsDetailView ptdv : this.observers){
			ptdv.refreshObserver();
		}
	}
	
	public void closeOpenObservers(ProductTemplatePart productTemplatePart){
		ArrayList<ProductTemplatePartsDetailView> templatesToRemove = 
				new ArrayList<ProductTemplatePartsDetailView>();
		for(ProductTemplatePartsDetailView ptdv : this.observers){
			if(ptdv.containsProductTemplatePart(productTemplatePart)){
				templatesToRemove.add(ptdv);
			}
		}
		
		this.observers.removeAll(templatesToRemove);
		
		for(ProductTemplatePartsDetailView removedViews : templatesToRemove){
			removedViews.dispose();
		}
	}
	
	public void updateViews(){
		if(this.viewCreated){
			this.productTemplatePartsListView.refreshList(this);
		}
		this.updateObservers();
	}
	
	public boolean validateTemplatePart(
			ProductTemplatePart productTemplatePart) throws Exception{
		String message = "";
		boolean isValid = true;
		if(productTemplatePart.getPartQuantity() < 1){
			message += "Part quantity must be greater than zero.\n";
			isValid = false;
		}
		if(!isValid){
			throw new Exception(message);
		}
		return isValid;
	}
}
