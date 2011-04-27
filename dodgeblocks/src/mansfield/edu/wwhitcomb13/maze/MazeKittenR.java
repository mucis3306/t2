package mansfield.edu.wwhitcomb13.maze;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * MVC Model: represents a player icon as a kitty image.
 * 
 * kitty acquired image public domain by http://www.clipart.com
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips edited by William Whitcomb
 */
public class MazeKittenR extends MazeShape {
	BufferedImage image = null;
	static String name = "kittyR";
	static final int size = 90;

	public MazeKittenR(int myRow, int myCol) {
		super(myRow, myCol, name);
		try {
			java.net.URL imageURL = MazeKittenR.class
					.getResource("/image/orangekittyrighttrans.png");
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
