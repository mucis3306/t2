package mansfield.edu.wwhitcomb13.maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * MVC View: displays a panel that draws the board, player and robot. 
 * Called from AView.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips edited by William Whitcomb
 */
public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 4417547627470770942L;
	private MazeBoard board;
	private MazeSound sound;
	private MazeShape human, humanR, humanL, humanB;
	private MazeRobot robot;
	private MazeDog dog;
	private MazeCrok crock;
	private int mazeSquareSize;
	private static int front =0;
	private int skip =0; 
	private int hidden = 8;
	

	/**
	 * The constructor sets the size and color of the panel.
	 */
	public DrawingPanel() {
		setPreferredSize(new Dimension(MazeApp.MAZEWIDTH,
				MazeApp.MAZEHEIGHT));
		setBackground(Color.WHITE);
	}

	/**
	 * Called to sync the main objects should the controller change them.
	 * 
	 * @param board
	 * @param human
	 * @param robot
	 */
	public void setup(MazeBoard board, MazeShape human, MazeShape humanB, MazeShape humanR, MazeShape humanL,
			MazeRobot robot, MazeDog dog, MazeCrok crock) {
		sound = new MazeSound();
		this.humanB = humanB;
		this.humanR = humanR;
		this.humanL = humanL;
		this.board = board;
		this.human = human;
		this.robot = robot;
		this.crock = crock;
		this.dog = dog;
		human.setLives(3);
		calcMazeSquareSize();
	}

	/**
	 * Repaints the drawing panel.
	 */
	public void refresh() {
		repaint();
	}
	/**
	 * Draws the board, player and robot
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		delayFront();
		super.paintComponent(g);
		drawBoard(g);
		drawPlayer(robot, g);
		drawPlayer(dog, g);
		drawPlayer(crock, g);
		if (AController.getType() == 0)
			drawPlayer(human, g);
		else if (AController.getType() == 1)
			drawPlayer(humanL, g); 
		else if (AController.getType() == 2)
			drawPlayer(humanR, g);
		else if (AController.getType() == 3)
			drawPlayer(humanB, g);
		if ((AController.getGameOver()) && (human.getLives() >0))
			drawWinner(g);
		else if (human.getLives() == 0)
		{
			drawSorry(g);
			AController.setGameOver();
		}
	}
	/**
	 * Calculates the size to draw each maze square given the display size 
	 * and the maze size.
	 */
	private void calcMazeSquareSize() {
		int nomWidth = MazeApp.MAZEWIDTH / (board.getEndCol() - (hidden-1));
		int nomHeight = MazeApp.MAZEHEIGHT / (board.getEndRow() - 1);
		mazeSquareSize = nomWidth < nomHeight ? nomWidth : nomHeight;
	}
	/**Returns the value of front
	 * 
	 * @return int
	 */
	public static int getFront()
	{
		return front;
	}
	/**
	 * Increments the variable front every 4th time. this enables the
	 * board to move 4x slower then the AI object 
	 * 
	 */
	public void delayFront()
	{
		int temp=0;
		if (this.skip < 3)
		{
			this.skip++;
			if (this.skip == 3)
			{
				if (front == (board.getEndCol() - 1))
					front=-1;
				front++;
				if (front + human.getCol() >  (board.getEndCol() - 1))
					temp = front + human.getCol() - (board.getEndCol() - 1);
				else
					temp = front + human.getCol();
				if(board.getChar(human.getRow(), temp) == 'x')
				{	
					if (human.getCol() == 1 && human.getRow()==1)
						human.setLives(human.getLives());
					else human.setLives(human.getLives()-1);
					humanR.setCol(1);
					humanR.setRow(1);	
					humanL.setCol(1);
					humanL.setRow(1);
					humanB.setCol(1);
					humanB.setRow(1);
					human.setCol(1);
					human.setRow(1);
					
					sound.boo();
				}
			}
		}
		else
			this.skip = 0;
	}
	/**
	 * Determines how to color a given maze square. shift
	 * is used to vary colors according to position on the board
	 * @param c
	 * @param shift
	 */
	private Color getMazeColor(char c, int shift) {
		if (c == '-' )
			return (new Color((shift*3), (150-shift), (255-(shift*2))));
		else if (c == 'x')
			return (new Color(((shift)), (shift%255), (255 - (shift*8))));
		return new Color(0, 0, 0);
	}
	/**
	 * Draws the dodge board on the screen using a circular array tracked by front
	 * 
	 * @param g
	 */
	public void drawBoard(Graphics g)
	{
		char [][] msArray;
		msArray = new char[board.getEndRow()][(board.getEndCol() - hidden)];
		int temp=0;
		for (int r = 0; r < (board.getEndRow()-1);r++)
		{
			for (int c = 0; c < (board.getEndCol() - hidden);c++)
			{
				if (front + c >  (board.getEndCol() - 1))
					temp = front + c - (board.getEndCol() - 1);
				else temp = front + c;
				msArray[r][c] = board.getChar(r, temp);
			}
		}
		for (int r = 0; r < board.getEndRow(); r++)
		{
			for (int c = 0; c < (board.getEndCol()-hidden); c++) 
			{
				g.setColor(getMazeColor(msArray[r][c], c));
				int x1 = (c - 1) * mazeSquareSize;
				int x2 = (r- 1) * mazeSquareSize;
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
	public void drawSorry(Graphics g) {
		Font font = new Font("Arial", Font.BOLD, 200);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("Lost", 80, 350);
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
		} else if (name.equals("kitty")) {
			MazeKitten mp = (MazeKitten) ms;////type casting
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("kittyR")) {
			MazeKittenR mp = (MazeKittenR) ms;////type casting
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("kittyL")) {
			MazeKittenL mp = (MazeKittenL) ms;////type casting
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mp.getSize();
			g.drawImage(mp.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		} else if (name.equals("kittyB")) {
			MazeKittenB mp = (MazeKittenB) ms;////type casting
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
		else if (name.equals("robotdog")) {
			MazeDog mr = (MazeDog) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mr.getSize();
			g.drawImage(mr.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		}
		else if (name.equals("robotcrock")) {
			MazeCrok mr = (MazeCrok) ms;
			int dx2 = startX + mazeSquareSize;
			int dy2 = startY + mazeSquareSize;
			int srcSize = mr.getSize();
			g.drawImage(mr.getImage(), startX, startY, dx2, dy2, 0,
					0, srcSize, srcSize, null);
		}
	}
}
