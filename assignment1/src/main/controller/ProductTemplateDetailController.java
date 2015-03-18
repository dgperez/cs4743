package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.ProductTemplate;
import main.model.ProductTemplates;
import main.view.ProductTemplateDetailView;

public class ProductTemplateDetailController implements ActionListener {

	private ProductTemplateDetailView productTemplateDetailView;
	
	private ProductTemplates productTemplates;
	
	private boolean newTemplate = false;
	
	public ProductTemplateDetailController(
			ProductTemplateDetailView productTemplateDetailView,
			ProductTemplates productTemplates) {
		this.productTemplateDetailView = productTemplateDetailView;
		this.productTemplates = productTemplates;
		this.productTemplateDetailView.showProductTemplateDetailView(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("saveTemplate")){
			ProductTemplate productTemplate = 
					this.productTemplateDetailView.getProductTemplate();
			try{
				if(this.productTemplates.validateProductTemplate(
						productTemplate)){
					if(this.newTemplate){
						this.productTemplateDetailView.setProductTemplate(
								this.productTemplates.addProductTemplate(
										productTemplate));
						this.newTemplate = false;
						this.productTemplateDetailView.setNew(
								this.newTemplate);
					} else {
						this.productTemplateDetailView.setProductTemplate(
								productTemplate);
						this.productTemplates.editProductTemplate(
								productTemplate);
					}
					this.productTemplateDetailView.refreshObserver();
				}
			
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		}
	}
	
	public void templateIsNew(){
		this.newTemplate = true;
	}

}
