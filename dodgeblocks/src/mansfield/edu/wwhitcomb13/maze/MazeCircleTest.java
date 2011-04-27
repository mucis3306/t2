package mansfield.edu.wwhitcomb13.maze;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MazeCircleTest {
	MazeCircle circle;
	@Before
	public void setUp() throws Exception {
		circle = new MazeCircle(1,1);
	}
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetRow() {
		assertTrue(circle.getRow() == 1 );
	}

	@Test
	public void testGetCol() {
		assertTrue(circle.getCol() == 1 );
	}

	@Test
	public void testGetName() {
		assertTrue(circle.getName() == "circle" );
	}

	@Test
	public void testSetRow() {
		int row  =5;
		circle.setRow(row);
		assertTrue(circle.getRow() == 5);
	}

	@Test
	public void testSetCol() {
		int col = 5;
		circle.setCol(col);
		assertTrue(circle.getCol() == 5);
	}

	@Test
	public void testMoveNorth() {
		circle.setRow(5);
		circle.moveNorth();
		assertTrue(circle.getRow() == 4);
	}

	@Test
	public void testMoveSouth() {
		circle.setRow(5);
		circle.moveSouth();
		assertTrue(circle.getRow() == 6);
	}

	@Test
	public void testMoveEast() {
		circle.setCol(5);
		circle.moveEast();
		assertTrue(circle.getCol() == 6);
	}

	@Test
	public void testMoveWest() {
		circle.setCol(5);
		circle.moveWest();
		assertTrue(circle.getCol() == 4);
	}

}
