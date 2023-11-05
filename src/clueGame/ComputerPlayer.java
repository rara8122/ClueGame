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

	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}

	public void addTargetRoom(Room targetRoom) {
		// TODO Auto-generated method stub
		
	}
	
	public void createSolution(Set<Card> deck, String currentRoom) {
		Random choice = new Random();
		Card card;
		Object[] deckArray = deck.toArray();
		boolean roomDone = false;
		boolean weaponDone = false;
		boolean personDone = false;
		for (Card room: deck) {
			if(room.getCardType() == CardType.ROOM && !roomDone && room.getCardName() == currentRoom) { 
				roomSolution = room;
				roomDone = true;
			}
		}
		if (!roomDone) {
			roomSolution = null;
		}
		while(!(weaponDone && personDone)) {
			card = ((Card) deckArray[choice.nextInt(deckArray.length)]);//pick a random card
			if(card.getCardType() == CardType.WEAPON && !weaponDone && !super.hasSeen(card)) {
				weaponSolution = card; 
				weaponDone = true;
			} else if(card.getCardType() == CardType.PERSON && !personDone && !super.hasSeen(card)) {
				personSolution = card; 
				personDone = true;
			}
		}
	}

	public BoardCell selectTarget(Set<BoardCell> targets) {
		Set<BoardCell> chosenTargets = new HashSet<BoardCell>();
		for(BoardCell target: targets) {
			if(target.isRoomCenter()) {
				if(!super.hasSeen(new Card(target.getRoomName(), CardType.ROOM))) {
					chosenTargets.add(target);
				}
			}
		}
		if(chosenTargets.isEmpty()){
			chosenTargets.addAll(targets);
		}
		Object[] targetsArray = chosenTargets.toArray();
		Random choice = new Random();
		return ((BoardCell) targetsArray[choice.nextInt(targetsArray.length)]);
	}

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
		// TODO Auto-generated method stub
		
	}

	public void setCurrentRoom(Room currentRoom) {
		// TODO Auto-generated method stub
		
	}

	public Object getRoom() {
		// TODO Auto-generated method stub
		return null;
	}

	public ComputerPlayer getSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

}
