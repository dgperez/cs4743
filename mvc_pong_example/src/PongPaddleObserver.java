package pong;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PongPaddleObserver extends JFrame implements PongModelObserver {
	private static final long serialVersionUID = 1L;
	JLabel paddleX;

	//called by model
	@Override
	public void updateObserver(PongModel m) {
		paddleX.setText(Double.toString(m.getPaddleX()));
	}
	
	public PongPaddleObserver(String title) {
		super(title);
		
		this.setLayout(new GridLayout(1, 3));

		this.add(new JLabel(""));
		this.add(new JLabel("X"));
		paddleX = new JLabel("0.0");
		this.add(paddleX);

		this.setSize(250, 100);
		this.setVisible(true);
	}
}
