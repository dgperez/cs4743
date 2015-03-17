package main.view;

import javax.swing.JFrame;

import main.model.ProductTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTemplateDetailView extends JFrame {
	
	private ProductTemplate productTemplate;
	
	private boolean newtemplate = false;

	public ProductTemplateDetailView(boolean newTemplate) {
		this.newtemplate = newTemplate;
	}

	public void refreshObserver(){
		throw new NotImplementedException();
	}
	
	public boolean containsProductTemplate(
			ProductTemplate productTemplate){
		return this.productTemplate.getId() == productTemplate.getId();
	}
	
	public void registerListener(){}
}
