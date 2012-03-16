package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Bejeweled Blitz Bot
 * 
 * @author gphan
 *
 */
public class Bejeweler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final int gridSize = 8;
			final int cellSize = 25;
			final int totalSize = gridSize * cellSize;
			
			final ScreenCapturer capturer = new ScreenCapturer(0, 0, totalSize, totalSize);
			final ScreenCaptureFrame frame = new ScreenCaptureFrame(totalSize, totalSize);
			final GridFrame grid = new GridFrame(cellSize, cellSize, gridSize, gridSize);
			final ColorSampler sampler = new AverageColorSampler();
			
			Timer timer = new Timer(100, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					capturer.centerCaptureOnMouse();
					capturer.recapture();
					frame.setCapture(capturer.getImage());
					Color[][] sampleColors = sampler.getColorSamples(capturer.getImage(), gridSize, gridSize);
					grid.updateCellColors(sampleColors);
				}
			});
			
			timer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
