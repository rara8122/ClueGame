package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		JButton nextPlayerButton = new JButton("Next Player");
        JButton makeAccusationButton = new JButton("Make Accusation");
        JLabel dieRollLabel = new JLabel("Roll: ");
        JLabel whoseTurn = new JLabel("Whose Turn?");
        //text field for whose turn
        JTextField player = new JTextField();
        //text field for roll number
        JTextField roll = new JTextField();
        JTextField guess = new JTextField();
        JTextField guessResult = new JTextField();
        
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
        guessPanel.setBorder(new TitledBorder( new EtchedBorder(), "Guess Result"));
        
        
        //create right panel
        JPanel rightPanel = new JPanel();
        rightPanel.add(whoseTurnP);
        rightPanel.add(dieRollP);
        rightPanel.add(nextPlayerButton);
        rightPanel.add(makeAccusationButton);
        //create left panel
        JPanel leftPanel = new JPanel();
        leftPanel.add(guessPanel);
        leftPanel.add(guessResultPanel);
        setLayout(new BorderLayout());   
        
       
        JPanel wholePanel = new JPanel();
        wholePanel.add(leftPanel);
        wholePanel.add(rightPanel);
        add(wholePanel);
        
        
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
		panel.setGuess( "So you have nothing?");
	}



	private void setGuess(String string) {
		// TODO Auto-generated method stub		
	}
	private void setTurn(ComputerPlayer computerPlayer) {
		// TODO Auto-generated method stub		
	}
}
