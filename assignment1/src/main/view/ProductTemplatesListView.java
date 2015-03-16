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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import main.model.ProductTemplates;

public class ProductTemplatesListView extends JFrame {

	private ProductTemplates productTemplates;
	
	private JList<Object> list;
	
	private JButton addProductTemplate;
	
	private JButton deleteProductTemplate;
	
	private JScrollPane scrollPane;
	
	private JPanel controls;
	
	private JPanel panel;

	public ProductTemplatesListView(ProductTemplates productTemplates) {
		super("Cabinetron Product Templates");
		
		this.productTemplates = productTemplates;
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.list = new JList<Object>(
				this.productTemplates.getProductTemplates().toArray());
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().add(this.list);
		this.scrollPane.setPreferredSize(new Dimension(250, 200));
		this.panel.add(this.scrollPane);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		this.addProductTemplate = new JButton("Add Template");
		this.addProductTemplate.setActionCommand("addproducttemplate");
		this.controls.add(this.addProductTemplate);
		
		this.deleteProductTemplate = new JButton("Delete Template");
		this.deleteProductTemplate.setActionCommand("deletetemplate");
		this.controls.add(this.deleteProductTemplate);
		
		add(this.panel, BorderLayout.CENTER);
		add(this.controls, BorderLayout.SOUTH);
		
		pack();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void registerListener(){}

	public void refreshList(ProductTemplates productTemplates){
		this.productTemplates = productTemplates;
		this.list.setListData(
				this.productTemplates.getProductTemplates().toArray());
		this.list.repaint();
	}
	
	public Object getSelectedListItem(){
		return this.list.getSelectedValue();
	}
}
