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
	//constructor 
	public Room(String newName) {
		this.name = newName;
		players = new HashSet<Player>();
	}
	
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	public void removePlayer(Player newPlayer) {
		players.remove(newPlayer);
	}
	
	public void emptyPlayers() {
		players.clear();
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
	
	/*
	 * Draw method to visualize the room
	 */
	public void drawLabel(int width, int height, Graphics newGraphic) {
		if(labelCell != null) {
			int x = width * (labelCell.getColumn() - 1);
			int y = height * (labelCell.getRow() + 1);
			newGraphic.setColor(Color.BLUE); 
			Font font = new Font("Academy Engraved LET", 1, (5*width)/7);
			newGraphic.setFont(font);
			newGraphic.drawString(name, x, y);
		}
	}
	
	public void drawPlayers(int width, int height, int w, int h, Graphics newGraphic) {
		if(labelCell == null) { //walkways don't need special logic
			for (Player player: players) {
				int x = width * player.getColumn() + width/BoardCell.BORDER_SIZE;
				int y = height * player.getRow() + height/BoardCell.BORDER_SIZE;
				player.draw(x, y, w, h, newGraphic);
			}
		} else {//if we're in a room players may be drawn on top of each other
			int x = width * ((centerCell.getColumn()) - players.size()/4);
			int y = height * (centerCell.getRow());
			for (Player player: players) {
				player.draw(x, y, w, h, newGraphic);
				x = x + width/2; //so the players draw offset to eachother
			}
		}
	}
}
