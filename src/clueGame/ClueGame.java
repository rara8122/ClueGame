/*
 * Class: This class creates the entire Jframe clue game gui with the board panel, clue cards panel, and game control panels. The clue cards panel and game control panel are in their own seperate classes. We extended JPanel on the existing board class to create the board panel. All the  panels were incorporated into this class to create he full clue game frame.  
 * Author: Rachel Davy, Melanie Perez
 * Date: 11/13/2023
 * Collaborators: none
 * Sources: https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/JOptionPane.html
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{
	
	private Board theBoard;
	private ClueCardsPanel cards;
	private GameControlPanel control;
	public static final int CELL_SIZE = 45;
	public static final int RATIO = 7;
	
	/*
	 * Constructor for class 
	 */
	public ClueGame(Board newBoard, ClueCardsPanel newcards, GameControlPanel newcontrol) {
		super();
		
		theBoard =  newBoard;
		cards = newcards;
		control = newcontrol;
		initializeMainFrame(); 		
		
		showSplashScreen(theBoard.getUser()); 
			
	}
	
	/*
	 * method to initialize the main frame with all the panels and splash screen
	 */
	private void initializeMainFrame() {
		super.add(theBoard, BorderLayout.CENTER);
		super.add(cards, BorderLayout.EAST);
		super.add(control, BorderLayout.SOUTH);
		super.setSize((RATIO + 1)*(CELL_SIZE*theBoard.getNumColumns())/RATIO, (RATIO + 1)*(CELL_SIZE*theBoard.getNumRows())/RATIO);  // size the frame
		cards.setPreferredSize(new Dimension ((CELL_SIZE*theBoard.getNumColumns())/RATIO, (RATIO + 1)*(CELL_SIZE*theBoard.getNumRows())/RATIO));
		control.setPreferredSize(new Dimension ((RATIO + 1)*(CELL_SIZE*theBoard.getNumColumns())/RATIO, (CELL_SIZE*theBoard.getNumRows())/RATIO));
		control.setGuessSize();
		theBoard.setPreferredSize(new Dimension (CELL_SIZE*theBoard.getNumColumns(), CELL_SIZE*theBoard.getNumRows()));
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		super.setVisible(true); // make it visible
		
	}
	
	/*
	 * presents the splash screen at the beginning of the game
	 */
	private void showSplashScreen(HumanPlayer user) {
		String message = "You are " + user.getName() + ". " + "Can you find the solution before the computer players?";
        JOptionPane.showMessageDialog(this,message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }
	
}
