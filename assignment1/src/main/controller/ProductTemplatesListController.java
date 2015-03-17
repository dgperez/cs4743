package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.model.ProductTemplates;
import main.view.ProductTemplateListView;

public class ProductTemplatesListController implements MouseListener,
		ActionListener {

	private ProductTemplates productTemplates;
	
	private ProductTemplateListView productTemplatesListView;
	
	public ProductTemplatesListController(ProductTemplates productTemplates,
			ProductTemplateListView productTemplatesListView) {
		this.productTemplates = productTemplates;
		this.productTemplatesListView = productTemplatesListView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("addTemplate")){
			
		} else if (e.getActionCommand().equals("deleteTemplate")){
			
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(2 == e.getClickCount()){
			
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
