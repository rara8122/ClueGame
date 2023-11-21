/*
 *Class: This class is a subclass of the player class. The computer player represents a player controlled by a computer. There will be 5 computer players implemented in the clue game.
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

public class ComputerPlayer extends Player{
	
	private Card roomSolution;
	private Card weaponSolution;
	private Card personSolution;

	/*
	 * constructor that takes in the computer player's name, color, column
	 */
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}
	
	/*method to create a solution
	 * 
	 */
	public void createSolution(Set<Card> deck, String currentRoom) {
		Random choice = new Random();
		Card card;
		Object[] deckArray = deck.toArray();
		boolean roomDone = false;
		boolean weaponDone = false;
		boolean personDone = false;
		// Iterate through the deck to find a matching room card
		for (Card room: deck) {
			if(room.getCardType() == CardType.ROOM && !roomDone && room.getCardName() == currentRoom) { 
				roomSolution = room;
				roomDone = true;
			}
		}
		// If a matching room card is not found, set the room solution to null
		if (!roomDone) {
			roomSolution = null;
		}
		 // Keep looping until both weapon and person are chosen
		while(!(weaponDone && personDone)) {
			card = ((Card) deckArray[choice.nextInt(deckArray.length)]);//pick a random card
	        // Check if it's a weapon card, not already chosen, and not seen by the player
			if(card.getCardType() == CardType.WEAPON && !weaponDone && !super.hasSeen(card)) {
				weaponSolution = card; 
				weaponDone = true;
			} else if(card.getCardType() == CardType.PERSON && !personDone && !super.hasSeen(card)) {
				personSolution = card; 
				personDone = true;
			}
		}
	}
	/*method to select  a target
	 * 
	 */
	public BoardCell selectTarget(Set<BoardCell> targets) {
		Set<BoardCell> chosenTargets = new HashSet<BoardCell>();
		// Iterate through the available targets
		for(BoardCell target: targets) {
			if(target.isRoomCenter()) {
				// Check if the target is a room center
				if(!super.hasSeen(new Card(target.getRoomName(), CardType.ROOM))) {
					chosenTargets.add(target);
				}
			}
		}
		// If no room centers are chosen, add all available targets to chosen targets
		if(chosenTargets.isEmpty()){
			chosenTargets.addAll(targets);
		}
		Object[] targetsArray = chosenTargets.toArray();
		Random choice = new Random();
		return ((BoardCell) targetsArray[choice.nextInt(targetsArray.length)]);
	}
	
	//getters and setters
	public Card getRoomSuggestion() {
		return roomSolution;
	}
	
	public Card getWeaponSuggestion() {
		return weaponSolution;
	}
	
	public Card getPersonSuggestion() {
		return personSolution;
	}

	public void setRoomSolution(Card roomSolution2) {
	}

	public void setCurrentRoom(Room currentRoom) {
		
	}

	public Object getRoom() {
		return null;
	}

	public ComputerPlayer getSuggestion() {
		return null;
	}
}