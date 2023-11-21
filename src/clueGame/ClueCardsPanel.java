/*Class: This class creates the cards panel which shows human player’s cards in their hand and shows human player’s seen cards. It also has a main method to display the panel and updates the data in the fields
 * Authors: Rachel Davy, Melanie Perez
 * Date: 11/08/2023
 * Sources: none other than lecture videos
 * Collaborators: none
 */
package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueCardsPanel extends JPanel{
	
	private JPanel peoplePanel;
	private JPanel roomsPanel;
	private JPanel weaponsPanel;
	private JPanel inHandPeople;
	private JPanel seenPeople;
	private JPanel inHandRooms;
	private JPanel seenRooms;
	private JPanel inHandWeapons;
	private JPanel seenWeapons;
	
	/*
	 * Constructor for class
	 */
	public ClueCardsPanel() {
		
		setBorder(new TitledBorder( new EtchedBorder(), "Known Cards"));
		setLayout(new GridLayout(3, 1));	
		
		JTextArea newCardArea;
		inHandPeople = new JPanel();
		inHandPeople.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		inHandPeople.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		inHandPeople.add(newCardArea);
		
		seenPeople = new JPanel();
		seenPeople.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		seenPeople.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		seenPeople.add(newCardArea);
		
		inHandRooms = new JPanel();
		inHandRooms.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		inHandRooms.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		inHandRooms.add(newCardArea);
		
		seenRooms = new JPanel();
		seenRooms.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		seenRooms.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		seenRooms.add(newCardArea);
		
		inHandWeapons = new JPanel();
		inHandWeapons.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		inHandWeapons.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		inHandWeapons.add(newCardArea);
		
		seenWeapons = new JPanel();
		seenWeapons.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		seenWeapons.setLayout(new GridLayout(0, 1));
		newCardArea = new JTextArea();
		newCardArea.setText("None");
		newCardArea.setEditable( false );
		seenWeapons.add(newCardArea);
		
		peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder( new EtchedBorder(), "People"));
		peoplePanel.setLayout(new GridLayout(2, 1));
		peoplePanel.add(inHandPeople);
		peoplePanel.add(seenPeople);
		
	
		roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder( new EtchedBorder(), "Rooms"));
		roomsPanel.setLayout(new GridLayout(2, 1));
		roomsPanel.add(inHandRooms);
		roomsPanel.add(seenRooms);
		
		weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder( new EtchedBorder(), "Weapons"));
		weaponsPanel.setLayout(new GridLayout(2, 1));
		weaponsPanel.add(inHandWeapons);
		weaponsPanel.add(seenWeapons);
		
		add(peoplePanel);
		add(roomsPanel);
		add(weaponsPanel);
		
	}
	/*
	 * method to update deck cards that calls in helper methods 
	 */
	public void updateDeckCards(Set<Card> newCards, Color color) {
	    Set<Card> handPeople = new HashSet<>();
	    Set<Card> handRooms = new HashSet<>();
	    Set<Card> handWeapons = new HashSet<>();
	    // Separate cards into different sets based on type
	    for (Card card : newCards) {
	        switch (card.getCardType()) {
	            case PERSON:
	                handPeople.add(card);
	                break;
	            case ROOM:
	                handRooms.add(card);
	                break;
	            case WEAPON:
	                handWeapons.add(card);
	                break;
	            default:
	                // Handle unexpected card types, if any
	                break;
	        }
	    }
	    // Helper method to add cards to their respective panels
	    addCardsToPanel(handPeople, inHandPeople, color);
	    addCardsToPanel(handRooms, inHandRooms, color);
	    addCardsToPanel(handWeapons, inHandWeapons, color);
	    // Add panels to their corresponding container
	    peoplePanel.add(inHandPeople);
	    roomsPanel.add(inHandRooms);
	    weaponsPanel.add(inHandWeapons);
	}
	/*
	 *  Method to add cards to a panel
	 */
	private void addCardsToPanel(Set<Card> cards, JPanel panel, Color color) {
	    if (cards.isEmpty()) {
	        panel.removeAll();// Clear the panel if there are no cards
	        return;
	    }
	    panel.removeAll();// Clear the panel before adding cards
	    for (Card card : cards) {
	        JTextArea newCardArea = new JTextArea();
	        setColorAndText(newCardArea, color, card.getCardName());
	        panel.add(newCardArea);//add card to panel
	    }
	}
	/*
	 *  Method to set color and text for a JTextArea
	 */
	private void setColorAndText(JTextArea textArea, Color color, String text) {
	    textArea.setBackground(color);
	    textArea.setText(text);
	    textArea.setEditable(false);

	    if (color.getBlue() + color.getGreen() + color.getRed() <= 255) {
	        textArea.setForeground(Color.WHITE);
	    } else {
	        textArea.setSelectedTextColor(Color.BLACK);
	    }
	}
	
	/*
	 * Method to update seen cards 
	 */
	public void updateSeenCards(Set<SeenCard> newCards) {
	    displayCards(newCards, CardType.PERSON, seenPeople);
	    displayCards(newCards, CardType.ROOM, seenRooms);
	    displayCards(newCards, CardType.WEAPON, seenWeapons);

	    peoplePanel.add(seenPeople);
	    roomsPanel.add(seenRooms);
	    weaponsPanel.add(seenWeapons);
	}
	/*
	 * Method to display cards in a panel based on card type
	 */
	private void displayCards(Set<SeenCard> newCards, CardType type, JPanel panel) {
	    Set<SeenCard> cards = new HashSet<>();
	    for (SeenCard card : newCards) {
	        if (card.getCardType() == type) {
	            cards.add(card);
	        }
	    }
	    if (!cards.isEmpty()) {
	        panel.removeAll();
	        for (SeenCard card : cards) {
	            JTextArea newCardArea = createCardTextArea(card.getColor(), card.getCardName());
	            panel.add(newCardArea);
	        }
	    }
	}
	/*
	 * Method to create a JTextArea for a card with specified color and name
	 */
	private JTextArea createCardTextArea(Color color, String cardName) {
	    JTextArea newCardArea = new JTextArea();
	    if (color.getBlue() + color.getGreen() + color.getRed() <= 255) {
	        newCardArea.setForeground(Color.WHITE);
	    } else {
	        newCardArea.setSelectedTextColor(Color.BLACK);
	    }
	    newCardArea.setBackground(color);
	    newCardArea.setText(cardName);
	    newCardArea.setEditable(false);
	    return newCardArea;
	}
		
	/*
	 * Main to test the panel
	 * @param args
	 */
	public static void main(String[] args) {
		ClueCardsPanel panel = new ClueCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300,400 );  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible	
		  
	    Set<Card> inHandCards = new HashSet<>();
	    inHandCards.add(new Card("Link", CardType.PERSON));
	    inHandCards.add(new Card("France", CardType.ROOM));
	    inHandCards.add(new Card("The One Ring", CardType.WEAPON));

	    Set<SeenCard> seenCards = new HashSet<>();
	    seenCards.add(new SeenCard(new Card("Frodo Baggins", CardType.PERSON), Color.RED));
	    seenCards.add(new SeenCard(new Card("Agraban", CardType.ROOM), Color.GREEN));
	    seenCards.add(new SeenCard(new Card("The Triforce", CardType.WEAPON), Color.BLUE));

	  
	    panel.updateDeckCards(inHandCards, Color.BLACK);
	    panel.updateSeenCards(seenCards);
	    frame.setVisible(true); // make it visible
        
	}
		
}