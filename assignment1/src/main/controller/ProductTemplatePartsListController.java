package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.model.ProductTemplateParts;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
