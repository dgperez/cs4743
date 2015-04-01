package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.ProductTemplateDetailController;
import main.model.ProductTemplate;

public class ProductTemplateDetailView extends JFrame {
	
	private static final long serialVersionUID = -4189487620065499866L;
	
	private JLabel id_Label = new JLabel("Id: ");
	private JTextField id_Text;
	
	private JLabel productNumber_Label = new JLabel("Product #: ");
	private JTextField productNumber_Text;
	
	private JLabel productDescription_Label = new JLabel("Prod Desc: ");
	private JTextField productDescription_Text;
	
	private JLabel quantity_Label = new JLabel("Quantity: ");
	private JTextField quantity_Text;
	
	private JPanel controls;
	
	private JPanel inputs;
	
	private JPanel labelsPanel;
	
	private JPanel fieldsPanel;
	
	private JPanel gui;
	
	private JButton saveTemplate;
	
	private ProductTemplate productTemplate;
	
	private boolean newTemplate = false;

	public ProductTemplateDetailView(boolean newTemplate) {
		super("Product Template");
		this.newTemplate = newTemplate;
		
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		this.labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		this.fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));

		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		this.id_Text = new JTextField(10);
		this.id_Text.setEditable(false);
		
		this.productNumber_Text = new JTextField(10);
		
		this.productDescription_Text = new JTextField(10);
		
		this.quantity_Text = new JTextField(10);
		
		if(this.newTemplate){
			this.id_Label.setVisible(false);
			this.id_Text.setVisible(false);
		}
		
		this.labelsPanel.add(this.id_Label);
		this.fieldsPanel.add(this.id_Text);
		
		this.labelsPanel.add(this.productNumber_Label);
		this.fieldsPanel.add(this.productNumber_Text);
		
		this.labelsPanel.add(this.productDescription_Label);
		this.fieldsPanel.add(this.productDescription_Text);
		
		this.labelsPanel.add(this.quantity_Label);
		this.fieldsPanel.add(this.quantity_Text);
		
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
		if(!this.newTemplate){
			this.id_Text.setText(
					Integer.toString(this.productTemplate.getId()));
			this.productNumber_Text.setText(
					this.productTemplate.getProductNumber());
			this.productDescription_Text.setText(
					this.productTemplate.getProductDescription());
			this.quantity_Text.setText(
					Integer.toString(this.productTemplate.getQuantity()));
		}
	}
	
	public boolean containsProductTemplate(
			ProductTemplate productTemplate){
		return this.productTemplate.getId() == productTemplate.getId();
	}
	
	public void registerListener(
			ProductTemplateDetailController productTemplateDetailController){
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(
						productTemplateDetailController);
			}
		}
	}
	
	public int getId(){
		return Integer.parseInt(this.id_Text.getText());
	}
	
	public String getProductNumber(){
		return this.productNumber_Text.getText();
	}
	
	public String getProductDescription(){
		return this.productDescription_Text.getText();
	}
	
	public ProductTemplate getProductTemplate(){
		if(this.productTemplate == null){
			return new ProductTemplate(
					(!this.newTemplate) ? this.getId() : -1, 
					this.getProductNumber(), 
					this.getProductDescription(),
					this.getQuantity());
		} else {
			this.productTemplate.setProductNumber(this.getProductNumber());
			this.productTemplate.setProductDescription(this.getProductDescription());
			this.productTemplate.setQuantity(this.getQuantity());
		}
		return this.productTemplate;
	}
	
	public int getQuantity(){
		return Integer.parseInt(this.quantity_Text.getText());
	}
	
	public void setNew(boolean isNew){
		this.newTemplate = isNew;
		this.id_Label.setVisible(!isNew);
		this.id_Text.setVisible(!isNew);
	}
}
