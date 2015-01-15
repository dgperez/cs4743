package pong;

import java.awt.event.*;

public class PongController implements MouseListener, MouseMotionListener {
	PongModel model;
	PongView view;
	public PongController(PongModel model, PongView view) {
		this.model = model;
		this.view = view;
	}
	
	/*
	 * MouseListener event handlers
	 */ 

	/**
	 * handle event when mouse released immediately after press
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent event) {
		view.setStatus(String.format( "Clicked at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mouseClicked

	/**
	 * handle event when mouse pressed
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent event) {
		view.setStatus( String.format("Pressed at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mousePressed

	/**
	 * handle event when mouse released
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent event) {
		view.setStatus( String.format("Released at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mouseReleased

	/**
	 * handle event when mouse enters area
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent event) {
		view.setStatus( String.format("Mouse entered at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mouseEntered

	/**
	 * handle event when mouse exits area
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent event) {
		view.setStatus("Mouse outside JPanel");
	} // end method mouseExited


	/*
	 *  MouseMotionListener event handlers
	 */

	/*
	 * handle event when user drags mouse with button pressed
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent event) {
		view.setStatus(String.format("Dragged at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mouseDragged

	/*
	 * handle event when user moves mouse
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent event) {
		view.setStatus(String.format("Moved at [%d, %d]", 
				event.getX(), event.getY()));
	} // end method mouseMoved
	
}
