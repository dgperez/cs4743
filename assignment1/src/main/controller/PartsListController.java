package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.Part;
import main.model.PartsInventory;
import main.model.UnitsOfQuantity;
import main.view.PartsDetailView;
import main.view.PartsListView;

public class PartsListController implements MouseListener, ActionListener{

	private PartsInventory partsInventory;
	
	private UnitsOfQuantity unitsOfQuantityTypes;
	
	private PartsListView partsListView;
	
	public PartsListController(PartsInventory partsInventory, 
			UnitsOfQuantity unitsOfQuantityTypes,
			PartsListView partsListView) {
		this.partsInventory = partsInventory;
		this.unitsOfQuantityTypes = unitsOfQuantityTypes;
		this.partsListView = partsListView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("addPart".equals(e.getActionCommand())){
			PartsDetailView partsDetailView = new PartsDetailView(
					this.unitsOfQuantityTypes, true);
			PartsDetailController partsDetailController = 
					new PartsDetailController(partsDetailView, 
							this.partsInventory);
			partsDetailController.partIsNew();
			partsDetailView.registerListener(partsDetailController);
			this.partsInventory.registerObservers(partsDetailView);
		} else if ("deletePart".equals(e.getActionCommand())){
			 Part part = (Part)this.partsListView.getSelectedListPart();
			 try {
				this.partsInventory.removePart(part);
			} catch (SQLException e1) {
				e1.printStackTrace();
				String message = e1.getMessage();
				if(message.contains("foreign key constraint")){
					message = "An item in inventory references this part. " +
							"It cannot be deleted until that item is deleted.";
				}
				JOptionPane.showMessageDialog(null, "Error: " + 
						message);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				@SuppressWarnings("unchecked")
				JList<Object> list = (JList<Object>)e.getSource();
				Part part = (Part)list.getSelectedValue();
				PartsDetailView partsDetailView = new PartsDetailView(
						this.unitsOfQuantityTypes, false);
				partsDetailView.setPart(part);
				PartsDetailController partsDetailController = 
						new PartsDetailController(partsDetailView, 
								this.partsInventory);
				partsDetailView.registerListener(partsDetailController);
				this.partsInventory.registerObservers(partsDetailView);
			}
		}
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
