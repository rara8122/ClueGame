/*
 *Class: This class is a subclass of the player class. The computer player represents a player controlled by a computer. There will be 5 computer players implemented in the clue game.
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private Card roomSolution;
	private Card weaponSolution;
	private Card personSolution;

	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}

	public void addTargetRoom(Room targetRoom) {
		// TODO Auto-generated method stub
		
	}
	
	public void createSolution(Set<Card> deck) {
		Random choice = new Random();
		Card card;
		Object[] deckArray = deck.toArray();
		boolean roomDone = false;
		boolean weaponDone = false;
		boolean personDone = false;
		while(!(roomDone && weaponDone && personDone)) {
			card = ((Card) deckArray[choice.nextInt(deckArray.length)]);//pick a random card
			if(card.getCardType() == CardType.ROOM && !roomDone && !super.hasSeen(card)) { 
				roomSolution = card;
				roomDone = true;
			}else if(card.getCardType() == CardType.WEAPON && !weaponDone && !super.hasSeen(card)) {
				weaponSolution = card; 
				weaponDone = true;
			} else if(card.getCardType() == CardType.PERSON && !personDone && !super.hasSeen(card)) {
				personSolution = card; 
				personDone = true;
			}
		}
	}

	public BoardCell selectTarget(Set<BoardCell> targets) {
		Object[] targetsArray = targets.toArray();
		Random choice = new Random();
		return ((BoardCell) targetsArray[choice.nextInt(targetsArray.length)]);
	}

	public Card getRoomSolution() {
		return roomSolution;
	}
	
	public Card getWeaponSolution() {
		return weaponSolution;
	}
	
	public Card getPersonSolution() {
		return personSolution;
	}

}
