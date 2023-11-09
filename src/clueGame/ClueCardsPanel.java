/*Class: This class creates the cards panel which shows human player’s cards in their hand and shows human player’s seen cards. It also has a main method to display the panel and updates the data in the fields
 * Authors: Rachel Davy, Melanie Perez
 * Date: 11/08/2023
 * Sources: none other than lecture videos
 * Collaborators: none
 */
package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
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
	
	//method to update the cards 
	public void updateDeckCards(Set<Card> newCards) {
		Set<Card> handPeople = new HashSet<>();
		Set<Card> handRooms = new HashSet<>();
		Set<Card> handWeapons = new HashSet<>();
		JTextArea newCardArea;
		//iterates through each card object in newCards
		for (Card card : newCards) {
			//checks type and adds card to approriate deck
			if(card.getCardType() == CardType.PERSON) {
				handPeople.add(card);
			}
			if(card.getCardType() == CardType.ROOM) {
				handRooms.add(card);
			}
			if(card.getCardType() == CardType.WEAPON) {
				handWeapons.add(card);
			}
		}
		if(handPeople.size() >= 1) {
			inHandPeople.removeAll();
			for (Card card : handPeople) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				inHandPeople.add(newCardArea);
			}
		}
		if(handRooms.size() >= 1) {
			inHandRooms.removeAll();
			for (Card card : handRooms) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				inHandRooms.add(newCardArea);
			}
		}
		if(handWeapons.size() >= 1) {
			inHandWeapons.removeAll();
			//inHandWeapons.setLayout(new GridLayout(handWeapons.size(), 1));
			for (Card card : handWeapons) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				inHandWeapons.add(newCardArea);
			}
		}
		peoplePanel.add(seenPeople);
		roomsPanel.add(seenRooms);
		weaponsPanel.add(seenWeapons);
	}
	
	//method to update seen cards
	public void updateSeenCards(Set<SeenCard> newCards) {
		JTextArea newCardArea;
		Set<SeenCard> people = new HashSet<>();
		Set<SeenCard> rooms = new HashSet<>();
		Set<SeenCard> weapons = new HashSet<>();
		for (SeenCard card : newCards) {
			if(card.getCardType() == CardType.PERSON) {
				people.add(card);
			}
			if(card.getCardType() == CardType.ROOM) {
				rooms.add(card);
			}
			if(card.getCardType() == CardType.WEAPON) {
				weapons.add(card);
			}
		}
		if(people.size() > 0) {
			seenPeople.removeAll();
			for (SeenCard card : people) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				seenPeople.add(newCardArea);
			}
		}
		if(rooms.size() > 0) {
			seenRooms.removeAll();
			for (SeenCard card : rooms) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				seenRooms.add(newCardArea);
			}
		}
		if(weapons.size() > 0) {
			seenWeapons.removeAll();
			for (SeenCard card : weapons) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				newCardArea.setEditable( false );
				seenWeapons.add(newCardArea);
			}
		}
		peoplePanel.add(seenPeople);
		roomsPanel.add(seenRooms);
		weaponsPanel.add(seenWeapons);
	}
	
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

	  
	    panel.updateDeckCards(inHandCards);
	    panel.updateSeenCards(seenCards);
	    frame.setVisible(true); // make it visible
        
	}
		
}