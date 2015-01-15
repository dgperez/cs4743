package test_pong;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import pong.PongModel;

public class TestModel {

	/*
	 * Test 1: Test reflection when ball hits the paddle
	 * Test 2: Test miss counter when paddle misses the ball
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test1() {
		PongModel model = new PongModel();
		//have to set the height
		model.setSize(400, 100);
		model.setPaddle(290, 10);//paddle is known to be 100 pixels wide
		
		//ball is decreasing y by change each turn
		//move ball until its Y is at paddle Y + paddle height (i.e., it hits the paddle)
		while(model.getBallY() > model.getPaddleY() + model.getPaddleHeight()) {
			model.moveBall();
		}
		//save ball Y
		double temp = model.getBallY();
		model.moveBall();
		//ball Y should now be > temp (it hit the paddle and reverse Y change)
		assertTrue(model.getBallY() > temp);
	}
	
	@Test
	public void test2() {
		PongModel model = new PongModel();
		int oldCount = model.getMisses();
		//have to set the height
		model.setSize(400, 100);
		//move paddle off screen
		model.setPaddle(-100, 10);//paddle is known to be 100 pixels wide
		while(model.getBallY() > model.getPaddleY() + model.getPaddleHeight()) {
			model.moveBall();
		}
		//should have missed the paddle
		//now keep moving ball until it moves back to bottom of screen (a miss)
		while(model.getBallY() < 100) {
			model.moveBall();
		}
		
		assertTrue(oldCount == 0 && model.getMisses() == 1);
	}

}
