package com.giangphan.bejeweler;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class BotPlayer {
	private static final int PLAY_TIME = 60 * 1000;
	private static final int MOVE_DELAY = 100;
	private final ScreenCapturer capturer;
	private final ColorSampler sampler;
	private final ColorMatcher matcher;
	private final int GRID_SIZE = 8; // Default bejeweled grid
	private Robot robot;
	private boolean bottomsUp = true;
	private boolean running = false;
	private GemColor[][] colorGrid;
	private GridFrame gridFrame = null;

	/**
	 * Creates a new bot player that will analyze, sample, and then make moves
	 * based on the screen capturer's location.
	 * 
	 * @throws AWTException
	 * 
	 */
	public BotPlayer(ScreenCapturer capturer, ColorSampler sampler,
			ColorMatcher matcher) throws AWTException {
		this.capturer = capturer;
		this.sampler = sampler;
		this.matcher = matcher;
		this.robot = new Robot();
	}

	/**
	 * Starts playing
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		System.out.println("Starting play...");
		int cellWidth = capturer.getCaptureRect().width / GRID_SIZE;
		int cellHeight = capturer.getCaptureRect().height / GRID_SIZE;
		int windowX = capturer.getCaptureRect().x;
		int windowY = capturer.getCaptureRect().y;

		System.out.println("Cell width: " + cellWidth);
		System.out.println("Cell height: " + cellHeight);
		System.out.println("Window X: " + windowX);
		System.out.println("Window Y: " + windowY);

		running = true;
		long runningTime = 0;
		long startTime = System.currentTimeMillis();
		while (isRunning()) {
			this.colorGrid = getGemColors();
			if (gridFrame != null) {
				gridFrame.updateGemColors(this.colorGrid);
			}

			System.out.println("Looking for a move...");

			Move m = getFirstMove();
			if (m == Move.NO_MOVE) {
				System.out.println("No move found.");
			} else {
				System.out.println(m.toString());
			}

			// Calculate start click
			int centerOfStartGemX = windowX + (m.getFromX() * cellWidth)
					+ (cellWidth / 2);
			int centerOfStartGemY = windowY + (m.getFromY() * cellHeight)
					+ (cellHeight / 2);
			int centerOfEndGemX = windowX + (m.getToX() * cellWidth)
					+ (cellWidth / 2);
			int centerOfEndGemY = windowY + (m.getToY() * cellHeight)
					+ (cellHeight / 2);

			robot.mouseMove(centerOfStartGemX, centerOfStartGemY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(MOVE_DELAY);
			robot.mouseMove(centerOfEndGemX, centerOfEndGemY);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);

			long currTime = System.currentTimeMillis();
			runningTime += currTime - startTime;
			startTime = currTime;

			if (runningTime > PLAY_TIME) {
				running = false;
			}

			Thread.sleep(100);
		}
		System.out.println("Stopping play...");
	}

	public void stop() {
		this.running = false;
	}

	private GemColor[][] getGemColors() {
		Color[][] samples = sampler.getColorSamples(capturer.getImage(),
				GRID_SIZE, GRID_SIZE);
		return matcher.getMatchedGemColors(samples);
	}

	/**
	 * Gets the next move based on is bottoms up or not. Based on the current
	 * private instance of colorGrid.
	 */
	private Move getFirstMove() {
		try {
			if (isBottomsUp()) {
				for (int x = GRID_SIZE - 1; x >= 0; x--) {
					for (int y = GRID_SIZE - 1; y >= 0; y--) {
						if (this.colorGrid[x][y] == GemColor.BLACK)
							continue;
						
						Move m = getAnyMove(x, y, this.colorGrid);
						if (m != null) {
							return m;
						}
						System.out.println(String.format(
								"No move found for %d, %d", x, y));
					}
				}
			} else {
				for (int x = 0; x < GRID_SIZE; x++) {
					for (int y = 0; y < GRID_SIZE; y++) {
						if (this.colorGrid[x][y] == GemColor.BLACK)
							continue;
						
						Move m = getAnyMove(x, y, this.colorGrid);
						if (m != null) {
							return m;
						}
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return Move.NO_MOVE;
	}

	/**
	 * Get any move based on the x any y position and the grid.
	 * 
	 * @param x
	 * @param y
	 * @param grid
	 * @return Either a move or null if no moves found.
	 */
	private Move getAnyMove(int x, int y, GemColor[][] grid) {
		GemColor target = grid[x][y];

		// xx-X
		if (x > 2 && target == grid[x - 2][y]
				&& grid[x - 2][y] == grid[x - 3][y]) {
			return new Move(x, y, x - 1, y);
		}

		// X-xx
		if (x < GRID_SIZE - 3 && target == grid[x + 2][y]
				&& grid[x + 3][y] == grid[x + 2][y]) {
			return new Move(x, y, x + 1, y);
		}

		// X
		// -
		// x
		// x
		if (y < GRID_SIZE - 3 && target == grid[x][y + 2]
				&& grid[x][y + 2] == grid[x][y + 3]) {
			return new Move(x, y, x, y + 1);
		}

		// x
		// x
		// -
		// X
		if (y > 2 && target == grid[x][y - 2]
				&& grid[x][y - 3] == grid[x][y - 2]) {
			return new Move(x, y, x, y - 1);
		}

		// - X -
		// x - x
		if (y < GRID_SIZE - 1 && x > 0 && x < GRID_SIZE - 1
				&& target == grid[x - 1][y + 1] && target == grid[x + 1][y + 1]) {
			return new Move(x, y, x, y + 1);
		}

		// x - x
		// - X -
		if (x > 0 && x < GRID_SIZE - 1 && y > 0 && target == grid[x - 1][y - 1]
				&& target == grid[x + 1][y - 1]) {
			return new Move(x, y, x, y - 1);
		}

		// x -
		// - X
		// x -
		if (x > 0 && y > 0 && y < GRID_SIZE - 1 && target == grid[x - 1][y - 1]
				&& target == grid[x - 1][y + 1]) {
			return new Move(x, y, x - 1, y);
		}

		// - x
		// X -
		// - x
		if (x < GRID_SIZE - 1 && y > 0 && y < GRID_SIZE - 1
				&& target == grid[x + 1][y - 1] && target == grid[x + 1][y + 1]) {
			return new Move(x, y, x + 1, y);
		}

		// x x -
		// - - X
		if (x > 1 && y > 0 && target == grid[x - 2][y - 1]
				&& target == grid[x - 1][y - 1]) {
			return new Move(x, y, x, y - 1);
		}

		// - x x
		// X - -
		if (x < GRID_SIZE - 2 && y > 0 && target == grid[x + 2][y - 1]
				&& target == grid[x + 1][y - 1]) {
			return new Move(x, y, x, y - 1);
		}

		// X - -
		// - x x
		if (x < GRID_SIZE - 2 && y < GRID_SIZE - 1
				&& target == grid[x + 1][y + 1] && target == grid[x + 2][y + 1]) {
			return new Move(x, y, x, y + 1);
		}

		// - - X
		// x x -
		if (x > 1 && y < GRID_SIZE - 1 && target == grid[x - 1][y + 1]
				&& target == grid[x - 2][y + 1]) {
			return new Move(x, y, x, y + 1);
		}

		// - X
		// x -
		// x -
		if (x > 0 && y < GRID_SIZE - 2 && target == grid[x - 1][y + 1]
				&& target == grid[x - 1][y + 2]) {
			return new Move(x, y, x - 1, y);
		}

		// X -
		// - x
		// - x
		if (x < GRID_SIZE - 1 && y < GRID_SIZE - 2
				&& target == grid[x + 1][y + 1] && target == grid[x + 1][y + 2]) {
			return new Move(x, y, x + 1, y);
		}

		// x -
		// x -
		// - X
		if (x > 0 && y > 1 && target == grid[x - 1][y - 1]
				&& target == grid[x - 1][y - 2]) {
			return new Move(x, y, x - 1, y);
		}

		// - x
		// - x
		// X -
		if (x < GRID_SIZE - 1 && y > 1 && target == grid[x + 1][y - 1]
				&& target == grid[x + 1][y - 2]) {
			return new Move(x, y, x + 1, y);
		}

		return null;
	}

	/**
	 * Whether the solver should start from the bottom.
	 * 
	 * @return True if bot should look from the bottom to the top.
	 */
	public boolean isBottomsUp() {
		return bottomsUp;
	}

	public void setBottomsUp(boolean bottomsUp) {
		this.bottomsUp = bottomsUp;
	}

	/**
	 * Sets the grid frame is you want it to update whenever bot player updates.
	 * 
	 * @param frame
	 */
	public void setGridFrame(GridFrame frame) {
		this.gridFrame = frame;
	}

	public boolean isRunning() {
		return running;
	}
}
