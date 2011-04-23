package mansfield.edu.bmiller.maze;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * MVC Model: abstract class for player shape and image icons. Keeps
 * track of position of icon in the maze.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public abstract class MazeShape {
	private int row;
	private int col;
	private Color color;
	private String name;

	public MazeShape(int myRow, int myCol, String myName) {
		row = myRow;
		col = myCol;
		name = myName;
		setColor("red");
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public void setRow(int r) {
		row = r;
	}

	public void setCol(int c) {
		col = c;
	}

	public void setColor(Color c) {
		color = c;
	}

	public void setColor(String colorName) {
		try {
			Field field = Class.forName("java.awt.Color")
					.getField(colorName);
			color = (Color) field.get(null);
		} catch (Exception ex) {
			color = Color.RED;
		}
	}

	public void moveNorth() {
		row -= 1;
	}

	public void moveSouth() {
		row += 1;
	}

	public void moveEast() {
		col += 1;
	}

	public void moveWest() {
		col -= 1;
	}

	@Override
	public String toString() {
		return ("Row = " + row + " Column = " + col + " Color = " + color);
	}
}
