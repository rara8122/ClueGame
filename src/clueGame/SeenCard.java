package clueGame;

import java.awt.Color;

public class SeenCard {
	private Card card;
	private Color color;
	
	public SeenCard(Card newCard, Color newColor) {
		card = newCard;
		color = newColor;
	}

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
