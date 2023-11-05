/*
 *Class: This is the player class which holds information about the players such as their name, color, location. etc. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {

	private String name; 
	private Color color;
	private int row, column; 
	private Set<Card> deck;
	private Set<Card> seen;
	private int deckSize;


	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		deck = new HashSet<Card>();
		seen = new HashSet<Card>();
		deckSize = 0;
	}
	//method to disprove a suggestion
	public Card disproveSuggestion(Card room, Card weapon, Card player) {
		Card disproveRoom = null;
		Card disproveWeapon = null;
		Card disprovePlayer = null;
		for (Card c : deck) {
			if (c.equals(room)) {
				disproveRoom = c;
			}
			if (c.equals(weapon)) {
				disproveWeapon = c;
			}
			if (c.equals(player)) {
				disprovePlayer = c;
			}
		}
		int cardsDisprove = 0;//how many cards we found
		if(disproveRoom != null) {
			if(disproveWeapon != null) {
				if(disprovePlayer != null) { 
					cardsDisprove = 3; //we have all 3 cards
				} else { 
					cardsDisprove = 2; //we do have the room and weapon cards
				}
			} else { 
				if(disprovePlayer != null) { //we do have the player card
					disproveWeapon = disprovePlayer; //we always make sure the cards are set room, then weapon, then player 
														//(even if the types don't match)
					cardsDisprove = 2;//we do have the room and player card
				} else { 
					cardsDisprove = 1;//we do have the room card
				}
			}
		} else {
			if(disproveWeapon != null) {
				if(disprovePlayer != null) {
					disproveRoom = disprovePlayer; 
					cardsDisprove = 2;//we do have the player and weapon cards
				} else {
					disproveRoom = disproveWeapon; 
					cardsDisprove = 1;// we do have the weapon cards
				}
			} else {
				if(disprovePlayer != null) {
					disproveRoom = disprovePlayer; 
					cardsDisprove = 1; //we do have the player card
				} else {
					return null; //we found no cards that disprove the suggestion
				}
			}
		}
		Random choice = new Random();
		int choose = choice.nextInt(cardsDisprove);
		if(choose == 0) {
			return disproveRoom;
		} else if(choose == 1) {
			return disproveWeapon;
		} else {
			return disprovePlayer;
		}
	}
	public Boolean hasSeen(Card card) {
		Boolean solution = false;
		for (Card c : deck) {
			if (c.equals(card)) {
				solution = true;
			}
		}
		for (Card c : seen) {
			if (c.equals(card)) {
				solution = true;
			}
		}
		return solution;
	}
	//adds a card to deck
	public void addCard(Card newCard) {
		deck.add(newCard);
		deckSize++;
	}
	//adds a card to the seen cards
	public void updateSeen(Card newCard) {
		seen.add(newCard);
	}
	//getters and setters
	public boolean deckFull() {
		return deckSize == 3;
	}

	public String getName() {
		return name;
	}

	public Set<Card> getDeck(){
		return deck;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	//setters  
	public void setColor(Color color) {
		this.color = color;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	public Set<Card> getCards() {
		return deck;	
		// TODO Auto-generated method stub		
	}
}