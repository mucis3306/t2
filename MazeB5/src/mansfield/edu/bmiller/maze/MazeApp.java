package mansfield.edu.bmiller.maze;


/**
 * Starting point for a simple maze game for young children.
 * The program is written following the MVC (Model View Controller) design 
 * pattern. MazeApp.java was written in January and February of 2011. 
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * Last updated: 2/13/2011
 * 
 * @author John Phillips
 * @author Bryan Miller
 * @version 1.0
 */
public class MazeApp {
	public static int MAZEWIDTH = 1000;
	public static int MAZEHEIGHT = 600;

	/**
	 * The constructor starts a new thread that will handle user 
	 * interface events. It then creates a view object and passes it
	 * to a controller object that then manages the game play.
	 */
	public MazeApp() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AView view = new AView();
				new AController(view);
			}
		});

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		new MazeApp();
		
	}
}

