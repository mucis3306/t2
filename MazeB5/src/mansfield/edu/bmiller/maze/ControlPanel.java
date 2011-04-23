package mansfield.edu.bmiller.maze;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * MVC View: displays a panel containing various controls allowing the 
 * player to control the look and feel of the game.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 * @author Bryan Miller
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 3930070147267264232L;
	private String[] playerShapeStrings = { "circle", "triangle", "puppy", "kitten", "penguin"};
	private String[] playerColorStrings = { "red", "black", "blue", "cyan",
			"gray", "green", "magenta", "orange", "pink", "white", "yellow"};
	JComboBox playerColorCB;
	JComboBox playerShapeCB;
	JTextField nameTF;
	JButton lastBtn, nextBtn, againBtn, countdownBtn;

	/**
	 * The constructor for the ControlPanel class. Called from AView. Places
	 * various controls on the panel.
	 */
	public ControlPanel(){
						
		JLabel nameLbl = new JLabel("Player Name:");
		this.add(nameLbl);

		nameTF = new JTextField("", 12);
		this.add(nameTF);
		
		playerColorCB = new JComboBox(playerColorStrings);
		this.add(playerColorCB);

		playerShapeCB = new JComboBox(playerShapeStrings);
		this.add(playerShapeCB);

		lastBtn = new JButton("Easier Maze");
		this.add(lastBtn);

		nextBtn = new JButton("Harder Maze");
		this.add(nextBtn);

		againBtn = new JButton("Play Again");
		this.add(againBtn);
	}
	
	/**
	 * Callback for the lastBtn ActionListener. Called from AController.
	 * 
	 * @param al
	 */

	public void setNameTF(String name) {
		nameTF.setText(name);
	}
	
	/**
	 * Callback for the lastBtn ActionListener. Called from AController.
	 * 
	 * @param al
	 */
	
	void addLastBtnActionListener(ActionListener al) {
		lastBtn.addActionListener(al);
	}
	
	/**
	 * Callback for the lastBtn ActionListener. Called from AController.
	 * 
	 * @param al
	 */

	void addNextBtnActionListener(ActionListener al) {
		nextBtn.addActionListener(al);
	}

	/**
	 * Callback for the againBtn ActionListener. Called from AController.
	 * 
	 * @param al
	 */
	void addAgainBtnActionListener(ActionListener al) {
		againBtn.addActionListener(al);
	}

	/**
	 * Callback for the player color combo box ActionListener. Called from
	 * AController.
	 * 
	 * @param al
	 */
	void addPlayerColorCBActionListener(ActionListener al) {
		playerColorCB.addActionListener(al);
	}

	/**
	 * Callback for the player shape combo box ActionListener. Called from
	 * AController.
	 * 
	 * @param al
	 */
	void addPlayerShapeCBActionListener(ActionListener al) {
		playerShapeCB.addActionListener(al);
	}

	/**
	 * Gets the player color currently selected by the combo box.
	 * 
	 * @return String colorName
	 */
	public String getPlayerColorName() {
		return ((String) playerColorCB.getSelectedItem());
	}

	/**
	 * Gets the player shape icon currently selected by the combo box.
	 * 
	 * @return String shapeName
	 */
	public String getPlayerShapeName() {
		return ((String) playerShapeCB.getSelectedItem());
	}
	
}
