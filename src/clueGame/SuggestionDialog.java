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


public class SuggestionDialog extends JDialog  {
	private Card suggestedPerson;
	private Card suggestedWeapon;
	private JComboBox<Card> weapons;
	private JComboBox<Card> people;
	private Board board;

	public SuggestionDialog(String currentRoom) {
		setTitle("Make a Suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
		
		board = Board.getInstance();
		Set<Card> deck = board.getDeck();
		
		JLabel roomLabel = new JLabel("Room");
		JLabel room = new JLabel(currentRoom);
		room.setBorder(new EtchedBorder());
		add(roomLabel);
		add(room);
		weapons = new JComboBox<Card>();
		people = new JComboBox<Card>();
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
			default:
				break;
			}
		}
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
		submit.addActionListener(e -> board.playerSuggestion());
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(e -> board.suggestionCancel());
		
		add(submit);
		add(cancel);

	}
	
	public Card getWeapon() {
		return suggestedWeapon;
	}
	public Card getPerson() {
		return suggestedPerson;
	}
	
	public void setWeapon(Card newWeapon) {
		suggestedWeapon = newWeapon;
	}
	
	public void setPerson(Card newPerson) {
		suggestedPerson = newPerson;
	}
	
	private class ComboListener implements ActionListener {
	  public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == weapons)
	    	suggestedWeapon = (Card) weapons.getSelectedItem();
	    else
	    	suggestedPerson = (Card) people.getSelectedItem();
	  }
	}
}
