package mansfield.edu.wwhitcomb13.maze;

/**
 * MVC Model: interface defining methods all board generators will have.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips edited by William Whitcomb
 */
public interface MazeBoardInterface {
	public void createBoard(String fileName);

	public boolean isValidMove(int row, int col);

	public int getStartRow();

	public int getStartCol();

	public int getEndRow();

	public int getEndCol();

	public char getChar(int row, int col);

	public String getVersion();

	@Override
	public String toString();
}
