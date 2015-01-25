package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.model.Inventory;
import main.model.Item;

public class InventoryListView extends JFrame {
	
	private JList<Object> list;

	public InventoryListView(Inventory inventory) {
		super("Cabinetron Inventory");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		ArrayList<String> allItems = new ArrayList<String>();
		
		for(Item item : inventory.getInventory()){
			allItems.add(item.toString());
		}
		
		list = new JList<Object>(allItems.toArray());
		
		JScrollPane pane = new JScrollPane();
		pane.getViewport().add(list);
		pane.setPreferredSize(new Dimension(250, 200));
		panel.add(pane);
		
		JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		controls.add(new JButton("Add Part"));
		controls.add(new JButton("Delete Part"));
		add(panel, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
		
		pack();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
