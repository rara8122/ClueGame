package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

public class AccusationDialogue extends JDialog{
	private Card suggestedPerson;
	private Card suggestedWeapon;
	private JComboBox<Card> weapons;
	private JComboBox<Card> people;
	private Card suggestedRoom;
	private JComboBox<Card> rooms;
	private Board board;
	
	public AccusationDialogue() {
		setTitle("Make a Suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
		
		board = Board.getInstance();
		Set<Card> deck = board.getDeck();
		
		weapons = new JComboBox<Card>();
		people = new JComboBox<Card>();
		rooms = new JComboBox<Card>();
		for (Card card : deck) {
			switch (card.getCardType())
			{
			case WEAPON:
				if(suggestedWeapon == null) {
					suggestedWeapon = card;
				}
				weapons.addItem(card);
				break;
			case PERSON:
				if(suggestedPerson == null) {
					suggestedPerson = card;
				}
				people.addItem(card);
				break;
			case ROOM:
				if(suggestedRoom == null) {
					suggestedRoom = card;
				}
				rooms.addItem(card);
				break;
			default:
				break;
			}
		}
		JLabel roomLabel = new JLabel("Room");
		add(roomLabel);
		add(rooms);
		
		JLabel personLabel = new JLabel("Person");
		add(personLabel);
		add(people);
		
		JLabel weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		add(weapons);
		
		ComboListener listener = new ComboListener();
		weapons.addActionListener(listener);
		people.addActionListener(listener);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(e -> board.playerAccusation());
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(e -> board.accusationCancel());
		
		add(submit);
		add(cancel);

	}
	public Card getRoom(){
		return suggestedRoom;
	}
	
	public Card getWeapon(){
		return suggestedWeapon;
	}
	
	public Card getPerson(){
		return suggestedPerson;
	}
	
	private class ComboListener implements ActionListener {
	  public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == weapons)
	    	suggestedWeapon = (Card) weapons.getSelectedItem();
	    else if (e.getSource() == people)
	    	suggestedPerson = (Card) people.getSelectedItem();
	    else
	    	suggestedRoom = (Card) rooms.getSelectedItem();
	  }
	}
}
