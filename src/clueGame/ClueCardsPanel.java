package clueGame;

import java.awt.Color;
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
	
	private JPanel peoplePanel;
	private JPanel roomsPanel;
	private JPanel weaponsPanel;
	private JPanel inHandPeople;
	private JPanel seenPeople;
	private JPanel inHandRooms;
	private JPanel seenRooms;
	private JPanel inHandWeapons;
	private JPanel seenWeapons;
	
	public ClueCardsPanel() {
		
		setBorder(new TitledBorder( new EtchedBorder(), "Known Cards"));
		setLayout(new GridLayout(3, 1));	
		
		inHandPeople = new JPanel();
		inHandPeople.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		seenPeople = new JPanel();
		seenPeople.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		
		inHandRooms = new JPanel();
		inHandRooms.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		seenRooms = new JPanel();
		seenRooms.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		
		inHandWeapons = new JPanel();
		inHandWeapons.setBorder(new TitledBorder( new EtchedBorder(), "In Hand:"));
		seenWeapons = new JPanel();
		seenWeapons.setBorder(new TitledBorder( new EtchedBorder(), "Seen:"));
		
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
	
	public void updateDeckCards(Set<Card> newCards) {
		inHandPeople.removeAll();
		inHandRooms.removeAll();
		inHandWeapons.removeAll();
		JTextArea newCardArea;
		for (Card card : newCards) {
			if(card.getCardType() == CardType.PERSON) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				inHandPeople.add(newCardArea);
			}
			if(card.getCardType() == CardType.ROOM) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				inHandRooms.add(newCardArea);
			}
			if(card.getCardType() == CardType.WEAPON) {
				newCardArea = new JTextArea();
				newCardArea.setText(card.getCardName());
				inHandWeapons.add(newCardArea);
			}
		}
		peoplePanel.add(inHandPeople);
		roomsPanel.add(inHandRooms);
		weaponsPanel.add(inHandWeapons);
	}
	public void updateSeenCards(Set<SeenCard> newCards) {
		seenPeople.removeAll();
		seenRooms.removeAll();
		seenWeapons.removeAll();
		JTextArea newCardArea;
		for (SeenCard card : newCards) {
			if(card.getCardType() == CardType.PERSON) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				seenPeople.add(newCardArea);
			}
			if(card.getCardType() == CardType.ROOM) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				seenRooms.add(newCardArea);
			}
			if(card.getCardType() == CardType.WEAPON) {
				newCardArea = new JTextArea();
				if(card.getColor().getBlue() + card.getColor().getGreen() + card.getColor().getRed() <= 255) {
					newCardArea.setForeground(Color.WHITE);
				} else {
					newCardArea.setSelectedTextColor(Color.BLACK);
				}
				newCardArea.setBackground(card.getColor());
				newCardArea.setText(card.getCardName());
				seenWeapons.add(newCardArea);
			}
		}
		peoplePanel.add(seenPeople);
		peoplePanel.add(seenRooms);
		peoplePanel.add(seenWeapons);
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
        
	}
	
	
}
