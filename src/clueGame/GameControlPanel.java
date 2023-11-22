/*Class: This class extends JPanel to create a panel with some game controls, It has butttons for the player to make an accusation and move to the next player. It also does other things like display of the roll of the die, display whose turn it is, display guesses made by players and the result.
 * Authors: Rachel Davy, Melanie Perez
 * Date: 11/08/2023
 * Sources: none other than lecture videos
 * Collaborators: none
 */
package clueGame;

import java.awt.Color;
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
	private JTextField guess;
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
        guess = new JTextField();
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

        nextPlayerButton.addActionListener(e -> {
            try {
                gameBoard.nextPlayer();
                gameBoard.play();
                updatePanel(); // Update the panel after the player's turn
            } catch (MisClick misClick) {
            	 JOptionPane.showMessageDialog(this, "Please finish your turn ", "Message", JOptionPane.ERROR_MESSAGE);
            }
        });
        
	}
	
	/*method to update the panel 
	 * 
	 */
	private void updatePanel() {
	    // Fetch current player's information and update the panel
	    Player currentPlayer = gameBoard.getCurrentPlayer();
	    setTurn(currentPlayer);
	    setRoll(gameBoard.getRoll());
	    setGuess("Some guess");
	    setGuessResult("Some guess result");
	    repaint();
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
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		panel.setRoll(5);
	}
	
	//setters
	public void setRoll(int rollNum) {
		roll.setText(Integer.toString(rollNum));	
	}
	
	public void setGuessResult(String string) {
		guessResult.setText(string);
	}

	public void setGuess(String string) {
		guess.setText(string);	
	}
	
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
