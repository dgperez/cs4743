package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.dao.ConnectionGateway;
import main.model.ProductTemplate;
import main.model.ProductTemplateParts;
import main.model.ProductTemplates;
import main.view.ProductTemplateDetailView;
import main.view.ProductTemplateListView;
import main.view.ProductTemplatePartsListView;

public class ProductTemplateListController implements MouseListener,
		ActionListener {

	private ProductTemplates productTemplates;
	
	private ProductTemplateListView productTemplatesListView;
	
	private ConnectionGateway connGateway;
	
	public ProductTemplateListController(ProductTemplates productTemplates,
			ProductTemplateListView productTemplatesListView,
			ConnectionGateway connGateway) {
		this.productTemplates = productTemplates;
		this.productTemplatesListView = productTemplatesListView;
		this.connGateway = connGateway;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("addTemplate")){
			ProductTemplateDetailView productTemplateDetailView = 
					new ProductTemplateDetailView(true);
			ProductTemplateDetailController productTemplateDetailController = 
					new ProductTemplateDetailController(
							productTemplateDetailView, this.productTemplates);
			productTemplateDetailController.templateIsNew();
			productTemplateDetailView.registerListener(
					productTemplateDetailController);
			this.productTemplates.registerObservers(productTemplateDetailView);
		} else if (e.getActionCommand().equals("deleteTemplate")){
			ProductTemplate productTemplate = 
					(ProductTemplate)this.productTemplatesListView
						.getSelectedListItem();
			try{
				this.productTemplates.deleteProductTemplate(productTemplate);
			} catch(Exception e1){
				e1.printStackTrace();
				String message = e1.getMessage();
				JOptionPane.showMessageDialog(null, "Error: " + 
						message);
			}
		} else if (e.getActionCommand().equals("viewParts")){
			if(this.productTemplatesListView.getSelectedListItem() != null){
				ProductTemplate productTemplate = 
						(ProductTemplate)this.productTemplatesListView
							.getSelectedListItem();
				ProductTemplateParts productTemplateParts = 
						new ProductTemplateParts(this.connGateway, 
								productTemplate);
				ProductTemplatePartsListView productTemplatePartsListView = 
						new ProductTemplatePartsListView(productTemplateParts);
				productTemplateParts.registerView(
						productTemplatePartsListView);
				ProductTemplatePartsListController 
					productTemplatePartsListController = 
						new ProductTemplatePartsListController(
								productTemplateParts, 
								productTemplatePartsListView);
				productTemplatePartsListView
					.registerListener(productTemplatePartsListController);
				productTemplatePartsListView.setVisible(true);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(2 == e.getClickCount()){
			if(e.getSource() instanceof JList){
				@SuppressWarnings("unchecked")
				JList<Object> list = (JList<Object>)e.getSource();
				ProductTemplate productTemplate = 
						(ProductTemplate)list.getSelectedValue();
				ProductTemplateDetailView productTemplateDetailView = 
						new ProductTemplateDetailView(false);
				productTemplateDetailView.setProductTemplate(productTemplate);
				ProductTemplateDetailController productTemplateDetailController 
					= new ProductTemplateDetailController(
							productTemplateDetailView, this.productTemplates);
				productTemplateDetailView.registerListener(
						productTemplateDetailController);
				this.productTemplates.registerObservers(
						productTemplateDetailView);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
