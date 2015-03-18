package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.ProductTemplatePartsDetailController;
import main.model.Part;
import main.model.ProductTemplatePart;
import main.model.ProductTemplateParts;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTemplatePartsDetailView extends JFrame {

	private ProductTemplateParts productTemplateParts;
	
	private ProductTemplatePart productTemplatePart;
	
	private boolean newTemplatePart;
	
	private JLabel id_Label = new JLabel("Id: ");
	
	private JLabel partsList_Label = new JLabel("Parts List: ");
	
	private JLabel partQuantity_Label = new JLabel("Part Quantity: ");
	
	private JTextField id_Text;
	
	private JComboBox<Object> partsList;
	
	private JTextField partQuantity_Text;
	
	private JButton savePart;
	
	private JPanel controls;
	
	private JPanel inputs;
	
	private JPanel labelsPanel;
	
	private JPanel fieldsPanel;
	
	private JPanel gui;
	
	public ProductTemplatePartsDetailView(
			ProductTemplateParts productTemplateParts,
			boolean newTemplatePart) {
		super("Product Template Detail");
		
		this.productTemplateParts = productTemplateParts;
		this.newTemplatePart = newTemplatePart;
		
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		this.labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		this.fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));

		this.inputs.add(labelsPanel, BorderLayout.WEST);
		this.inputs.add(fieldsPanel, BorderLayout.CENTER);
		
		this.id_Text = new JTextField(10);
		this.id_Text.setEditable(false);
		
		this.partQuantity_Text = new JTextField(10);
		
		try {
			this.partsList = new JComboBox<Object>(
					this.productTemplateParts.getAvailableParts().toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + 
					e.getMessage());
		}
		
		if(this.newTemplatePart){
			this.id_Label.setVisible(false);
			this.id_Text.setVisible(false);
		}
		
		this.labelsPanel.add(this.id_Label);
		this.fieldsPanel.add(this.id_Text);
				
		this.labelsPanel.add(this.partsList_Label);
		this.fieldsPanel.add(this.partsList);
		
		this.labelsPanel.add(this.partQuantity_Label);
		this.fieldsPanel.add(this.partQuantity_Text);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
		
		this.savePart = new JButton("Save Part");
		this.savePart.setActionCommand("savePart");
		
		this.controls.add(this.savePart);
		
		this.gui = new JPanel(new BorderLayout(10, 10));
		this.gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.gui.add(this.inputs, BorderLayout.CENTER);
		this.gui.add(this.controls, BorderLayout.SOUTH);
		
		add(this.gui);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void registerListener(
			ProductTemplatePartsDetailController 
				productTemplatePartsDetailController){
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(
						productTemplatePartsDetailController);
			}
		}
	}
	
	public void setProductTemplatePart(
			ProductTemplatePart productTemplatePart){
		this.productTemplatePart = productTemplatePart;
		this.refreshObserver();
	}
	
	public int getId(){
		return Integer.parseInt(this.id_Text.getText());
	}
	
	public Part getPart(){
		Part part = (Part)this.partsList.getSelectedItem();
		return part;
	}
	
	public int getQuantity(){
		int i = -1;
		try{
			i = Integer.parseInt(this.partQuantity_Text.getText());
		} catch(NumberFormatException e){
			i = -1;
			e.printStackTrace();
		}
		return i;
	}
	
	public ProductTemplatePart getProductTemplatePart(){
		if(this.newTemplatePart){
			ProductTemplatePart temp =  new ProductTemplatePart(
					(!this.newTemplatePart) ? this.getId() : -1, 
							this.getPart(), 
							this.getQuantity());
			temp.setProductTemplateId(
					this.productTemplateParts.getProductTemplateId());
			return temp;
		}
		return this.productTemplatePart;
	}

	public void refreshObserver(){
		if(!this.newTemplatePart){
			this.id_Text.setText(
					Integer.toString(this.productTemplatePart.getId()));
			this.partsList.setSelectedItem(this.productTemplatePart.getPart());
			this.partQuantity_Text.setText(
					Integer.toString(
							this.productTemplatePart.getPartQuantity()));
		} else {
			this.partsList.setSelectedIndex(0);
		}
	}
	
	public boolean containsProductTemplatePart(
			ProductTemplatePart productTemplatePart){
		return this.productTemplatePart.getId() == productTemplatePart.getId(); 
	}
	
	public void setNew(boolean isNew){
		this.newTemplatePart = isNew;
		this.id_Label.setVisible(!isNew);
		this.id_Text.setVisible(!isNew);
	}
}
