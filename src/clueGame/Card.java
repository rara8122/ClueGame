/*
 *Class: This class is used for information about a card such as the cardname and cardtype. There are three kinds of card types; person, weapon, and room. 
 *Authors: Melanie Perez, Rachel Davy 
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

import java.awt.Color;

public class Card {
	
	private String cardName;
	private CardType cardType;
	
	/*Constructor to initialize a Card object with a name and a card type 
	*/
	public Card(String name, CardType newType) {
		cardName = name;
		cardType = newType;
	}
	// Copy constructor to create a Card from another Card object
	public Card(Card card) {
		cardName = card.getCardName();
		cardType = card.getCardType();
	}
	// Method to check if two cards are equal based on their name and card type
	public boolean equals(Card c) {
		return (cardName.equals(c.getCardName()) && cardType == c.getCardType());	
	}
	
	// Method to get a string representation of the card (its name)
	public String toString() {
		return cardName;
	}

	//getters
	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	//setters
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public boolean contains(Card roomCard) {
		return false;
	}

}
