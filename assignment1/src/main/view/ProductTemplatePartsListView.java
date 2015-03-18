package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.controller.ProductTemplatePartsListController;
import main.model.ProductTemplateParts;

public class ProductTemplatePartsListView extends JFrame {
	
	private ProductTemplateParts productTemplateParts;
	
	private JList<Object> list;
	
	private JButton addProductTemplatePart;
	
	private JButton deleteProductTemplatePart;
	
	private JScrollPane scrollPane;
	
	private JPanel controls;
	
	private JPanel panel;

	public ProductTemplatePartsListView(
			ProductTemplateParts productTemplateParts) {
		super("Template Parts List");
		
		this.productTemplateParts = productTemplateParts;
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.list = new JList<Object>(
				this.productTemplateParts.getProductTemplateParts().toArray());
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().add(this.list);
		this.scrollPane.setPreferredSize(new Dimension(250, 200));
		this.panel.add(this.scrollPane);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		this.addProductTemplatePart = new JButton("Add Template Part");
		this.addProductTemplatePart.setActionCommand("addTemplatePart");
		this.controls.add(this.addProductTemplatePart);
		
		this.deleteProductTemplatePart = new JButton("Delete Template Part");
		this.deleteProductTemplatePart.setActionCommand("deleteTemplatePart");
		this.controls.add(this.deleteProductTemplatePart);
		
		add(this.panel, BorderLayout.CENTER);
		add(this.controls, BorderLayout.SOUTH);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void registerListener(
			ProductTemplatePartsListController 
				productTemplatePartsListController){
		this.list.addMouseListener(productTemplatePartsListController);
		
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(productTemplatePartsListController);
			}
		}
	}

	public void refreshList(ProductTemplateParts productTemplateParts){
		this.productTemplateParts = productTemplateParts;
		this.list.setListData(
				this.productTemplateParts.getProductTemplateParts().toArray());
		this.list.repaint();
	}
	
	public Object getSelectedItem(){
		return this.list.getSelectedValue();
	}
	
}
