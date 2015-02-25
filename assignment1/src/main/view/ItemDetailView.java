package main.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.model.Item;
import main.model.Locations;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ItemDetailView extends JFrame {
	
	private JPanel panel;

	private Locations locations;
	
	public ItemDetailView(Locations locations) {
		this.locations = locations;
	}

	public void refreshObserver(){
		throw new NotImplementedException();
	}
	
	public boolean containsItem(Item item){
		throw new NotImplementedException();
	}
	
}
