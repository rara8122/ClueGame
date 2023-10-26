/*
*Class: This class is used to define a single cell in the game. it contains appropriate getters and setters to ensure there are no errors in the test classes. This class is similiar to testboardcell class, but is modified for our game. 
*Authors: Melanie Perez, Rachel Davy
*Collaborators: none
*Sources: none
*/
package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean occupied;

	
	public BoardCell(int newRow, int newColumn, char newInitial) {
		row = newRow;
		column = newColumn;
		initial = newInitial;
		doorDirection = doorDirection.NONE;
		roomLabel = false;
		roomCenter = false;
		adjList = new HashSet<>();
		secretPassage = ' ';
	}
	
	// Method to add an adjacent cell to the adjacency list	
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	// Method to check if this cell is a doorway
	public boolean isDoorway() {
		return (doorDirection != doorDirection.NONE);
	}
	//method to check if this cell is a walkway 
	public boolean isWalkway() {
		return initial == 'W';
	}
		
	// Method to check if this cell is a label
	public boolean isLabel() {
		return roomLabel;
	}

	// Method to check if this cell is a room center
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public boolean isOccupied() {
		return occupied;
	}	
	//all setters here
	public void setOccupied(boolean b) {
		occupied = b;
	}
	public void setDoorDirection(DoorDirection newDoorDirection) {
		doorDirection = newDoorDirection;
	}
	
	public void setRoomCenter(boolean newRoomCenter) {
		roomCenter = newRoomCenter;
	}
	
	public void setRoomLabel(boolean newRoomLabel) {
		roomLabel = newRoomLabel;
	}
	
	public void setSecretPassage(char newSecretPassage) {
		secretPassage = newSecretPassage;
	}
	//all getters here
	public char getInitial() {
		return initial;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}


	
}