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
	
	public BoardCell(int newRow, int newColumn, char newInitial) {
		row = newRow;
		column = newColumn;
		initial = newInitial;
		doorDirection = doorDirection.NONE;
		roomLabel = false;
		roomCenter = false;
		adjList = new HashSet<>();
	}
	// Method to add an adjacent cell to the adjacency list	
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
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

	// Method to check if this cell is a doorway
	public boolean isDoorway() {
		return (doorDirection != doorDirection.NONE);
	}

	// Method to get the direction of the door (should return an enum, not Object)
	public Object getDoorDirection() {
		return doorDirection;
	}

	// Method to check if this cell is a label
	public boolean isLabel() {
		return roomLabel;
	}

	// Method to check if this cell is a room center
	public boolean isRoomCenter() {
		return roomCenter;
	}

	// Method to get the secret passage character for this cell
	public char getSecretPassage() {
		return secretPassage;
	}

	// Method to get the initial character for this cell
	public char getInitial() {
		return initial;
	}
	
}








