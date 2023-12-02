/*Class: This class extends JPanel to create a panel with some game controls, It has butttons for the player to make an accusation and move to the next player. It also does other things like display of the roll of the die, display whose turn it is, display guesses made by players and the result.
 * Authors: Rachel Davy, Melanie Perez
 * Date: 11/08/2023
 * Sources: none other than lecture videos
 * Collaborators: none
 */
package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	
	private JTextArea player;
	private JTextArea roll;
	private JTextArea guess;
	private JTextArea guessResult;
	private static Board gameBoard = Board.getInstance();
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */	
	public GameControlPanel()  {
		this.gameBoard = gameBoard; 
		JButton nextPlayerButton = new JButton("Next Player");
        JButton makeAccusationButton = new JButton("Make Accusation");
        JLabel dieRollLabel = new JLabel("Roll: ");
        JLabel whoseTurn = new JLabel("Whose Turn?");
        //text field for whose turn
        player = new JTextArea();
        player.setEditable( false );
        //text field for roll number
        roll = new JTextArea();
        roll.setEditable( false );
        guess = new JTextArea();
        guess.setEditable( false );
        guessResult = new JTextArea();
        guessResult.setEditable( false );
        
        
        //panel with the player turn label and player
        JPanel whoseTurnP = new JPanel(); 
        whoseTurnP.add(whoseTurn);
        whoseTurnP.add(player);
        //panel for roll 
        JPanel dieRollP = new JPanel();
        dieRollP.add(dieRollLabel);
        dieRollP.add(roll);
        //panel guess
        JPanel guessPanel  = new JPanel();
        guessPanel.add(guess);
        guessPanel.setBorder(new TitledBorder( new EtchedBorder(), "Guess"));
        //panel guess results
        JPanel guessResultPanel = new JPanel();
        guessResultPanel.add(guessResult);
        guessResultPanel.setBorder(new TitledBorder( new EtchedBorder(), "Guess Result"));
        
        
        //create right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 2));
        rightPanel.add(whoseTurnP);
        rightPanel.add(dieRollP);
        rightPanel.add(makeAccusationButton);
        rightPanel.add(nextPlayerButton);
        //create left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        leftPanel.add(guessPanel);
        leftPanel.add(guessResultPanel);
        //setLayout(new BorderLayout());   
        
       
        JPanel wholePanel = new JPanel();
        wholePanel.add(leftPanel);
        wholePanel.add(rightPanel);
        add(wholePanel); 
        
        makeAccusationButton.addActionListener(e -> {
		    if (gameBoard.canAccuse()) {
		    	AccusationDialogue accuse = new AccusationDialogue();
	        	accuse.setVisible(true);
	        	gameBoard.setAccuse(accuse);
		    } else {
		        JOptionPane.showMessageDialog(this, "It's not the human player's turn!", "Invalid Action", JOptionPane.WARNING_MESSAGE);
		    }
		}); 

        nextPlayerButton.addActionListener(e -> {
            try {
                gameBoard.nextPlayer();
                gameBoard.play();
                updatePanel();
            } catch (MisClick misClick) {
            	 JOptionPane.showMessageDialog(this, "Please finish your turn ", "Message", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
	}
	
	/*method to update the panel  to the default
	 * 
	 */
	private void updatePanel() {
	    // Fetch current player's information and update the panel
	    Player currentPlayer = gameBoard.getCurrentPlayer();
	    setTurn(currentPlayer);
	    setRoll(gameBoard.getRoll());
	    repaint();
	}
	
	// Adjust the size of guess and guessResult JTextAreas
	public void setGuessSize() {
		int width = (int) (this.getPreferredSize().getWidth()/5);
		int height = (int) (this.getPreferredSize().getHeight()/6);
		guess.setPreferredSize(new Dimension (width, height));
        guessResult.setPreferredSize(new Dimension (width, height));
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible	
		// test filling in the data
		panel.setTurn(new ComputerPlayer("Bruce Wayne (Batman)", Color.BLACK, 18, 0));
		panel.setGuess( "I have no guess!", Color.WHITE);
		panel.setGuessResult( "So you have nothing?", Color.WHITE);
		panel.setRoll(5);
	}
	
	/*
	 * Sets the roll number on the panel.
	 * 
	 * rollNum The roll number to be displayed
	 */
	public void setRoll(int rollNum) {
		roll.setText(Integer.toString(rollNum));// Set the roll text
	}
	
	/**
	 * Sets the guess result text and colors on the panel.
	 * 
	 * string The guess result text
	 * color The color to be applied to the guess result panel
	 */
	public void setGuessResult(String string, Color color) {
		if(color == null) {
			guessResult.setOpaque(false);
			guessResult.setForeground(Color.BLACK);
		} else {
			guessResult.setOpaque(true);
			if((color.getBlue() + color.getGreen() + color.getRed()) <= 255) {
				guessResult.setForeground(Color.WHITE);
			} else {
				guessResult.setForeground(Color.BLACK);
			}
			guessResult.setBackground(color);
		}
		guessResult.setText(string);
		repaint();
	}
	/**
	 * Sets the guess text and colors on the panel.
	 * 
	 * string The guess text
	 * color The color to be applied to the guess panel
	 */
	public void setGuess(String string, Color color) {
		 // Set guess to transparent and text color to black if no color is provided
		if(color == null) {
			guess.setOpaque(false);
			guess.setForeground(Color.BLACK);
		} else {
			guess.setOpaque(true);
			if(color.getBlue() + color.getGreen() + color.getRed() <= 255) {
				guess.setForeground(Color.WHITE);
			} else {
				guess.setForeground(Color.BLACK);
			}
			guess.setBackground(color);
		}
		guess.setText(string);
		repaint();
	}
	/**
	 * Sets the current player's turn information on the panel.
	 * 
	 * currentTurn The current player object
	 */
	public void setTurn(Player currentTurn) {	
		if(currentTurn.getColor().getBlue() + currentTurn.getColor().getGreen() + currentTurn.getColor().getRed() <= 255) {
			player.setForeground(Color.WHITE);
		} else {
			player.setSelectedTextColor(Color.BLACK);
		}
		player.setBackground(currentTurn.getColor());
		player.setText(currentTurn.getName());
	}

}
