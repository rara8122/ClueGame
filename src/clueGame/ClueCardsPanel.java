package clueGame;

import java.awt.GridLayout;
import java.awt.TextArea;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueCardsPanel extends JPanel{
	
	private JTextArea inHandCards;
	private JTextArea seenCards;
	private JLabel inHand;
	private JLabel seen;
	private Set<String> handPeopleCards;
    private Set<String> seenPeopleCards;
    private Set<String> handRoomsCards;
    private Set<String> seenRoomsCards;
    private Set<String> handWeaponsCards;
    private Set<String> seenWeaponsCards;
	
	
	public ClueCardsPanel() {
	
	inHandCards = new JTextArea("None");
	seenCards = new JTextArea("None");
	inHand = new JLabel("In Hand: ");
	seen = new JLabel("Seen: ");
	handPeopleCards = new HashSet<>();
    seenPeopleCards = new HashSet<>();
    handRoomsCards = new HashSet<>();
    seenRoomsCards = new HashSet<>();
    handWeaponsCards = new HashSet<>();
    seenWeaponsCards = new HashSet<>();
		
	JPanel knownCards = new JPanel();
	knownCards.setBorder(new TitledBorder( new EtchedBorder(), "Known Cards"));
	knownCards.setLayout(new GridLayout(3, 1));
	add(knownCards);
	
	
	JPanel peoplePanel = new JPanel();
	peoplePanel.setBorder(new TitledBorder( new EtchedBorder(), "People"));
	peoplePanel.add(inHand);
	peoplePanel.add(seen);
	

	JPanel roomsPanel = new JPanel();
	roomsPanel.setBorder(new TitledBorder( new EtchedBorder(), "Rooms"));
	roomsPanel.add(inHand);
	peoplePanel.add(seen);
	
	JPanel Weapons = new JPanel();
	Weapons.setBorder(new TitledBorder( new EtchedBorder(), "Weapons"));
	Weapons.add(inHand);
	peoplePanel.add(seen);
	
	
	
}
	
	public static void main(String[] args) {
		ClueCardsPanel panel = new ClueCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300,400 );  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	
	
}
