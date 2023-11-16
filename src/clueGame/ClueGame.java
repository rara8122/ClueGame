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
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{
	
	Board theBoard;
	ClueCardsPanel cards;
	GameControlPanel control;
	/*
	 * Constructor for cluegame
	 */
	public ClueGame(Board newBoard, ClueCardsPanel newcards, GameControlPanel newcontrol) {
		super();
		
		theBoard =  newBoard;
		cards = newcards;
		control = newcontrol;
		
		initializeMainFrame(); 
		
		HumanPlayer user = theBoard.getUser();
		for(Card card : user.getDeck()) {
			System.out.println(card.getCardName());
		}
		cards.updateDeckCards(user.getDeck(), user.getColor());
		cards.updateSeenCards(user.getSeen());
		
		control.setTurn(user);
		control.setGuess( "I have no guess!");
		control.setGuessResult( "So you have nothing?");
		control.setRoll(5);
		
		showSplashScreen(user); 
			
	}
	
	private void initializeMainFrame() {
		super.add(theBoard, BorderLayout.CENTER);
		super.add(cards, BorderLayout.EAST);
		super.add(control, BorderLayout.SOUTH);
		super.setSize(1500,1000 );  // size the frame
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		super.setVisible(true); // make it visible
		
	}
	private void showSplashScreen(HumanPlayer user) {
		String message = "You are " + user.getName() + ". " + "Can you find the solution before the computer players?";
        JOptionPane.showMessageDialog(this,message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }

	public static void main(String[] args) {
		Board theBoard = Board.getInstance();
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		theBoard.initialize();
		ClueCardsPanel cards = new ClueCardsPanel();  // create the panel
		GameControlPanel control = new GameControlPanel();  // create the panel
		ClueGame frame = new ClueGame(theBoard, cards, control);  // create the frame 
	}
}
