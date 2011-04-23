package mansfield.edu.bmiller.maze;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * MVC Model: represents a robot player that wanders through the maze.
 * 
 * Ladybug.png image is public domain from clker.com at 
 * http://www.clker.com/clipart-3331.html
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public class MazeRobot extends MazeShape {
	private BufferedImage image = null;
	private Random gen = new Random();
	static String name = "robot";
	static final int size = 95;

	public MazeRobot(int myRow, int myCol) {
		super(myRow, myCol, name);
		try {
			java.net.URL imageURL = MazeRobot.class
					.getResource("/image/ladybug.png");
			image = ImageIO.read(imageURL);
		} catch (IOException ioe) {
		}
	}

	public int getSize() {
		return size;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void moveRobot(MazeBoard board) {
		int dir;

		dir = gen.nextInt(4);

		if (dir == 0) {
			if (board.isValidMove(getRow() - 1, getCol()) == true) {
				moveNorth();
			}
		} else if (dir == 1) {
			if (board.isValidMove(getRow(), getCol() + 1) == true) {
				moveEast();
			}
		} else if (dir == 2) {
			if (board.isValidMove(getRow() + 1, getCol()) == true) {
				moveSouth();
			}
		} else if (dir == 3) {
			if (board.isValidMove(getRow(), getCol() - 1) == true) {
				moveWest();
			}
		}
	}
}
