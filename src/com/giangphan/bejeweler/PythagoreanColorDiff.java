package com.giangphan.bejeweler;

import java.awt.Color;

/**
 * Calculates simple color distance by using geometry.
 * 
 * @author gphan
 * 
 */
public class PythagoreanColorDiff implements ColorDiff {

	@Override
	public double getColorDifference(Color c1, Color c2) {
		int dr = Math.abs(c1.getRed() - c2.getRed());
		int dg = Math.abs(c1.getGreen() - c2.getGreen());
		int db = Math.abs(c1.getBlue() - c2.getBlue());

		return Math.sqrt((dr * dr) + (dg * dg) + (db * db));
	}
}
