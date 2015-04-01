package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import main.dao.ConnectionGateway;
import main.model.Inventory;
import main.model.Item;
import main.model.ProductTemplate;
import main.model.ProductTemplatePart;
import main.model.ProductTemplates;
import main.view.ProductTemplateDetailView;

public class ProductTemplateDetailController implements ActionListener {

	private ProductTemplateDetailView productTemplateDetailView;

	private ProductTemplates productTemplates;
	
	private Inventory inventory;

	private boolean newTemplate = false;

	public ProductTemplateDetailController(
			ProductTemplateDetailView productTemplateDetailView,
			ProductTemplates productTemplates) {
		this.productTemplateDetailView = productTemplateDetailView;
		this.productTemplates = productTemplates;
		this.productTemplateDetailView.showProductTemplateDetailView(true);
		ConnectionGateway connGateway = new ConnectionGateway();
		this.inventory = new Inventory(connGateway);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("saveTemplate")){
			ProductTemplate productTemplate = 
					this.productTemplateDetailView.getProductTemplate();
			try{

				if(this.newTemplate){
					if(this.productTemplates.validateProductTemplate(
							productTemplate)){
						this.productTemplateDetailView.setProductTemplate(
								this.productTemplates.addProductTemplate(
										productTemplate));
						this.newTemplate = false;
						this.productTemplateDetailView.setNew(
								this.newTemplate);
					}
				} else {
					this.productTemplateDetailView.setProductTemplate(
							productTemplate);
					this.productTemplates.editProductTemplate(
							productTemplate);
				}
				this.productTemplateDetailView.refreshObserver();

			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		} else if(e.getActionCommand().equals("createPtoduct")){
			ProductTemplate productTemplate = 
					this.productTemplateDetailView.getProductTemplate();
			try{
				if(this.newTemplate){
					JOptionPane.showMessageDialog(null, "Error: " +
							"Can not create product from new template.");
					return;
				}
				boolean valid = true;
				this.inventory.loadInitialInventory();
				for(ProductTemplatePart p : productTemplate.getProductTemplateParts()){
					for(Item item : this.inventory.getInventory()){
						if(item.getPart().getId() == p.getPart().getId()){
							System.out.println("Checking");
							if(item.getQuantity() < p.getPartQuantity()){
								valid = false;
							}
						}
					}
				}
				if(valid){
					System.out.println("Valid!");
				} else {
					JOptionPane.showMessageDialog(null, "Error: " +
							"Not enough parts to create product.");
				}
			} catch(Exception e1){
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
