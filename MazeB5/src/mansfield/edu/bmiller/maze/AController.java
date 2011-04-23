package mansfield.edu.bmiller.maze;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * MVC Controller: receives a handle to the View and controls everything
 * that happens as the program runs. It maintains a separate thread that 
 * allows for simple animation. In addition, AController handles all of 
 * the events generated when buttons are clicked or keys are pressed.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 * @author Bryan Miller
 */
public class AController implements Runnable {
	private AView view;
	private MazeBoard board;
	private MazeBoardFiles mazeFiles;
	private MazeMidi midi;
	private MazeMidiFiles midiFiles;
	private MazeSound sound;
	private MazeShape human;
	private MazeRobot robot;
	private Thread runner;
	private static boolean gameOver=true;
	private GameTimer gameTimer;
	
	/**
	 * The animation delay is in milliseconds. The value should be >= to 1.
	 */
	private int delay = 300;
	
	
	/**
	 * gameOver needs to be public static so that it can be read from
	 * the View.
	 */
	//public static boolean gameOver = false;
	
	/**
	 * The constructor for the AController class. It is called from MazeApp.
	 * 
	 * @param view the MVC view is passed in from MazeApp.java. This allows
	 * the controller to interact with any part of the view or controlPanel 
	 * or drawingPanel. 
	 */
	public AController(AView view) {
		this.view = view;
		
		mazeFiles = new MazeBoardFiles();
		midiFiles = new MazeMidiFiles();
		gameTimer = new GameTimer();
		setupBoardAndPlayers();
		setupListeners();		
		runner = new Thread(this);
		runner.start();
		
	}
	
	/**
	 * Used to set up a new board, player pieces, sounds, music, and initialize count down timer.
	 */
	public void setupBoardAndPlayers() {
		gameOver = true;
		board = new MazeBoard(mazeFiles.getMazeFileName());
		int r = board.getStartRow();
		int c = board.getStartCol();
		robot = new MazeRobot(r, c);
		String shape = "circle";
		if(human!=null) shape = view.controlPanel.getPlayerShapeName();
		setNewShape(r, c, shape);
		sound = new MazeSound();
		if (midi != null) midi.stop();
		midiFiles.randomSong();
		midi = new MazeMidi(midiFiles.getMidiFileName());
		gameTimer.timeRemaining = 30;
		gameTimer.win = false;
		GameTimer.zero = false;
		refresh();
		gameOver = false;
	}
	
	/**
	 * Creates a new MazeShape object for the human player.
	 * @param r the board row the object will sit on
	 * @param c the board column the object will sit on
	 * @param shape the shape the object will take
	 */
	private void setNewShape(int r, int c, String shape) {
		if (shape.equals("circle")) human = new MazeCircle(r, c);
		else if (shape.equals("triangle")) human = new MazeTriangle(r, c);
		else if (shape.equals("puppy")) human = new MazePuppy(r, c);
		else if (shape.equals("kitten")) human = new MazeKitten(r, c);
		else if (shape.equals("penguin")) human = new MazePenguin(r, c);
		human.setColor(view.controlPanel.getPlayerColorName());
		view.drawingPanel.setup(board, human, robot);
	}
	
	public static boolean getGameOver() {
		return gameOver;
	}
	
	/**
	 * Repaints the drawingPanel and returns focus to the frame which
	 * listens for key presses.
	 * @return 
	 */
	private void refresh() {
		view.drawingPanel.refresh();
		view.frame.requestFocus();
	}
	
	/**
	 * Checks to see if the someone has reached the maze end.
	 */
	private void checkFinish() {
		if (board.isFinish(human.getRow(), human.getCol()) == true ||
				board.isFinish(robot.getRow(), robot.getCol())) {
			gameOver = true;
			sound.cheer();
			gameTimer.win = true;
			mazeFiles.nextMaze();			
		}

	}
	
	
	/**
	 * Adds animation to the game allowing a robot object to move around
	 * on the screen.
	 */
	@Override
	public void run() {
		while(true) {
			if(!gameOver) {
				robot.moveRobot(board);
				checkFinish();
				view.drawingPanel.refresh();
			}
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) { }
		}
	}

	/**
	 * Adds listeners to the controls on the controlPanel. This is 
	 * necessary since the View is created before the Controller. 
	 * Hence, after the Controller is created we add the Controller's
	 * listeners back on to the View's controls using these callbacks.
	 */
	private void setupListeners() {
		view.addMenu1MIListener(new Menu1MIListener());
		view.addKeyListener(new MyKeyListener());
		view.controlPanel.addLastBtnActionListener(
				new LastBtnActionListener());
		view.controlPanel.addNextBtnActionListener(
				new NextBtnActionListener());
		view.controlPanel.addAgainBtnActionListener(
				new AgainBtnActionListener());
		view.controlPanel.addPlayerColorCBActionListener(
				new PlayerColorCBActionListener());
		view.controlPanel.addPlayerShapeCBActionListener(
				new PlayerShapeCBActionListener());
		
	}
	
	private class Menu1MIListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			view.controlPanel.setNameTF("Hello");
		}
	}
	
	
	/**
	 * When lastBtn is clicked the previous (simpler) maze file is loaded and
	 * the game is restarted.
	 */
	private class LastBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			mazeFiles.lastMaze();
			setupBoardAndPlayers();
		}
	}
	
	/** 
	 * When nextBtn is clicked the next (harder) maze file is loaded and the
	 * game is restarted.
	 */
	private class NextBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			mazeFiles.nextMaze();
			setupBoardAndPlayers();
		}
	}
	
	/**
	 * When the againBtn is clicked the same maze game is started over.
	 */
	private class AgainBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setupBoardAndPlayers();
		}
	}

	/**
	 * The color combo box allows the player to choose different colors for
	 * the simple shape objects that represent the player on the screen.
	 */
	private class PlayerColorCBActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			human.setColor(view.controlPanel.getPlayerColorName());
			refresh();
		}
	}

	/**
	 * The shape combo box allows the player to choose different shapes or
	 * images to represent the player on the screen.
	 */
	private class PlayerShapeCBActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setNewShape(human.getRow(), human.getCol(), 
					view.controlPanel.getPlayerShapeName());
			refresh();
		}
	}
 
	/**
	 * Detects when arrow keys are pressed and moves the player icon through
	 * the maze. Sounds are used to indicate whether the maze wall is hit.
	 */
	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getKeyCode();
			boolean hitWall = true;
			int r = human.getRow();
			int c = human.getCol();

				
			if (keyCode == KeyEvent.VK_RIGHT) {
				if (board.isValidMove(r, c+1) == true) {
					human.moveEast();
					hitWall = false;					
				}			
			}
			else if (keyCode == KeyEvent.VK_LEFT) {
				if (board.isValidMove(r, c-1) == true) {
					human.moveWest();
					hitWall = false;
				}
			}
			else if (keyCode == KeyEvent.VK_UP) {
				if (board.isValidMove(r-1, c) == true) {
					human.moveNorth();
					hitWall = false;
				}
			}
			else if (keyCode == KeyEvent.VK_DOWN) {
				if (board.isValidMove(r+1, c) == true) {
					human.moveSouth();
					hitWall = false;
				}
			}
			
			
			if (hitWall == true)
				sound.blip();
			else 
				sound.pop();
			 
			refresh();
		}
	}
}