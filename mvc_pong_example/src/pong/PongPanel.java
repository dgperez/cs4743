package pong;

import java.awt.*;
import javax.swing.*;

public class PongPanel extends JPanel {
	private PongModel model;
	private PongView view;
	
	public PongPanel(PongModel model, PongView view) {
		this.model = model;
		this.view = view;
		
		// so this JPanel can listen to the keyboard
		this.setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		int x = (int) model.getPaddleX();
		int y = (int) model.getPaddleY();
		if (view.getFlipVertical()) {
			y = view.translateY(y) - model.getPaddleHeight();
		}
		g.fillRect(x, y, model.getPaddleWidth(), model.getPaddleHeight());
		g.setColor(Color.RED);
		x = (int) model.getBallX();
		y = (int) model.getBallY();
		if (view.getFlipVertical()) {
			y = view.translateY(y) - model.getBallDiameter();
		}
		g.fillOval(x, y, model.getBallDiameter(), model.getBallDiameter());
	}
}
