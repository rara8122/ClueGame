/*
*Class: This class is used to define a single cell in the game. it contains appropriate getters and setters to ensure there are no errors in the test classes. This class is similiar to testboardcell class, but is modified for our game. 
*Authors: Melanie Perez, Rachel Davy
*Collaborators: none
*Sources: none
*/
package clueGame;

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
	
	// Method to add an adjacent cell to the adjacency list	
	public void addAdj(BoardCell adj) {}

	// Method to check if this cell is a doorway
	public boolean isDoorway() {
		return false;
	}

	// Method to get the direction of the door (should return an enum, not Object)
	public Object getDoorDirection() {
		return null;
	}

	// Method to check if this cell is a label
	public boolean isLabel() {
		return false;
	}

	// Method to check if this cell is a room center
	public boolean isRoomCenter() {
		return false;
	}

	// Method to get the secret passage character for this cell
	public char getSecretPassage() {
		return 0;
	}

	// Method to get the initial character for this cell
	public char getInitial() {
		return 0;
	}
	
}








