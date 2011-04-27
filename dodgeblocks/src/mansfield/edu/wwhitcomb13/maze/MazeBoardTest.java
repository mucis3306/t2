package mansfield.edu.wwhitcomb13.maze;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazeBoardTest {
	MazeBoard board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		board = new MazeBoard("m1000.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStartRow() {
		assertTrue(board.getStartRow() == 1);
	}

	@Test
	public void testGetStartCol() {
		assertTrue(board.getStartCol() == 1);
	}

	@Test
	public void testGetChar() {
		assertTrue(board.getChar(1,1) == '-');
		}

	@Test
	public void testGetEndRow() {
		assertTrue(board.getEndRow() == 13);
	}

	@Test
	public void testGetEndCol() {
		assertTrue(board.getEndCol() == 29);
	}

}
