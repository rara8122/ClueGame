/*
*Class: This class contains information about all rooms we need in our game. It contains variables regarding the rooms, and has getters for label cell and center cell, and name. This is essential to have in our game code.  
*Authors: Rachel Davy 
*Date: 10/08/2023
*Collaborators: none
*Sources: none
*/
package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Set<Player> players;
	
	// Constructor initializing the Room with a name
	public Room(String newName) {
		this.name = newName;
		players = new HashSet<Player>();
	}
	// Method to add a player to the Room
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	// Method to remove a player from the Room
	public void removePlayer(Player newPlayer) {
		players.remove(newPlayer);
	}
	
	// Method to empty the players set
	public void emptyPlayers() {
		players.clear();
	}
		
	 /*
     * Draw method to visualize the room label.
     * Draws the room name in blue color at the labelCell location.
     */
	public void drawLabel(int cellWidth, int cellHeight, Graphics newGraphic) {
		if(labelCell != null) {
			int x = cellWidth * (labelCell.getColumn() - 1);
			int y = cellHeight * (labelCell.getRow() + 1);
			newGraphic.setColor(Color.BLUE); 
			Font font = new Font("Academy Engraved LET", 1, (5*cellWidth)/7);
			newGraphic.setFont(font);
			newGraphic.drawString(name, x, y);
		}
	}
	
	public void drawPlayers(int cellWidth, int cellHeight, int playerWidth, int playerHeight, Graphics newGraphic) {
		if(labelCell == null) { //walkways don't need special logic
			for (Player player: players) {
				int x = cellWidth * player.getColumn() + cellWidth/BoardCell.BORDER_SIZE;
				int y = cellHeight * player.getRow() + cellHeight/BoardCell.BORDER_SIZE;
				player.draw(x, y, playerWidth, playerHeight, newGraphic);
			}
		} else {//if we're in a room players may be drawn on top of each other
			int x = cellWidth * ((centerCell.getColumn()) - players.size()/4);
			int y = cellHeight * (centerCell.getRow());
			for (Player player: players) {
				player.draw(x, y, playerWidth, playerHeight, newGraphic);
				x = x + cellWidth/2; //so the players draw offset to eachother
			}
		}
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
}
