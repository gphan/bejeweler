package com.giangphan.bejeweler;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenCaptureFrame extends JFrame implements Observer {

	private static final int WINDOW_PADDING = 5;
	private JLabel imageLabel;
	private JButton recenterButton;
	private JButton startButton;
	private JButton stopButton;
	private ScreenCapturer capturer;
	private boolean listening = false;

	/**
	 * Creates a new frame to display the capture image.
	 * @param width
	 * @param height
	 * @throws AWTException
	 */
	public ScreenCaptureFrame(ScreenCapturer capturer, int width, int height) throws AWTException {
		super("Screen Capture");
		this.capturer = capturer;
		
		this.capturer.addObserver(this);
		
		setLocation(300, 10);
		int frameWidth = width + WINDOW_PADDING * 2;
		int frameHeight = height + WINDOW_PADDING * 2 + 80; // window control buffer
		setSize(frameWidth, frameHeight);
		
		this.imageLabel = new JLabel();
		this.imageLabel.setSize(width, height);
		this.imageLabel.setLocation(WINDOW_PADDING, WINDOW_PADDING + 40);
		
		this.getContentPane().add(this.imageLabel);
		this.initComponents();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setBackground(Color.black);
		setVisible(true);
		setAlwaysOnTop(true);
	}
	
	private void initComponents() {
		recenterButton = new JButton("Recenter");
		recenterButton.setSize(100,40);
		recenterButton.setLocation(0,0);
		recenterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!listening) {
					listening = true;
					System.out.println("Press SPACE to select center of board.");
					return;
				}
				
				listening = false;
				capturer.centerCaptureOnMouse();
			}
		});
		
		startButton = new JButton("Start");
		startButton.setSize(100, 40);
		startButton.setLocation(110, 0);
		stopButton = new JButton("Stop");
		stopButton.setSize(100, 40);
		stopButton.setLocation(210, 0);
		
		this.getContentPane().add(recenterButton);
		this.getContentPane().add(startButton);
		this.getContentPane().add(stopButton);
	}
	
	public void addStartAction(ActionListener action) {
		this.startButton.addActionListener(action);
	}
	
	public void addStopAction(ActionListener action) {
		this.stopButton.addActionListener(action);
	}
	
	public void setCapture(BufferedImage image) {
		this.imageLabel.setIcon(new ImageIcon(image));
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		this.setCapture(capturer.getImage());
	}
}
