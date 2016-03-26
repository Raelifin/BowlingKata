package test;

import game.BowlingGame;
import junit.framework.TestCase;

public class BowlingGameTest extends TestCase {
	
	private BowlingGame g;
	
	protected void setUp() {
		g = new BowlingGame();
	}
	
	private void rollMany(int quantity, int roll) {
		for (int i=0; i < quantity; i++) {
			g.roll(roll);
		}
	}
	
	public void testGutterGame() {
		rollMany(20,0);
		assertEquals(0, g.score());
	}
	
	public void testAllOnes() {
		rollMany(20,1);
		assertEquals(20, g.score());
	}
	
	public void testOneSpare() {
		g.roll(5);
		g.roll(5);
		g.roll(3);
		rollMany(17,0);
		assertEquals(16, g.score());
	}
	
	public void testOneStrike() {
		g.roll(10);
		g.roll(3);
		g.roll(4);
		rollMany(16,0);
		assertEquals(24, g.score());
	}
	
	public void testPerfectGame() {
		rollMany(12,10);
		assertEquals(300, g.score());
	}
}
