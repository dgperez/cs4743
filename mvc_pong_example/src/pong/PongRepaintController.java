package pong;

import java.awt.event.*;
/**
 * To be used by a timer to update the model and to repaint the view
 * 
 * @author Tom Bilander
 * @author Weining Zhang
 *
 */
public class PongRepaintController implements ActionListener {
	private PongModel model;
	private PongView view;
	
	public PongRepaintController(PongModel model, PongView view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent event) {
		int hits = model.getHits();
		int misses = model.getMisses();
		// update the model
		model.moveBall();
		// If this move causes a hit or a miss, change the model status
		if (hits != model.getHits() || misses != model.getMisses()) {
			view.setStatus(model.getHits() + " hits, " + 
					model.getMisses() + " misses");
		}
		// repaint the view
		view.repaint();
	}

}
