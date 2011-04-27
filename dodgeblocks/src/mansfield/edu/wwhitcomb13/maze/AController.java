package mansfield.edu.wwhitcomb13.maze;

import java.awt.event.*;

/**
 * MVC Controller: receives a handle to the View and controls everything
 * that happens as the program runs. It maintains a separate thread that 
 * allows for simple animation. In addition, AController handles all of 
 * the events generated when buttons are clicked or keys are pressed.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips edited by William Whitcomb
 */
public class AController implements Runnable {
	private static int type=0;
	private AView view;
	private MazeBoard board;
	private MazeBoardFiles mazeFiles;
	private MazeMidi midi;
	private MazeMidiFiles midiFiles;
	private MazeSound sound;
	private MazeShape human, humanR, humanL, humanB;
	private MazeRobot robot;
	private MazeDog dog;
	private MazeCrok crock;
	private Thread runner;
	private static boolean gameOver = false;
	/**
	 * The animation delay is in milliseconds. The value should be >= to 1.
	 */
	private int delay = 600;
		
	/**
	 * gameOver needs to be public static so that it can be read from
	 * the View.
	 */
	
	
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
		setupBoardAndPlayers();
		setupListeners();
		runner = new Thread(this);
		runner.start();
	}
	
	/**
	 * Used to set up a new board, player pieces, sounds, and music.
	 * Starts maze with the kitty shape. robot is placed at 10,15.
	 */
	private void setupBoardAndPlayers() {
		gameOver = true;
		board = new MazeBoard(mazeFiles.getMazeFileName());
		int r = board.getStartRow();
		int c = board.getStartCol();
		robot = new MazeRobot(r+9, c+9);
		dog = new MazeDog(r+5,c+5);
		crock = new MazeCrok(r+7,c+7);
		String shape = "kitty";//starts maze with the kitty shape
		//if(human!=null) shape = view.controlPanel.getPlayerShapeName();
		setNewShape(r, c, shape);
		sound = new MazeSound();
		if (midi != null) midi.stop();
		midiFiles.randomSong();
		midi = new MazeMidi(midiFiles.getMidiFileName());	
		refresh();
		human.setLives(3);
		gameOver = false;
	}
	
	/**
	 * Creates a new MazeShape object for the human player.
	 * @param r the board row the object will sit on
	 * @param c the board column the object will sit on
	 * @param shape the shape the object will take
	 */
	private void setNewShape(int r, int c, String shape) {
		//if (shape.equals("circle")) human = new MazeCircle(r, c);
		//else if (shape.equals("triangle")) human = new MazeTriangle(r, c);
		//else if (shape.equals("puppy")) human = new MazePuppy(r, c);
		//else if (shape.equals("kitty"))
			//{
			human = new MazeKitten(r, c);
			humanL = new MazeKittenL(r, c);
			humanR = new MazeKittenR(r, c);
			humanB = new MazeKittenB(r, c);
			//}
		//human.setColor(view.controlPanel.getPlayerColorName());
		view.drawingPanel.setup(board, human, humanB, humanR, humanL, robot, dog, crock);
	}
	
	/**
	 * Repaints the drawingPanel and returns focus to the frame which
	 * listens for key presses.
	 */
	private void refresh() {
		view.drawingPanel.refresh();
		view.frame.requestFocus();
	}
	public static int getType()
	{
		return type;
	}
	public static boolean getGameOver()
	{
		return gameOver;
	}
	public static void setGameOver()
	{
		gameOver = true;
	}
	/**
	 * Checks to see if the human has caught the robot 
	 */
	private void checkFinish() 
	{
		if ((human.getRow() == robot.getRow()) && (human.getCol()) ==  robot.getCol())
		{
			gameOver = true;
			sound.cheer();
		}
		if (((human.getRow() == crock.getRow()) && (human.getCol() ==  crock.getCol()))
				|| ((human.getRow() == dog.getRow()) && (human.getCol() == dog.getCol())))
		{
			sound.boo();
			human.setRow(1);
			human.setCol(1);
			humanB.setRow(1);
			humanB.setCol(1);
			humanL.setRow(1);
			humanL.setCol(1);
			humanR.setRow(1);
			humanR.setCol(1);
			human.setLives(human.getLives()-1);
		}
	}
	
	/**
	 * Adds animation to the game allowing a robot object to move around
	 * on the screen. Checks if the robot has been caught.
	 */
	@Override
	public void run() {
		
		while(true) {
			if(!gameOver) {
				crock.moveCrock(board);
				dog.moveDog(board);
				robot.moveRobot(board);
				checkFinish();//just in case the robot runs into the human
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
		
		view.controlPanel.addMuteBtnActionListener(
				new MuteBtnActionListener());
		view.controlPanel.addSongBtnActionListener(
				new SongBtnActionListener());
		view.controlPanel.addLastBtnActionListener(
				new LastBtnActionListener());
		view.controlPanel.addNextBtnActionListener(
				new NextBtnActionListener());
		view.controlPanel.addAgainBtnActionListener(
				new AgainBtnActionListener());
		
		//view.controlPanel.addPlayerColorCBActionListener(
			//	new PlayerColorCBActionListener());
		//view.controlPanel.addPlayerShapeCBActionListener(
				//new PlayerShapeCBActionListener());
	}
	private class Menu1MIListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			view.controlPanel.setNameTF("Hello");
		}
	}
	/**
	 * When lastBtn is clicked the delay is increased
	 */
	private class LastBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			delay +=50;
			refresh();
		}
	}
	
	/** 
	 * When nextBtn is clicked the delay is decreased by 50 down to a min of 100
	 * 
	 */
	private class NextBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (delay > 250)
			{
				delay -=50;
				refresh();
			}
			else
				refresh();
		}
	}
	
	/**
	 * When the againBtn is clicked the game is started over.
	 */
	private class MuteBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			midi.stop();
			refresh();
		}
	}
	private class SongBtnActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (midi != null) midi.stop();
			midiFiles.randomSong();
			midi = new MazeMidi(midiFiles.getMidiFileName());	
			refresh();
		}
	}
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
	 * Detects when arrow keys are pressed and moves the player icon around the map
	 * Sounds are used to indicate collisions.  Tests to see if the robot was caught
	 * after moving
	 */
	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getKeyCode();
			boolean hitWall = true;
			int r = human.getRow();
			int c=0;
			
			if (human.getCol() + DrawingPanel.getFront() > (board.getEndCol() - 1))
				c = human.getCol() + DrawingPanel.getFront()-((board.getEndCol() - 1));
			else c = human.getCol() + DrawingPanel.getFront();	
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				if ((board.isValidMove(r, c+1) == true) && (human.getCol() <= board.getEndCol()-11))
				{	
					type = 2;
					human.moveEast();
					if (human.getName()=="kitty")
					{
						humanB.moveEast();
						humanR.moveEast();
						humanL.moveEast();
					}
					hitWall = false;
				}			
			}
			else if ((keyCode == KeyEvent.VK_LEFT) && (human.getCol() > 1)) {
				if (board.isValidMove(r, c-1) == true) {
					type=1;
					human.moveWest();
					if (human.getName()=="kitty")
					{
						humanB.moveWest();
						humanL.moveWest();
						humanR.moveWest();
					}
					hitWall = false;
				}
			}
			else if (keyCode == KeyEvent.VK_UP) {
				if ((board.isValidMove(r-1, c) == true)&& (human.getRow() > 1))
				{
					type=3;
					human.moveNorth();
					if (human.getName()=="kitty")
					{
						humanB.moveNorth();
						humanL.moveNorth();
						humanR.moveNorth();
					}
					hitWall = false;
				}
			}
			else if (keyCode == KeyEvent.VK_DOWN) {
				if ((board.isValidMove(r+1, c) == true) && (human.getRow() < board.getEndRow()-2))
				{
					type =0 ;
					human.moveSouth();
					if (human.getName()=="kitty")
					{
						humanB.moveSouth();
						humanL.moveSouth();
						humanR.moveSouth();
					}
					hitWall = false;
				}
			}			
			if (hitWall == true)
			{
				if (human.getRow() == 1 && human.getCol() == 1)
				{
					human.setLives(human.getLives());
				}
				else human.setLives(human.getLives()-1);
				type=0;
				sound.boo();
				human.setRow(1);
				human.setCol(1);
				if (human.getName()=="kitty")
				{
					humanB.setRow(1);
					humanB.setCol(1);
					humanL.setRow(1);
					humanL.setCol(1);
					humanR.setRow(1);
					humanR.setCol(1);
				}
			}
			else 
				sound.pop();
			checkFinish();//see if we caught the robot after we moved
			refresh();
		}
	}
}