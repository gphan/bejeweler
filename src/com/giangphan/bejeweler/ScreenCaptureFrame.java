package com.giangphan.bejeweler;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenCaptureFrame extends JFrame {

	private static final int WINDOW_PADDING = 5;
	private JLabel imageLabel;

	/**
	 * Creates a new frame to display the capture image.
	 * @param width
	 * @param height
	 * @throws AWTException
	 */
	public ScreenCaptureFrame(int width, int height) throws AWTException {
		super("Screen Capture");
		
		setLocation(300, 10);
		int frameWidth = width + WINDOW_PADDING * 2;
		int frameHeight = height + WINDOW_PADDING * 2 + 20; // window control buffer
		setSize(frameWidth, frameHeight);
		
		
		this.imageLabel = new JLabel();
		this.imageLabel.setSize(width, height);
		this.imageLabel.setLocation(WINDOW_PADDING, WINDOW_PADDING);
		
		add(this.imageLabel);
		setLayout(null);
		setBackground(Color.black);
		setVisible(true);
	}
	
	public void setCapture(BufferedImage image) {
		this.imageLabel.setIcon(new ImageIcon(image));
	}
	
	public static void main(String[] args) {
		try {
			new ScreenCaptureFrame(200, 200);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
