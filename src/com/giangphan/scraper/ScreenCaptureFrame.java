package com.giangphan.scraper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class ScreenCaptureFrame extends JFrame {

	private static final int WINDOW_PADDING = 5;
	private Robot robot;
	private BufferedImage image;
	private JLabel imageLabel;
	private Rectangle captureRect;
	private Timer timer;
	
	public ScreenCaptureFrame(int x, int y, int width, int height) throws AWTException {
		super("Screen Capture");
		
		setLocation(300, 10);
		int frameWidth = width + WINDOW_PADDING * 2;
		int frameHeight = height + WINDOW_PADDING * 2 + 20; // window control buffer
		setSize(frameWidth, frameHeight);
		
		this.robot = new Robot();
		// Capture initial screen
		captureRect = new Rectangle(x, y, width, height);
		
		image = robot.createScreenCapture(captureRect);
		imageLabel = new JLabel(new ImageIcon(image));
		imageLabel.setSize(width, height);
		imageLabel.setLocation(WINDOW_PADDING, WINDOW_PADDING);
		
		add(imageLabel);
		setLayout(null);
		setBackground(Color.black);
		setVisible(true);
		
		this.timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				recapture();
			}
		});
		
		this.timer.start();
	}
	
	public BufferedImage getScreenCapture() {
		return this.image;
	}
	
	public Rectangle getCaptureRect() {
		return captureRect;
	}
	
	public Robot getRobot() {
		return robot;
	}
	
	public void recapture() {
		image = robot.createScreenCapture(captureRect);
		imageLabel.setIcon(new ImageIcon(image));
	}
	
	public static void main(String[] args) {
		try {
			new ScreenCaptureFrame(0, 0, 200, 200);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
