/*
*Class: This class contains information about all rooms we need in our game. It contains variables regarding the rooms, and has getters for label cell and center cell, and name. This is essential to have in our game code.  
*Authors: Rachel Davy 
*Date: 10/08/2023
*Collaborators: none
*Sources: none
*/
package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String newName) {
		this.name = newName;
	}
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
}
