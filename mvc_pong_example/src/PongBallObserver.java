package pong;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PongBallObserver extends JFrame implements PongModelObserver {
	private static final long serialVersionUID = 1L;
	JLabel ballX, ballY;

	//called by model
	@Override
	public void updateObserver(PongModel m) {
		ballX.setText(Double.toString(m.getBallX()));
		ballY.setText(Double.toString(m.getBallY()));
	}
	
	public PongBallObserver(String title) {
		super(title);
		
		this.setLayout(null);

		JLabel label = new JLabel("Ball X"); 
		label.setBounds(10, 10, 40, 10);
		this.add(label);
		ballX = new JLabel("0.0");
		ballX.setBounds(50, 10, 40, 10);
		this.add(ballX);

		label = new JLabel("Ball Y"); 
		label.setBounds(10, 40, 40, 10);
		this.add(label);
		ballY = new JLabel("0.0");
		ballY.setBounds(50, 40, 40, 10);
		this.add(ballY);

		this.setSize(300, 300);
		this.setVisible(true);
	}
}
