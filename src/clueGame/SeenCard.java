/*
 * Class: The SeenCard class appears to represent a card that has been seen before, storing information about the card itself (Card object) and its associated Color. 
 * Authors: Rachel Davy, Melanie Perez
 * Collaborators: none
 * Sources: none
 */
package clueGame;

import java.awt.Color;

public class SeenCard {
	private Card card;
	private Color color;
	/*
	 * constructor to initialize a seencard 
	 */
	public SeenCard(Card newCard, Color newColor) {
		card = newCard;
		color = newColor;
	}
	/*
	 * all getters here
	 */
	public Card getCard() {
		return card;
	}

	public Color getColor() {
		return color;
	}
	
	public String getCardName() {
		return card.getCardName();
	}
	
	public CardType getCardType() {
		return card.getCardType();
	}
	
}
