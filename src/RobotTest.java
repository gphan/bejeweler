import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;

public class RobotTest {

	/**
	 * @param args
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		while (true) {
			PointerInfo pInfo = MouseInfo.getPointerInfo();
			Color pixelColor = robot.getPixelColor(pInfo.getLocation().x, pInfo.getLocation().y);
			System.out.println(
					String.format("X: %d Y: %d R: %d G: %d B: %d",
							pInfo.getLocation().x,
							pInfo.getLocation().y,
							pixelColor.getRed(),
							pixelColor.getGreen(),
							pixelColor.getBlue()
							)
					);
			Thread.sleep(100);
		}
	}

}
