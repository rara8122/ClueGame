/*
 *Class: This is the player class which holds information about the players such as their name, color, location. etc. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {

	private String name; 
	private Color color;
	private int row, column, startRow, startColumn; 
	private Set<Card> deck;
	private Set<SeenCard> seen;
	private int deckSize;
	public static final int BORDER_SIZE = 20; //increase this number to make the player borders thinner, decrease to make it thicker

	/*
     * Player class constructor that takes in 4 parameters name, color, row, and column
     * Initializes player attributes and sets up the deck and seen cards
     */

	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.color = color;
		this.startRow = row;
		this.startColumn = column;
		this.row = row;
		this.column = column;
		deck = new HashSet<Card>();
		seen = new HashSet<SeenCard>();
		deckSize = 0;
	}
	 // Method to reset player attributes to initial state
	public void reset() {
		deck.clear();
		deckSize = 0;
		seen.clear();
		row = startRow;
		column = startColumn;
	}
	
	 /*
     * Method to disprove a suggestion
     * Chooses a card from the player's deck that matches the suggestion (room, weapon, or player)
     */
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
	
	  /*
     * Method to check whether a card is in the deck or seen list
     * Checks if a card exists either in the player's deck or seen cards
     */
	public Boolean hasSeen(Card card) {
		Boolean solution = false;
		//check if card exists in the deck
		for (Card c : deck) {
			if (c.equals(card)) {
				solution = true;
			}
		}
		//check if card exists in the seen list
		for (SeenCard c : seen) {
			if (c.getCard().equals(card)) {
				solution = true;
			}
		}
		return solution;
	}
	
	 /*
     * Method to add a card to the player's deck
     */
	public void addCard(Card newCard) {
		deck.add(newCard);
		deckSize++;
	}
	
	/*
     * Method to update seen cards list
     * Adds a new seen card to the seen cards list if not already present
     */
	public void updateSeen(Card newCard, Color color) {
		if(!hasSeen(newCard)) {
			SeenCard newSeenCard = new SeenCard(newCard, color);
			seen.add(newSeenCard);
		}
	}
	
	/*
     * Overloaded method to update seen cards list using an existing SeenCard object
     */
	public void updateSeen(SeenCard newSeenCard) {
		if(!hasSeen(newSeenCard.getCard())) {
			seen.add(newSeenCard);
		}
	}
	
	/*
     * Method to draw the player on the board
     * Draws a player as a colored circle within a black circle
     */
	public void draw(int innerCircleWidth, int innerCircleHeight, int outerCircleWidth, int outerCircleHeight, Graphics newGraphic) {
		newGraphic.setColor(Color.BLACK);
		newGraphic.fillOval(innerCircleWidth, innerCircleHeight, outerCircleWidth, outerCircleHeight); //draw black circle with slightly smaller colored circle inside
		newGraphic.setColor(color);
		newGraphic.fillOval(innerCircleWidth + outerCircleWidth/BORDER_SIZE, innerCircleHeight + outerCircleHeight/BORDER_SIZE, ((BORDER_SIZE - 1) * outerCircleWidth)/BORDER_SIZE, ((BORDER_SIZE - 1) * outerCircleHeight)/BORDER_SIZE); 
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
	
	public Set<SeenCard> getSeen(){
		return seen;
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
	
}