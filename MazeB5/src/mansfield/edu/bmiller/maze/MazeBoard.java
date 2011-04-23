package mansfield.edu.bmiller.maze;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * MVC Model: generates a maze board as a 2D array of chars.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public class MazeBoard implements MazeBoardInterface {
	private char[][] mArray;
	private ArrayList<Dimension> startList;
	private int startRow = 1;
	private int startCol = 1;
	private int endRow, endCol;
	private String version;
	private static Random gen = new Random();

	public MazeBoard(String fileName) {
		createBoard(fileName);
		endCol = mArray[0].length;
		endRow = mArray.length;
	}

	@Override
	public void createBoard(String fileName) {
		Scanner sc = new Scanner(
				MazeBoard.class.getResourceAsStream("/maze/" + fileName));
		version = sc.nextLine();
		endRow = sc.nextInt();
		sc.nextLine();
		endCol = sc.nextInt();
		sc.nextLine();
		System.out.println("r=" + endRow + "c" + endCol);
		mArray = new char[endRow][endCol];
		for (int r = 0; r < endRow; r++)
			mArray[r] = sc.nextLine().toCharArray();

		makeStartList();
		chooseRandomStart();
	}

	private void makeStartList() {
		startList = new ArrayList<Dimension>();
		for (int row = 1; row < endRow - 1; row++) {
			for (int col = 1; col < endCol - 1; col++) {
				if (mArray[row][col] == 's')
					startList.add(new Dimension(row, col));
			}
		}
		startList.trimToSize();
	}

	private void chooseRandomStart() {
		Dimension dm = startList.get(gen.nextInt(startList.size()));
		startRow = (int) dm.getWidth();
		startCol = (int) dm.getHeight();
	}

	@Override
	public boolean isFinish(int row, int col) {
		if (mArray[row][col] == 'e') {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		char c = mArray[row][col];
		if (c == '-' || c == 's' || c == 'e') {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "startRow" + getStartRow() + " startCol" + getStartCol();
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	@Override
	public int getStartCol() {
		return startCol;
	}

	@Override
	public char getChar(int row, int col) {
		return mArray[row][col];
	}

	@Override
	public int getEndRow() {
		return endRow - 1;
	}

	@Override
	public int getEndCol() {
		return endCol - 1;
	}
}
