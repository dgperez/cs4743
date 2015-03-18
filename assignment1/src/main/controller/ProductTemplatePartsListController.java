package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.ProductTemplatePart;
import main.model.ProductTemplateParts;
import main.view.ProductTemplatePartsDetailView;
import main.view.ProductTemplatePartsListView;

public class ProductTemplatePartsListController implements ActionListener,
		MouseListener {

	private ProductTemplateParts productTemplateParts;
	
	private ProductTemplatePartsListView productTemplatePartsListView;
	
	public ProductTemplatePartsListController(
			ProductTemplateParts productTemplateParts,
			ProductTemplatePartsListView productTemplatePartsListView) {
		this.productTemplateParts = productTemplateParts;
		this.productTemplatePartsListView = productTemplatePartsListView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("addTemplatePart")){
			ProductTemplatePartsDetailView productTemplatePartsDetailView = 
					new ProductTemplatePartsDetailView(
							this.productTemplateParts, true);
			ProductTemplatePartsDetailController 
				productTemplatePartsDetailController = 
					new ProductTemplatePartsDetailController(
							this.productTemplateParts, 
							productTemplatePartsDetailView);
			productTemplatePartsDetailController.templatePartIsNew();
			productTemplatePartsDetailView
				.registerListener(productTemplatePartsDetailController);
			this.productTemplateParts
				.registerObservers(productTemplatePartsDetailView);
		} else if (e.getActionCommand().equals("deleteTemplatePart")){
			ProductTemplatePart productTemplatePart = 
					(ProductTemplatePart)this.productTemplatePartsListView
						.getSelectedItem();
			try{
				this.productTemplateParts
					.removeProductTemplatePart(productTemplatePart);
			} catch (Exception e1){
				e1.printStackTrace();
				String message = e1.getMessage();
				JOptionPane.showMessageDialog(null, "Error: " + 
						message);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(2 == e.getClickCount()){
			if(e.getSource() instanceof JList){
				@SuppressWarnings("unchecked")
				JList<Object> list = (JList<Object>)e.getSource();
				ProductTemplatePart productTemplatePart = 
						(ProductTemplatePart)list.getSelectedValue();
				ProductTemplatePartsDetailView productTemplatePartsDetailView = 
						new ProductTemplatePartsDetailView(
								this.productTemplateParts, false);
				productTemplatePartsDetailView
					.setProductTemplatePart(productTemplatePart);
				ProductTemplatePartsDetailController 
					productTemplatePartsDetailController = 
						new ProductTemplatePartsDetailController(
								this.productTemplateParts, 
								productTemplatePartsDetailView);
				productTemplatePartsDetailView
					.registerListener(productTemplatePartsDetailController);
				this.productTemplateParts
					.registerObservers(productTemplatePartsDetailView);
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
