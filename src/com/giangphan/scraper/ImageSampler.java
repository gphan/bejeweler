package com.giangphan.scraper;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageSampler {
	private BufferedImage image;
	private final int gridWidth;
	private final int gridHeight;
	private int cellWidth;
	private int cellHeight;

	/**
	 * Creates new Image Sampler
	 * @param image
	 */
	public ImageSampler(BufferedImage image, int gridWidth, int gridHeight) {
		this.image = image;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.cellWidth = this.image.getWidth() / this.gridWidth;
		this.cellHeight = this.image.getHeight() / this.gridHeight;
	}
	
	/**
	 * Returns a 2-dimensional array of the average colors of the image.
	 * @return
	 */
	public Color[][] getAverageColors() {
		int cellWidth = this.image.getWidth() / this.gridWidth;
		int cellHeight = this.image.getHeight() / this.gridHeight;
		

	}
	
	private Color getAverageColor(int gridx, int gridy) {
		int cellWidth = this.image.getWidth() / this.gridWidth;
	}
}
