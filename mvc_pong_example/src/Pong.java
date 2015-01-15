package pong;

import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JFrame;

/**
 * This implements a simple Pong game to illustrate a more complex
 * GUI with multiple classes for the controller and view parts of 
 * the MVC pattern.
 * @author Tom Bylander
 *
 */
public class Pong {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* create new model, view and controller */
		PongModel model = new PongModel();
		PongView view = new PongView(model);
		PongMouseController mouseController = 
				new PongMouseController(model, view);
		PongResizeController resizeController = 
				new PongResizeController(model, view);
		PongMenuController menuController = 
				new PongMenuController(model, view);
		PongPopupController popupController = 
				new PongPopupController(model, view);
		
		/* register other controllers as listeners */
		view.registerListeners(mouseController, resizeController,
				menuController, popupController);
		
		PongRepaintController repaintController = 
				new PongRepaintController(model, view);
		
		/* start it up */
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(400, 300);
		view.setVisible(true);
		
		//MARCOS
		PongPaddleObserver o1 = new PongPaddleObserver("Paddle Coordinates");
		o1.setLocation(400, 10);
		model.registerObserver(o1);
		PongBallObserver o2 = new PongBallObserver("Ball Coordinates");
		o2.setLocation(400, 150);
		model.registerObserver(o2);
		
		// repaint timer so that the screen will update every 25 ms
    new Timer(25, repaintController).start();

	}
}
