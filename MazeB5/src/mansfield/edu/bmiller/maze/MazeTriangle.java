package mansfield.edu.bmiller.maze;

/**
 * MVC Model: represents a triangle icon.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public class MazeTriangle extends MazeShape {
	static String name = "triangle";

	public MazeTriangle(int myRow, int myCol) {
		super(myRow, myCol, name);
	}
}
