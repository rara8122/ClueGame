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
	
	private Board theBoard;
	private ClueCardsPanel cards;
	private GameControlPanel control;
	
	/*
	 * Constructor for class 
	 */
	public ClueGame(Board newBoard, ClueCardsPanel newcards, GameControlPanel newcontrol) {
		super();
		
		theBoard =  newBoard;
		cards = newcards;
		control = newcontrol;
		initializeMainFrame(); 		
		HumanPlayer user = theBoard.getUser();		
		cards.updateDeckCards(user.getDeck(), user.getColor());
		cards.updateSeenCards(user.getSeen());
		control.setTurn(user);
		control.setGuess( "I have no guess!");
		control.setGuessResult( "So you have nothing?");
		control.setRoll(theBoard.getRoll());
		
		showSplashScreen(user); 
			
	}
	
	/*
	 * method to initialize the main frame with all the panels and splash screen
	 */
	private void initializeMainFrame() {
		super.add(theBoard, BorderLayout.CENTER);
		super.add(cards, BorderLayout.EAST);
		super.add(control, BorderLayout.SOUTH);
		super.setSize(1500,1000 );  // size the frame
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
	
	/*
	 * Main to test the panel
	 */
	public static void main(String[] args) {
		Board theBoard = Board.getInstance();
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		theBoard.initialize();
		ClueCardsPanel cards = new ClueCardsPanel();  // create the cards panel
		GameControlPanel control = new GameControlPanel();  // create the control panel
		ClueGame frame = new ClueGame(theBoard, cards, control);  // create the frame 
		try {
			theBoard.nextPlayer();
		} catch (MisClick e) {
			e.printStackTrace();
		}
		theBoard.play();
		control.setRoll(theBoard.getRoll());
		String lastGuess = " ";
		String lastResult = " ";
		HumanPlayer user = theBoard.getUser();
		Set<SeenCard> lastSeen = user.getSeen();
		int wins = 0;
		while(true) {
			while(!theBoard.isDone()) {
				if(!lastGuess.equals(theBoard.getGuess())) {
					lastGuess = theBoard.getGuess();
					control.setGuess(lastGuess);
				}
				if(!lastResult.equals(theBoard.getResult())) {
					lastResult = theBoard.getResult();
					control.setGuessResult(lastResult);
				}
				if(lastSeen.equals(user.getSeen())){
					lastSeen = user.getSeen();
					cards.updateSeenCards(lastSeen);
				}
			}
			if(theBoard.getWin() == 2) {
				JOptionPane.showMessageDialog(frame, "You Win!", "You win", JOptionPane.INFORMATION_MESSAGE);
				wins ++;
			} else if (theBoard.getWin() == 1){
				JOptionPane.showMessageDialog(frame, "Sorry, not correct! You lose!", "You Lose", JOptionPane.INFORMATION_MESSAGE);
			} else if (theBoard.getWin() == 3){
				JOptionPane.showMessageDialog(frame, "The computer just won, answer is " + theBoard.getPlayerSoln() + ", " 
											+ theBoard.getRoomSoln() + ", " + theBoard.getWeaponSoln(),
											"Computer Won", JOptionPane.INFORMATION_MESSAGE);
			}
			theBoard.reset();
			try {
				theBoard.nextPlayer();
			} catch (MisClick e) {
				e.printStackTrace();
			}
			theBoard.play();
			control.setRoll(theBoard.getRoll());
			lastGuess = theBoard.getGuess();
			control.setGuess(lastGuess);
			lastResult = theBoard.getResult();
			control.setGuessResult(lastResult);
			lastSeen = user.getSeen();
			cards.updateSeenCards(lastSeen);
			cards.updateDeckCards(user.getDeck(), user.getColor());
		}
		//frame.dispose();
	}
}
