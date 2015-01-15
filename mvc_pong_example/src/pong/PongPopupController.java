package pong;

import java.awt.event.*;

public class PongPopupController extends MouseAdapter {
	
	private PongModel model;
	private PongView view;
	
	/**
	 * Needs to know the model and the view.
	 * @param model
	 * @param view
	 */
	public PongPopupController(PongModel model, PongView view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * Go back to the view object to do the pop up menu.
	 */
    public void mousePressed(MouseEvent event) { 
       view.checkForTriggerEvent(event); // check for trigger
    } // end method mousePressed

    /**
     * Go back to the view object to do the pop up menu.
     */
    public void mouseReleased(MouseEvent event) { 
       view.checkForTriggerEvent(event); // check for trigger
    } // end method mouseReleased
}
