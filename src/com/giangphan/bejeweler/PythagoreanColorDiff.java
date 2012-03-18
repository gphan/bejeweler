package com.giangphan.bejeweler;

import java.awt.Color;

/**
 * Calculates simple color distance by using geometry.
 * 
 * @author gphan
 * 
 */
public class PythagoreanColorDiff implements ColorDiff {

	private static final float HUE_BIAS = 0.1f;
	private static final float SAT_BIAS = 1f;
	private static final float VALUE_BIAS = 1f;
	private final boolean useHSV;

	public PythagoreanColorDiff(boolean useHSV) {
		this.useHSV = useHSV;
	}

	@Override
	public double getColorDifference(Color c1, Color c2) {
		if (useHSV) {
			float[] c1HSV = new float[3];
			float[] c2HSV = new float[3];
			Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), c1HSV);
			Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), c2HSV);
			
			float dh = c1HSV[0] - c2HSV[0];
			float ds = c1HSV[1] - c2HSV[1];
			float db = c1HSV[2] - c2HSV[2];

			return Math.sqrt(HUE_BIAS * (dh * dh) + SAT_BIAS * (ds * ds) + VALUE_BIAS * (db * db));
		} else {
			double dr = c1.getRed() - c2.getRed();
			double dg = c1.getGreen() - c2.getGreen();
			double db = c1.getBlue() - c2.getBlue();

			return Math.sqrt((dr * dr) + (dg * dg) + (db * db));
		}
	}
}
