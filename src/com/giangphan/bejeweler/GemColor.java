package com.giangphan.bejeweler;

import java.awt.Color;

/**
 * Enumerable of bejeweled gem colors.
 * @author gphan
 *
 */
public enum GemColor {
	BLUE (Color.blue),
	WHITE (Color.white),
	ORANGE (Color.orange),
	RED (Color.red),
	YELLOW (Color.yellow),
	GREEN (Color.green),
	PURPLE (new Color(200, 0, 200)),
	BLACK (Color.black);
	
	private Color color;

	GemColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}

	/**
	 * Gets the color distance based on the color diff equation and the other
	 * color.
	 * 
	 * @param diffEq
	 * @param color
	 * @return
	 */
	public double getColorDifference(ColorDiff diffEq, Color color) {
		return diffEq.getColorDifference(this.color, color);
	}
}
