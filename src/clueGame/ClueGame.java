/*
 * Class: This class creates the entire Jframe clue game gui with the board panel, clue cards panel, and game control panels. The clue cards panel and game control panel are in their own seperate classes. We extended JPanel on the existing board class to create the board panel. All the  panels were incorporated into this class to create he full clue game frame.  
 * Author: Rachel Davy, Melanie Perez
 * Date: 11/13/2023
 * Collaborators: none
 */
package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
		
		super.add(theBoard, BorderLayout.CENTER);
		super.add(cards, BorderLayout.EAST);
		super.add(newcontrol, BorderLayout.SOUTH);
		super.setSize(1500,1000 );  // size the frame
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		super.setVisible(true); // make it visible
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
