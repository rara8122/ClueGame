/*
 * Class: This class presents a suggestion dialogue that has drop menus for person and weapon suggestions when a player is to make a suggestion when they enter a room. 
 * Authors: Rachel Davy, Melanie Perez
 * Date: 11/29/2023
 * Collaborators: none 
 * Sources: none
 */
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
	
	/*
	 * Constructor that handles the creation of the dialog box
	 */
	public SuggestionDialog(String currentRoom) {
		setTitle("Make a Suggestion"); 
		setSize(500, 200);
		setLayout(new GridLayout(0, 2));
		
		board = Board.getInstance();
		Set<Card> deck = board.getDeck();
		
		JLabel roomLabel = new JLabel("Room");
		JLabel room = new JLabel(currentRoom);
		room.setBorder(new EtchedBorder());
		add(roomLabel);
		add(room);
		weapons = new JComboBox<Card>(); // Initialize combo boxes for weapons and people cards
		people = new JComboBox<Card>();
		for (Card card : deck) {
			// Populate the dropdowns based on card type
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
		
		// Add labels and dropdowns for person and weapon
		JLabel personLabel = new JLabel("Person");
		add(personLabel);
		add(people);
		
		JLabel weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		add(weapons);
		
		// ActionListener for the dropdowns to update selected cards
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
	// Getter methods for suggested weapon and person
	public Card getWeapon() {
		return suggestedWeapon;
	}
	public Card getPerson() {
		return suggestedPerson;
	}
	
	  // Setter methods for suggested weapon and person
	public void setWeapon(Card newWeapon) {
		suggestedWeapon = newWeapon;
	}
	
	public void setPerson(Card newPerson) {
		suggestedPerson = newPerson;
	}
    // ActionListener for the dropdowns
	private class ComboListener implements ActionListener {
	  public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == weapons)
	    	suggestedWeapon = (Card) weapons.getSelectedItem();
	    else
	    	suggestedPerson = (Card) people.getSelectedItem();
	  }
	}
}
