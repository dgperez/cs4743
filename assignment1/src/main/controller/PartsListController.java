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
import main.model.Session;
import main.model.UnitsOfQuantity;
import main.view.PartsDetailView;
import main.view.PartsListView;

public class PartsListController implements MouseListener, ActionListener{

	private PartsInventory partsInventory;
	
	private UnitsOfQuantity unitsOfQuantityTypes;
	
	private PartsListView partsListView;
	
	private Session session;
	
	public PartsListController(PartsInventory partsInventory, 
			UnitsOfQuantity unitsOfQuantityTypes,
			PartsListView partsListView, Session session) {
		this.partsInventory = partsInventory;
		this.unitsOfQuantityTypes = unitsOfQuantityTypes;
		this.partsListView = partsListView;
		this.session = session;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("addPart".equals(e.getActionCommand()) 
				&& this.session.canAddParts()){
			PartsDetailView partsDetailView = new PartsDetailView(
					this.unitsOfQuantityTypes, true, this.session);
			PartsDetailController partsDetailController = 
					new PartsDetailController(partsDetailView, 
							this.partsInventory, this.session);
			partsDetailController.partIsNew();
			partsDetailView.registerListener(partsDetailController);
			this.partsInventory.registerObservers(partsDetailView);
		} else if ("deletePart".equals(e.getActionCommand()) 
				&& this.session.canDeleteParts()){
			 Part part = (Part)this.partsListView.getSelectedListPart();
			 try {
				this.partsInventory.removePart(part);
			} catch (SQLException e1) {
				e1.printStackTrace();
				String message = e1.getMessage();
				if(message.contains("foreign key constraint")){
					if(message.contains("product_template_parts")){
						message = "An existing Product Template references " +
								"this Part. Remove the template first.";
					} else if (message.contains("inventory")){
						message = "An item in inventory references this " +
								"part. It cannot be deleted until that " +
								"item is deleted.";
					}
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
						this.unitsOfQuantityTypes, false, this.session);
				partsDetailView.setPart(part);
				PartsDetailController partsDetailController = 
						new PartsDetailController(partsDetailView, 
								this.partsInventory, this.session);
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
