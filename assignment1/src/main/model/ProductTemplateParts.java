package main.model;

import java.sql.SQLException;
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
	
	public void addProductTemplatePart(ProductTemplatePart productTemplatePart) 
			throws SQLException{
		if(!this.productTemplate.getProductTemplateParts()
				.contains(productTemplatePart)){
			this.productTemplatesPartsDao
				.addProductTemplatePart(productTemplatePart);
			this.productTemplate.getProductTemplateParts()
				.add(productTemplatePart);
		}
	}
	
	public void removeProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		this.productTemplate.getProductTemplateParts()
			.remove(productTemplatePart);
		this.productTemplatesPartsDao
			.deleteProductTemplatePart(productTemplatePart);
	}
	
	public void editProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		for(ProductTemplatePart ptp : 
			this.productTemplate.getProductTemplateParts()){
			if(ptp.getId() == productTemplatePart.getId()){
				ptp.setPart(productTemplatePart.getPart());
				ptp.setPartQuantity(productTemplatePart.getPartQuantity());
				ptp.setProductTemplateId(
						productTemplatePart.getProductTemplateId());
			}
		}
		this.productTemplatesPartsDao
			.editProductTemplatePart(productTemplatePart);
	}

}
