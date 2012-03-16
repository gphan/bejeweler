package com.giangphan.bejeweler;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class GridCell extends JComponent {	
	/**
	 * Creates a new grid cell.
	 */
	public GridCell() {
		super();
		this.setBackground(Color.blue);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
