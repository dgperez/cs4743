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

import main.controller.InventoryListController;
import main.model.Inventory;

public class InventoryListView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -354701430566371345L;

	private final JList<Object> list;
	
	private JButton addPart;
	
	private JButton deletePart;
	
	private Inventory inventory;
	
	private JScrollPane scrollPane;
	
	private JPanel controls;

	public InventoryListView(Inventory inventory) {
		super("Cabinetron Inventory");
		
		this.inventory = inventory;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.list = new JList<Object>(this.inventory.getInventory().toArray());
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().add(this.list);
		this.scrollPane.setPreferredSize(new Dimension(250, 200));
		panel.add(this.scrollPane);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		this.addPart = new JButton("Add Part");
		this.addPart.setActionCommand("add");
		this.controls.add(this.addPart);
		
		this.deletePart = new JButton("Delete Part");
		this.deletePart.setActionCommand("delete");
		this.controls.add(this.deletePart);
		
		add(panel, BorderLayout.CENTER);
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
	public void registerListener(InventoryListController listener) {
		// add listener for JList double click events
		this.list.addMouseListener(listener);
		
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(listener);
			}
		}
	}
}
