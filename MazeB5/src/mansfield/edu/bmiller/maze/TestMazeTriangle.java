package mansfield.edu.bmiller.maze;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * This is the JUnit test case that tests all the methods
 * within MazeCircle and its superclass MazeShape
 * After testing each method, they all are running correctly
 *  @author Bryan Miller
 */
public class TestMazeTriangle {
	//using the MazeTriangle class
	MazeTriangle mt;
	
	@Before
	public void setUp() throws Exception 
	{
		mt = new MazeTriangle(8,8);
	}

	/**
	 * This method tests the getColor function that is
	 * contained in the MazeShape class
	 */
	@Test
	public void testGetColor() 
	{
		mt.setColor("orange");
		assertTrue(mt.getColor().equals(Color.orange));
	}


	/**
	 * This method tests the SetColor function that is
	 * contained in the MazeShape class, this class receives a color property
	 */
	@Test
	public void testSetColorColor() 
	{
		mt.setColor(Color.blue);
		assertTrue(mt.getColor().equals(Color.blue));
	}
	
	/**
	 * This method tests the setColor function that is
	 * contained in the MazeShape class, this method receives a string instead of a color
	 */
	@Test
	public void testSetColorString() 
	{
		mt.setColor("blue");
		assertTrue(mt.getColor().equals(Color.blue));
	}

	/**
	 * This method tests the moveNorth function that is
	 * contained in the MazeShape class
	 */
	@Test
	public void testMoveNorth() 
	{
		int myrow1 = mt.getRow();
		mt.moveNorth();
		int myrow2 = mt.getRow();
		assertTrue(myrow1 == myrow2 + 1);
	}

	/**
	 * This method tests the moveSouth function that is
	 * contained in the MazeShape class
	 */
	@Test
	public void testMoveSouth() 
	{
		int myrow1 = mt.getRow();
		mt.moveSouth();
		int myrow2 = mt.getRow();
		assertTrue(myrow1 == myrow2 - 1);
	}
	
	/**
	 * This method tests the moveEast function that is
	 * contained in the MazeShape class
	 */
	@Test
	public void testMoveEast() 
	{
		int mycol1 = mt.getCol();
		mt.moveEast();
		int mycol2 = mt.getCol();
		assertTrue(mycol1 == mycol2 - 1);
	}
	
	/**
	 * This method tests the moveWest function that is
	 * contained in the MazeShape class
	 */
	@Test
	public void testMoveWest() 
	{
		int mycol1 = mt.getCol();
		mt.moveWest();
		int mycol2 = mt.getCol();
		assertTrue(mycol1 == mycol2 + 1);
	}

	/**
	 * This method tests the toString function that is
	 * contained in the MazeShape class that displays all 
	 * the information regarding the circle position and color
	 */
	@Test
	public void testToString() 
	{
		int row = mt.getRow();
		int col = mt.getCol();
		Color color = mt.getColor();
		String a = mt.toString();
		String b = "Row = " + row + " Column = " + col + " Color = " + color;
		assertTrue(a.equalsIgnoreCase(b));
		
	}

}

