package main.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.model.ProductTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTemplateDetailView extends JFrame {
	
	private JLabel id_Label = new JLabel("Id: ");
	private JTextField id_Text;
	
	private JLabel productNumber_Label = new JLabel("Product #: ");
	private JTextField productNumber_Text;
	
	private JLabel productDescription_Label = new JLabel("Product Desc: ");
	private JTextField productDescription_Text;
	
	private JPanel controls;
	
	private JPanel inputs;
	
	private JPanel labelsPanel;
	
	private JPanel fieldsPanel;
	
	private JPanel gui;
	
	private JButton saveTemplate;
	
	private ProductTemplate productTemplate;
	
	private boolean newtemplate = false;

	public ProductTemplateDetailView(boolean newTemplate) {
		super("Product Template");
		this.newtemplate = newTemplate;
		
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));

		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		this.id_Text = new JTextField(10);
		this.id_Text.setEditable(false);
		
		this.productNumber_Text = new JTextField(10);
		
		this.productNumber_Text = new JTextField(10);
		
		if(this.newtemplate){
			this.id_Label.setVisible(false);
			this.id_Text.setVisible(false);
		}
		
		labelsPanel.add(this.id_Label);
		fieldsPanel.add(this.id_Text);
		
		labelsPanel.add(this.productNumber_Label);
		fieldsPanel.add(this.productNumber_Text);
		
		labelsPanel.add(this.productDescription_Label);
		fieldsPanel.add(this.productDescription_Text);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
		
		this.saveTemplate = new JButton("Save Template");
		this.saveTemplate.setActionCommand("saveTemplate");
		
		this.controls.add(this.saveTemplate);
		
		this.gui = new JPanel(new BorderLayout(10, 10));
		this.gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.gui.add(this.inputs, BorderLayout.CENTER);
		this.gui.add(this.controls, BorderLayout.SOUTH);
		
		add(this.gui);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void showProductTemplateDetailView(boolean show){
		this.setVisible(show);
	}
	
	public void setProductTemplate(ProductTemplate productTemplate){
		this.productTemplate = productTemplate;
		this.refreshObserver();
	}

	public void refreshObserver(){
		throw new NotImplementedException();
	}
	
	public boolean containsProductTemplate(
			ProductTemplate productTemplate){
		return this.productTemplate.getId() == productTemplate.getId();
	}
	
	public void registerListener(){}
}
