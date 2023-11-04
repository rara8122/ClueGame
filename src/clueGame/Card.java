/*
 *Class: This class is used for information about a card such as the cardname and cardtype. There are three kinds of card types; person, weapon, and room. 
 *Authors: Melanie Perez, Rachel Davy 
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

public class Card {
	
	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType newType) {
		cardName = name;
		cardType = newType;
	}
	
	public Card(Card card) {
		cardName = card.getCardName();
		cardType = card.getCardType();
	}

	public boolean equals(Card c) {
		return (cardName == c.getCardName() && cardType == c.getCardType());	
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

}
