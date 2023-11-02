package clueGame;

public class Card {
	
	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType newType) {
		cardName = name;
		cardType = newType;
	}

	public boolean equals() {
		return false;	
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
