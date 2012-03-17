package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class NearestBinColorSampler implements ColorSampler {
	private static final int RED_BIN = 0;
	private static final int GREEN_BIN = 1;
	private static final int BLUE_BIN = 2;
	private static final int SAMPLE_RATE = 10;
	private int colorBinRange;
	private int[][] colorBins;
	private final int binsPerColor;

	/**
	 * Creates a new nearest bin color sampler.
	 * 
	 * @param binsPerColor
	 *            The number of bins per color channel (r, g, b).
	 */
	public NearestBinColorSampler(int binsPerColor) {
		this.binsPerColor = binsPerColor;
		this.colorBinRange = 256 / binsPerColor;
		resetBins();
	}

	/**
	 * Resets the color bins
	 */
	private void resetBins() {
		colorBins = new int[3][binsPerColor];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < binsPerColor; j++) {
				colorBins[i][j] = 0;
			}
		}
	}

	@Override
	public Color[][] getColorSamples(BufferedImage image, int gridWidth,
			int gridHeight) {
		int cellWidth = image.getWidth() / gridWidth;
		int cellHeight = image.getHeight() / gridHeight;
		Color[][] colors = new Color[gridWidth][gridHeight];
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				colors[i][j] = getCellLargestColorBin(image, cellWidth, cellHeight, i, j);
			}
		}
		
		return colors;
	}

	/**
	 * Gets the color of the largest color bin for the grid square.
	 * 
	 * @param image
	 * @param cellWidth
	 * @param cellHeight
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	private Color getCellLargestColorBin(BufferedImage image, int cellWidth,
			int cellHeight, int gridX, int gridY) {
		int startX = cellWidth * gridX;
		int startY = cellHeight * gridY;
		int endX = cellWidth * (gridX + 1);
		int endY = cellHeight * (gridY + 1);
		int pixelSample = 0;

		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				if (pixelSample++ % SAMPLE_RATE != 0)
					continue;
				
				int pixelColor = image.getRGB(i, j);

				int r = (pixelColor >> 16) & 0xff;
				int g = (pixelColor >> 8) & 0xff;
				int b = (pixelColor) & 0xff;

				int rbin = r / colorBinRange;
				int gbin = g / colorBinRange;
				int bbin = b / colorBinRange;

				colorBins[RED_BIN][rbin]++;
				colorBins[GREEN_BIN][gbin]++;
				colorBins[BLUE_BIN][bbin]++;
			}
		}

		int[] maxChannelBin = new int[] { 0, 0, 0 };
		for (int i = 0; i < maxChannelBin.length; i++) {
			for (int j = 0; j < binsPerColor; j++) {
				if (colorBins[i][j] > colorBins[i][maxChannelBin[i]]) {
					maxChannelBin[i] = j;
				}
			}
		}

		resetBins();
		
		return new Color(maxChannelBin[0] * colorBinRange, maxChannelBin[1]
				* colorBinRange, maxChannelBin[2] * colorBinRange);
	}
}
