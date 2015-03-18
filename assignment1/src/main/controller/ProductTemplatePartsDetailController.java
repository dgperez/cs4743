package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.ProductTemplatePart;
import main.model.ProductTemplateParts;
import main.view.ProductTemplatePartsDetailView;

public class ProductTemplatePartsDetailController implements ActionListener {

	private ProductTemplateParts productTemplateParts;
	
	private ProductTemplatePartsDetailView productTemplatePartsDetailView;
	
	private boolean newTemplatePart = false;
	
	public ProductTemplatePartsDetailController(
			ProductTemplateParts productTemplateParts, 
			ProductTemplatePartsDetailView productTemplatePartsDetailView) {
		this.productTemplateParts = productTemplateParts;
		this.productTemplatePartsDetailView = productTemplatePartsDetailView;
		this.productTemplatePartsDetailView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("savePart")){
			ProductTemplatePart productTemplatePart = 
					this.productTemplatePartsDetailView
						.getProductTemplatePart();
			try{
				if(this.productTemplateParts
						.validateTemplatePart(productTemplatePart)){
					if(this.newTemplatePart){
						this.productTemplatePartsDetailView
							.setProductTemplatePart(this.productTemplateParts
									.addProductTemplatePart(
											productTemplatePart));
						this.newTemplatePart = false;
						this.productTemplatePartsDetailView
							.setNew(this.newTemplatePart);
					} else {
						this.productTemplatePartsDetailView
							.setProductTemplatePart(productTemplatePart);
						this.productTemplateParts
							.editProductTemplatePart(productTemplatePart);
					}
					this.productTemplatePartsDetailView.refreshObserver();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		}

	}
	
	public void templatePartIsNew(){
		this.newTemplatePart = true;
	}

}
