package pong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class is an event handler for when the Pong's panel
 * is resized.
 * @author bylander
 */
public class PongResizeController extends ComponentAdapter {
	private PongModel model;
	private PongView view;
	
	public PongResizeController(PongModel model, PongView view) {
		this.model = model;
		this.view = view;
	}

    public void componentResized(ComponentEvent event) {
    	Dimension size = view.getPongPanelSize();
    	if (size.width != model.getWidth() || size.height != model.getHeight()) {
    		model.setSize(size.width, size.height);
    		view.setStatus("Size is " + size.width + " x " + size.height);
    	}
    }
}


