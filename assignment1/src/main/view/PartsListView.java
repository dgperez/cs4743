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

import main.controller.PartsListController;
import main.model.Inventory;
import main.model.Part;
import main.model.PartsInventory;

public class PartsListView extends JFrame{

	private JList<Object> partList;
	
	private JButton addPart;
	
	private JButton deletePart;
	
	private PartsInventory partsInventory;
	
	private JScrollPane scrollPane;
	
	private JPanel controls;
	
	private JPanel panel;
	
	public PartsListView(PartsInventory partsInventory) {
		super("Cabinetron Parts List");
		this.partsInventory = partsInventory;
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.partList = new JList<Object>(this.partsInventory.getAllParts().toArray());
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().add(this.partList);
		this.scrollPane.setPreferredSize(new Dimension(250, 200));
		this.panel.add(this.scrollPane);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		this.addPart = new JButton("Add Part");
		this.addPart.setActionCommand("addPart");
		this.controls.add(this.addPart);
		
		this.deletePart = new JButton("Delete Part");
		this.deletePart.setActionCommand("deletePart");
		this.controls.add(this.deletePart);
		
		add(this.panel, BorderLayout.CENTER);
		add(this.controls, BorderLayout.SOUTH);
		
		pack();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Register the controller as the listener to the JList and the
	 * MousePanel.
	 * @param listener
	 */
	public void registerListener(PartsListController listener) {
		// add listener for JList double click events
		this.partList.addMouseListener(listener);
		
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(listener);
			}
		}
	}
	
	public void refreshList(Inventory inventory){
		this.partList.setListData(this.partsInventory.getAllParts().toArray());
		this.partList.repaint();
	}
	
	public Object getSelectedListPart(){
		return this.partList.getSelectedValue();
	}
	
}
