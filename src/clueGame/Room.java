/*
*Class: This class contains information about all rooms we need in our game. It contains variables regarding the rooms, and has getters for label cell and center cell, and name. This is essential to have in our game code.  
*Authors: Rachel Davy 
*Date: 10/08/2023
*Collaborators: none
*Sources: none
*/
package clueGame;

import java.awt.Graphics;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	//constructor 
	public Room(String newName) {
		this.name = newName;
	}
	//all setters here
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	//all getters here
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
	public void draw(int width, int height, Graphics newGraphic) {
		if(labelCell != null) {
			int x = width * labelCell.getColumn();
			int y = height * labelCell.getRow();
			newGraphic.drawString(name, x, y);
		}
	}
}
