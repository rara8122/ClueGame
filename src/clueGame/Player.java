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
import java.util.Set;

public abstract class Player {

	private String name; 
	private Color color;
	private int row, column; 
	private Set<Card> deck;
	private int deckSize;


	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		deck = new HashSet<Card>();
		deckSize = 0;
	}
	//adds a card to deck
	public void addCard(Card newCard) {
		deck.add(newCard);
		deckSize++;
	}
	//getters 
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
}