package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
			final int cellSize = 40;
			final int totalSize = gridSize * cellSize;
			
			
			final ScreenCapturer capturer = new ScreenCapturer(0, 0, totalSize, totalSize);
			final ScreenCaptureFrame frame = new ScreenCaptureFrame(totalSize, totalSize, capturer);
			final GridFrame grid = new GridFrame(cellSize, cellSize, gridSize, gridSize);
			final ColorSampler sampler = new InterpolationColorSampler(true);
			final ColorMatcher matcher = new ColorMatcher(new PythagoreanColorDiff());
			final BotPlayer player = new BotPlayer(capturer, sampler, matcher); 
			player.setGridFrame(grid);
			
			final ExecutorService executor = Executors.newSingleThreadExecutor();
			frame.addStartAction(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!player.isRunning()) {
						executor.submit(new Runnable() {
							@Override
							public void run() {
								try {
									player.start();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			});
			
			frame.addStopAction(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stop();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
