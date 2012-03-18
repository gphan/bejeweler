package com.giangphan.bejeweler;

import java.awt.Color;

public class ColorMatcher {
	private static final double DIST_THRESHOLD = 0.25;
	private ColorDiff diffEquation;
	
	/**
	 * Creates a new color matcher using the difference equation.
	 * @param diffEquation
	 */
	public ColorMatcher(ColorDiff diffEquation) {
		this.diffEquation = diffEquation;
	}
	
	/**
	 * Calculates the matched colors and returns a different 2D array of colors.
	 * 
	 * @param colors
	 * @return
	 */
	public Color[][] getMatchedColors(Color[][] colors) {
		GemColor[] gems = GemColor.values();
		Color[][] matched = new Color[colors.length][colors[0].length];
		
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				double closetDist = gems[0].getColorDifference(diffEquation, colors[i][j]);
				GemColor closestGem = gems[0];
				for (int k = 1; k < gems.length; k++) {
					double testDist = gems[k].getColorDifference(diffEquation, colors[i][j]);
					if (testDist < closetDist) {
						closetDist = testDist;
						closestGem = gems[k];
					}
				}
				
				matched[i][j] = closestGem.getColor();
			}
		}
		
		return matched;
	}
	
	public GemColor[][] getMatchedGemColors(Color[][] colors) {
		GemColor[] gemColors = GemColor.values();
		GemColor[][] matched = new GemColor[colors.length][colors.length];
		
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				for (int k = 1; k < gemColors.length - 1; k++) {
					double testDist = gemColors[k].getColorDifference(diffEquation, colors[i][j]);
					System.out.println("Color Distance : " + testDist);
					if (testDist < DIST_THRESHOLD) {
						matched[i][j] = gemColors[k];
						break;
					}
				}
				if (matched[i][j] == null) {
					matched[i][j] = GemColor.BLACK;
				}
			}
		}
		
		return matched;
	}
}
