package com.giangphan.bejeweler;

/**
 * A move on the board from one gem to another.
 * 
 * @author gphan
 * 
 */
public class Move {
	public static final Move NO_MOVE = new Move(-1, -1, -1, -1);
	
	private final int fromX, fromY;
	private final int toX, toY;

	public Move(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		
	}

	public int getFromX() {
		return fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public int getToX() {
		return toX;
	}

	public int getToY() {
		return toY;
	}
}
