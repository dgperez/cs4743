package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.model.ProductTemplate;

public class ProductTemplatePartsListView extends JFrame {
	
	private ProductTemplate productTemplate;
	
	private JList<Object> list;
	
	private JButton addProductTemplatePart;
	
	private JButton deleteProductTemplatePart;
	
	private JScrollPane scrollPane;
	
	private JPanel controls;
	
	private JPanel panel;

	public ProductTemplatePartsListView(ProductTemplate productTemplate) {
		super("Template Parts List");
		
		this.productTemplate = productTemplate;
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.list = new JList<Object>(
				this.productTemplate.getProductTemplateParts().toArray());
		
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
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void registerListener(){}

	public void refreshList(ProductTemplate productTemplate){
		this.productTemplate = productTemplate;
		this.list.setListData(
				this.productTemplate.getProductTemplateParts().toArray());
		this.list.repaint();
	}
	
	public Object getSelectedItem(){
		return this.list.getSelectedValue();
	}
	
}
