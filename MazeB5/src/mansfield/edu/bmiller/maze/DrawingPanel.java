package mansfield.edu.bmiller.maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * MVC View: displays a panel that draws the maze and player pieces. 
 * Called from AView.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 * @author Bryan Miller
 */
public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 4417547627470770942L;
	private MazeBoard board;
	private MazeShape human;
	private MazeRobot robot;
	private int mazeSquareSize;
	

	/**
	 * The constructor sets the size and color of the panel.
	 */
	public DrawingPanel() {
		setPreferredSize(new Dimension(MazeApp.MAZEWIDTH,
				MazeApp.MAZEHEIGHT));
		setBackground(Color.BLACK);

	}

	/**
	 * Called to sync the main objects should the controller change them.
	 * 
	 * @param board
	 * @param human
	 * @param robot
	 */
	public void setup(MazeBoard board, MazeShape human,
			MazeRobot robot) {
		this.board = board;
		this.human = human;
		this.robot = robot;
		calcMazeSquareSize();
		
	}

	/**
	 * Repaints the drawing panel.
	 */
	public void refresh() {
		repaint();
	}

	/**
	 * Draws the maze board and the players pieces.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		drawPlayer(robot, g);
		drawPlayer(human, g);
		if (AController.getGameOver() == true)
			drawWinner(g);
		if (AController.getGameOver()== false && GameTimer.zero == true)
			drawLoss(g);
	}

	/**
	 * Calculates the size to draw each maze square given the display size 
	 * and the maze size.
	 */
	private void calcMazeSquareSize() {
		int nomWidth = MazeApp.MAZEWIDTH / (board.getEndCol() - 1);
		int nomHeight = MazeApp.MAZEHEIGHT / (board.getEndRow() - 1);
		mazeSquareSize = nomWidth < nomHeight ? nomWidth : nomHeight;
	}

	/**
	 * Determines how to color a given maze square.
	 */
	private Color getMazeColor(char c) {
		if (c == '-' || c == 's')
			return (new Color(200, 200, 255));
		else if (c == 'e')
			return (new Color(0, 255, 0));
		else if (c == 'x')
			return (new Color(20, 20, 200));
		return new Color(0, 0, 0);
	}

	/**
	 * Draws the maze board on the screen.
	 * 
	 * @param g
	 */
	public void drawBoard(Graphics g) {
		for (int row = 1; row < board.getEndRow(); row++) {
			for (int col = 1; col < board.getEndCol(); col++) {
				g.setColor(getMazeColor(board.getChar(row, col)));
				int x1 = (col - 1) * mazeSquareSize;
				int x2 = (row - 1) * mazeSquareSize;
				g.fillRect(x1, x2, mazeSquareSize, mazeSquareSize);
			}
		}
	}

	/**
	 * Draws a message when there is a winner.
	 * 
	 * @param g
	 */
	public void drawWinner(Graphics g) {
		Font font = new Font("Arial", Font.BOLD, 200);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("WINNER!", 80, 350);
	}
	
	/**
	 * Draws a message when the timer ends.
	 * 
	 * @param g
	 */
	public void drawLoss(Graphics g) {
		Font font = new Font("Arial", Font.BOLD, 150);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString("GAMEOVER!", 80, 350);
	}
	
	/**
	 * Draws a player's icon on the screen.
	 * 
	 * @param ms
	 * @param g
	 */
	public void drawPlayer(MazeShape ms, Graphics g) {
		String name = ms.getName();
		g.setColor(ms.getColor());
		int startX = (ms.getCol() - 1) * mazeSquareSize;
		int startY = (ms.getRow() - 1) * mazeSquareSize;
		int size = mazeSquareSize;

		if (name.equals("circle")) {
			g.fillOval(startX, startY, size, size);
		} else if (name.equals("triangle")) {
			int x2 = startX + mazeSquareSize / 2;
			int x3 = startX + mazeSquareSize;
			int y2 = startY + mazeSquareSize;
			int[] xPoints = { x2, x3, startX, x2 };
			int[] yPoints = { startY, y2, y2, startY };
			int nPoints = 4;
			g.fillPolygon(xPoints, yPoints, nPoints);
		} else if (name.equals("puppy")) {
			MazePuppy mp = (MazePuppy) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("kitten")) {
			MazeKitten mp = (MazeKitten) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("penguin")) {
			MazePenguin mp = (MazePenguin) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("robot")) {
			MazeRobot mr = (MazeRobot) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mr.getSize();
			g.drawImage(mr.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		}
	}
}
