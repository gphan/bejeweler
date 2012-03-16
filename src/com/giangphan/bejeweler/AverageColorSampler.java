package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Samples a buffered image and returns an average sample depending on the grid
 * width and height.
 * 
 * @author gphan
 * 
 */
public class AverageColorSampler implements ColorSampler {
	private BufferedImage image;
	private int gridWidth;
	private int gridHeight;
	private int cellWidth;
	private int cellHeight;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.giangphan.bejeweler.Sampler#getAverageColors()
	 */
	@Override
	public Color[][] getColorSamples(BufferedImage image, int gridWidth,
			int gridHeight) {
		this.image = image;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.cellWidth = image.getWidth() / this.gridWidth;
		this.cellHeight = image.getHeight() / this.gridHeight;

		Color[][] colors = new Color[gridWidth][gridHeight];

		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				colors[i][j] = getAverageColor(i, j);
			}
		}

		// Unset image for GC
		this.image = null;
		
		return colors;
	}

	/**
	 * Gets the average color given a grid x and y location of the image.
	 * 
	 * @param gridx
	 * @param gridy
	 * @return A color which is the average or highest.
	 */
	protected Color getAverageColor(int gridx, int gridy) {
		int totalPixels = cellWidth * cellHeight;
		int[] avg = new int[] { 0, 0, 0 };

		int startX = gridx * cellWidth;
		int startY = gridy * cellHeight;
		int endX = (gridx + 1) * cellWidth;
		int endY = (gridy + 1) * cellHeight;

		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				int pixel = this.image.getRGB(i, j);

				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel & 0xff);

				avg[0] += red;
				avg[1] += green;
				avg[2] += blue;
			}
		}

		avg[0] /= totalPixels;
		avg[1] /= totalPixels;
		avg[2] /= totalPixels;
		
		// Create new color
		return new Color(avg[0], avg[1], avg[2]);
	}
}
