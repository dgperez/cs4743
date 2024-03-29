package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.model.Part;
import main.model.PartsInventory;
import main.model.Session;
import main.view.PartsDetailView;

public class PartsDetailController implements ActionListener {

	private PartsDetailView view;
	
	private PartsInventory partsInventory;

	private boolean newPart = false;
	
	private Session session;
	
	public PartsDetailController(PartsDetailView view, 
			PartsInventory partsInventory, Session session) {
		this.view = view;
		this.partsInventory = partsInventory;
		this.session = session;
		if(this.session.canViewParts()){
			this.view.showPartsDetailView();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("savePart") 
				&& this.session.canAddParts()){
			Part part = this.view.getPart();
			try {
				if(this.partsInventory.validateSavedPart(part)){
					if(this.newPart){
						this.view.setPart(this.partsInventory.addPart(part));
						this.newPart = false;
						this.view.setNew(this.newPart);
					} else {
						this.view.setPart(part);
						this.partsInventory.editPart(part);
					}
					this.view.refreshObserver();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		}
	}

	public void partIsNew(){
		this.newPart = true;
	}
}
