package mansfield.edu.bmiller.maze;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Font;

/**
 * MVC View: sets up a GUI view for the application. It creates a frame and 
 * main panel. On the main panel goes a drawingPanel that will hold the 
 * maze. In addition, a control panel is placed at the bottom of the 
 * main panel.  **Added a second GUI as a count down timer to enhance
 * the user experience 
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author Bryan Miller
 */

/**
 * Setup timer class, GUI, and initialize global variables and timer.
 */
public class GameTimer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel clockLbl;
	Timer countdownTimer;    // Initial game time
	public int timeRemaining = 30;
	public boolean win; 
	public static boolean zero;

	/**
	 * Setup timer GUI and timer listener and start the timer.
	 */	
	public GameTimer(){
		new JFrame("MAZE GAME");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(210, 100);
		setLocation(800,685);
		clockLbl = new JLabel(String.valueOf(timeRemaining), JLabel.CENTER);
		clockLbl.setFont(new Font("sansserif", Font.BOLD, 32));
		this.add(clockLbl);
		countdownTimer = new Timer(1000, new CountdownTimerListener());
		setVisible(true);
		countdownTimer.start();    }

	/**
	 * Display timer while counting, stop timer if player wins, and if timer ends display game over.
	 */  
	class CountdownTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (--timeRemaining > 0 && win == false) {
			clockLbl.setText(String.valueOf(timeRemaining));			
			}
			
			else if (win == true){
				clockLbl.setText("You Win");
			}
			else{
				zero = true;
				clockLbl.setText("GameOver");
			}
		}
	}
	  
}
	


