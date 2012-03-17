package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * TODO: Determine the grid's average color by using bicubic interpolation and
 * resizing the image down to a 1x1 pixel and then getting it's color.
 * BRILLIANT!
 * 
 * @author gphan
 * 
 */
public class InterpolationColorSampler implements ColorSampler {
	private BufferedImage interpCanvas = new BufferedImage(1, 1,
			BufferedImage.TYPE_INT_RGB);

	@Override
	public Color[][] getColorSamples(BufferedImage image, int gridWidth,
			int gridHeight) {
		int cellWidth = image.getWidth() / gridWidth;
		int cellHeight = image.getHeight() / gridHeight;
		Color[][] colors = new Color[gridWidth][gridHeight];
		
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				BufferedImage sub = getGridCellImage(image, cellWidth, cellHeight, i, j);
				colors[i][j] = getInterpolatedColor(sub);
			}
		}
		
		return colors;
	}

	/**
	 * Gets the buffered image's subregion
	 * 
	 * @param image
	 * @param cellWidth
	 * @param cellHeight
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	private BufferedImage getGridCellImage(BufferedImage image, int cellWidth,
			int cellHeight, int gridX, int gridY) {
		int startX = gridX * cellWidth;
		int startY = gridY * cellHeight;
		return image.getSubimage(startX, startY, cellWidth, cellHeight);
	}

	/**
	 * Resizes the images to a 1x1 pixel and gets the color.
	 * 
	 * @param image
	 * @return
	 */
	private Color getInterpolatedColor(BufferedImage image) {

		Graphics2D g = interpCanvas.createGraphics();
		try {
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(image, 0, 0, 1, 1, null);
		} finally {
			g.dispose();
		}

		int color = interpCanvas.getRGB(0, 0);
		return new Color(color);
	}
}
