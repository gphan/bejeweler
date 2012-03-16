package com.giangphan.bejeweler;

import java.awt.Color;
import java.security.InvalidParameterException;

import javax.swing.JFrame;

public class GridFrame extends JFrame {
	
	private static final int GRID_PADDING = 5;
	private static final int TOP_PADDING = 50;
	private static final int CELL_PADDING = 3;
	
	private GridCell[] gridCells;
	private final int cellWidth;
	private final int cellHeight;
	private final int gridWidth;
	private final int gridHeight;
	
	/**
	 * Creates a frame that displays a grid of GridCell objects.
	 */
	public GridFrame(int cellWidth, int cellHeight, int gridWidth, int gridHeight) {
		super();
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		
		int width = (gridWidth * (cellWidth + CELL_PADDING)) + (2 * GRID_PADDING);
		int height = (gridHeight * (cellHeight + CELL_PADDING)) + (2 * GRID_PADDING) + TOP_PADDING + 20;
		setSize(width, height);
		setLocation(0, 0);
		setTitle("The Grid");
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		
		int totalCells = gridWidth * gridHeight;
		this.gridCells = new GridCell[totalCells];
		for (int i = 0; i < totalCells; i++) {
			GridCell cell = new GridCell();
			this.gridCells[i] = cell;
			
			int gridx = GRID_PADDING + ((i % gridWidth) * (cellWidth + CELL_PADDING));
			int gridy = GRID_PADDING  + TOP_PADDING + ((i / gridWidth) * (cellHeight + CELL_PADDING));
			cell.setLocation(gridx, gridy);
			cell.setSize(cellWidth, cellHeight);
			this.getContentPane().add(cell);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	/**
	 * Sets the color of the grid cell at location x, y (0-based)
	 * 
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setCellColor(int x, int y, Color c) {
		if (x >= gridWidth || x < 0) 
			throw new InvalidParameterException("x");
		
		if (y >= gridHeight || y < 0) 
			throw new InvalidParameterException("y");
		
		gridCells[x + (this.gridWidth * y)].setBackground(c);
	}
	
	/**
	 * Updates the cell colors from a 2-dimensional array.
	 * @param colors
	 */
	public void updateCellColors(Color[][] colors) {
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				setCellColor(i, j, colors[i][j]);
			}
		}
	}
	
	public static void main(String[] args) {
		new GridFrame(25, 25, 8, 8);
	}
}
