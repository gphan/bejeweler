package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface ColorSampler {

	/**
	 * Returns a 2-dimensional array of a sampled grid colors of the image.
	 * i.e., average color within the grid subsection of an image or highest bin count.
	 * 
	 * @return A 2-dimensional array of java.awt.Color
	 */
	public abstract Color[][] getColorSamples(BufferedImage image, int gridWidth, int gridHeight);
}