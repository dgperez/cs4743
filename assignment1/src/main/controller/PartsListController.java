package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import main.model.Item;
import main.model.Part;
import main.model.PartsInventory;
import main.model.UnitsOfQuantity;
import main.view.PartsDetailView;

public class PartsListController implements MouseListener, ActionListener{

	private PartsInventory partsInventory;
	
	private UnitsOfQuantity unitsOfQuantityTypes;
	
	public PartsListController(PartsInventory partsInventory, 
			UnitsOfQuantity unitsOfQuantityTypes) {
		this.unitsOfQuantityTypes = unitsOfQuantityTypes;
		this.partsInventory = partsInventory;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("addPart".equals(e.getActionCommand())){
			
		} else if ("deletePart".equals(e.getActionCommand())){
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JList){
				JList<Object> list = (JList<Object>)e.getSource();
				Part part = (Part)list.getSelectedValue();
				PartsDetailView partDetailView = new PartsDetailView(
						this.unitsOfQuantityTypes);
				partDetailView.setPart(part);
				PartsDetailController partsDetailController = 
						new PartsDetailController(partDetailView, 
								this.partsInventory);
				partsDetailController.editPart();
				partDetailView.registerListener(partsDetailController);
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
