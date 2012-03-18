package com.giangphan.bejeweler;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Captures the screen with the given initial x, y, w, and h.
 * 
 * @author gphan
 * 
 */
public class ScreenCapturer extends Observable {

	private Robot robot;
	private BufferedImage image;
	private Rectangle captureRect;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;

	/**
	 * Creates a new screen capturer based on the x and y locations and the
	 * width and height.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws AWTException
	 */
	public ScreenCapturer(int x, int y, int width, int height)
			throws AWTException {
		this.robot = new Robot();
		this.captureRect = new Rectangle(x, y, width, height);
		this.image = robot.createScreenCapture(captureRect);
		calculateCaptureBoundaries();
	}

	public BufferedImage getImage() {
		return image;
	}

	public Rectangle getCaptureRect() {
		return captureRect;
	}

	/**
	 * Re-captures the screen shot based on the current capture rectangle.
	 */
	public void recapture() {
		image = robot.createScreenCapture(captureRect);
		setChanged();
		notifyObservers();
	}

	public void centerCaptureOnMouse() {
		PointerInfo info = MouseInfo.getPointerInfo();
		Point point = info.getLocation();

		centerOnPoint(point.x, point.y);
	}

	public void centerOnPoint(int x, int y) {
		int xloc = (x < minX) ? 0 : (x >= maxX ? maxX - minX : x - minX);
		int yloc = (y < minY) ? 0 : (y >= maxY ? maxY - minY : y - minY);
		System.out.println(String.format("Recentering on X: %d Y: %d", xloc, yloc));
		captureRect.setLocation(xloc, yloc);
		recapture();
	}

	/**
	 * Sets the size of the capture window.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		this.captureRect.setSize(width, height);
		calculateCaptureBoundaries();
	}

	private void calculateCaptureBoundaries() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

		this.minX = captureRect.width / 2;
		this.minY = captureRect.height / 2;
		this.maxX = screenDimension.width - minX;
		this.maxY = screenDimension.height - minY;
	}
}
