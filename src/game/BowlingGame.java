package game;


public class BowlingGame {

	private Frame frames = new Frame(9);
	
	public int score() {
		return frames.score();
	}

	public void roll(int pins) {
		frames = frames.addRoll(pins);
	}
	
	private class Frame {
		private final Integer rollOne;
		private final Integer rollTwo;
		private final Integer rollThree;
		private final Frame nextFrame;
		
		public Frame(int remainingFrames) {
			this.rollOne = null;
			this.rollTwo = null;
			this.rollThree = null;
			if (remainingFrames > 0) {
				this.nextFrame = new Frame(remainingFrames-1);
			} else {
				this.nextFrame = null;
			}
		}

		public Frame(Integer rollOne, Integer rollTwo, Integer rollThree, Frame nextFrame) {
			this.rollOne = rollOne;
			this.rollTwo = rollTwo;
			this.rollThree = rollThree;
			this.nextFrame = nextFrame;
		}
		
		public int rollOne() {
			return (rollOne!=null? rollOne : 0);
		}
		
		public int rollTwo() {
			return (rollTwo!=null? rollTwo : 0);
		}
		
		public int rollThree() {
			return (rollThree!=null? rollThree : 0);
		}
		
		public boolean isStrike() {
			return rollOne == 10;
		}
		
		public boolean isSpare() {
			return rollOne != null && rollTwo != null && rollOne + rollTwo == 10;
		}

		public int score() {
			return baseScore() + bonusScore() + tailScore();
		}
		
		private int baseScore() {
			return rollOne() + rollTwo();
		}
		
		private int bonusScore() {
			if (nextFrame != null) {
				int score = 0;
				if (isStrike()) {
					if (nextFrame.isStrike() && nextFrame.nextFrame != null) {
						score += nextFrame.rollOne() + nextFrame.nextFrame.rollOne();
					} else {
						score += nextFrame.baseScore();
					}
				} else if (isSpare()) {
					score += nextFrame.rollOne();
				}
				return score;
			} else {
				return (isStrike() || isSpare()? rollThree() : 0);
			}
		}

		private int tailScore() {
			return (nextFrame != null)? nextFrame.score() : 0;
		}

		public Frame addRoll(int pins) {
			if (rollOne == null) {
				return new Frame(pins, rollTwo, rollThree, nextFrame);
			} else if (isStrike() && nextFrame != null) {
				return new Frame(rollOne, rollTwo, rollThree, nextFrame.addRoll(pins));
			} else if (rollTwo == null) {
				return new Frame(rollOne, pins, rollThree, nextFrame);
			} else if (nextFrame != null) {
				return new Frame(rollOne, rollTwo, rollThree, nextFrame.addRoll(pins));
			} else if (rollThree == null) {
				return new Frame(rollOne, rollTwo, pins, nextFrame);
			}
			return null;
		}
	}
}
