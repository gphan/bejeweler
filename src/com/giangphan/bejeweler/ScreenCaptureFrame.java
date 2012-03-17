package com.giangphan.bejeweler;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenCaptureFrame extends JFrame {

	private static final int WINDOW_PADDING = 5;
	private JLabel imageLabel;
	private JButton recenterButton;

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
		int frameHeight = height + WINDOW_PADDING * 2 + 40; // window control buffer
		setSize(frameWidth, frameHeight);
		
		this.imageLabel = new JLabel();
		this.imageLabel.setSize(width, height);
		this.imageLabel.setLocation(WINDOW_PADDING, WINDOW_PADDING + 40);
		
		this.getContentPane().add(this.imageLabel);
		this.createRecenterButton();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setBackground(Color.black);
		setVisible(true);		
	}
	
	private void createRecenterButton() {
		recenterButton = new JButton("Recenter");
		recenterButton.setSize(100,40);
		recenterButton.setLocation(0,0);
		
		this.getContentPane().add(recenterButton);
	}
	
	public void addRecenterActionListener(ActionListener listener) {
		recenterButton.addActionListener(listener);
	}
	
	public void setCapture(BufferedImage image) {
		this.imageLabel.setIcon(new ImageIcon(image));
	}
}
