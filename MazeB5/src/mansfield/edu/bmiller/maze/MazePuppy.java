package mansfield.edu.bmiller.maze;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * MVC Model: represents a player icon as a puppy image.
 * 
 * Puppy image by John Phillips
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public class MazePuppy extends MazeShape {
	BufferedImage image = null;
	static String name = "puppy";
	static final int size = 90;

	public MazePuppy(int myRow, int myCol) {
		super(myRow, myCol, name);
		try {
			java.net.URL imageURL = MazePuppy.class
					.getResource("/image/puppygrass.jpg");
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
}
