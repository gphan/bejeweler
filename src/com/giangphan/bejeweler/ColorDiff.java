package com.giangphan.bejeweler;

import java.awt.Color;

public interface ColorDiff {
	/**
	 * Gets the color distance between two colors.
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public double getColorDifference(Color c1, Color c2);
}
