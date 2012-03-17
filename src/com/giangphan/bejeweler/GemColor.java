package com.giangphan.bejeweler;

import java.awt.Color;

/**
 * Enumerable of bejeweled gem colors.
 * @author gphan
 *
 */
public enum GemColor {
	BLUE (new Color(0, 60, 170)),
	WHITE (new Color(200, 200, 200)),
	ORANGE (new Color(255, 116, 10)),
	RED (new Color(200, 0, 0)),
	YELLOW (new Color(255, 255, 0)),
	GREEN (new Color(85, 255, 130)),
	PURPLE (new Color(200, 0, 200));
	
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
