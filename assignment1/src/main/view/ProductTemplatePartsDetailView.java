package main.view;

import javax.swing.JFrame;

import main.model.ProductTemplatePart;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTemplatePartsDetailView extends JFrame {

	private ProductTemplatePart productTemplatePart;
	
	public ProductTemplatePartsDetailView() {
		// TODO Auto-generated constructor stub
	}

	public void refreshObserver(){
		throw new NotImplementedException();
	}
	
	public boolean containsProductTemplatePart(
			ProductTemplatePart productTemplatePart){
		return this.productTemplatePart.getId() == productTemplatePart.getId(); 
	}
	
}
