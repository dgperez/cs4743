package main.model;

import java.util.ArrayList;
import java.util.List;

import main.dao.ConnectionGateway;
import main.dao.ProductTemplatePartsDao;
import main.view.ProductTemplatePartsDetailView;
import main.view.ProductTemplatePartsListView;

public class ProductTemplateParts {

	private ConnectionGateway connGateway;
	
	private ProductTemplate productTemplate;
	
	private ProductTemplatePartsDao productTemplatesPartsDao;
	
	private ProductTemplatePartsListView productTemplatePartsListView;
	
	boolean viewCreated = false;
	
	private ArrayList<ProductTemplatePartsDetailView> observers = 
			new ArrayList<ProductTemplatePartsDetailView>();
	
	public ProductTemplateParts(ConnectionGateway connGateway, 
			ProductTemplate productTemplate) {
		this.connGateway = connGateway;
		this.productTemplatesPartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
	}
	
	public List<ProductTemplatePart> getProductTemplateParts(){
		return this.productTemplate.getProductTemplateParts();
	}
	
	

}
