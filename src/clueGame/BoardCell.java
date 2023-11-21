/*
*Class: This class is used to define a single cell in the game. it contains appropriate getters and setters to ensure there are no errors in the test classes. This class is similiar to testboardcell class, but is modified for our game. 
*Authors: Melanie Perez, Rachel Davy
*Collaborators: none
*Sources: none
*/
package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private String roomName;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean occupied;
	public static final int BORDER_SIZE = 20; //increase this number to make the black walkway borders thinner, decrease to make it thicker

	
	public BoardCell(int newRow, int newColumn, char newInitial, String newName) {
		row = newRow;
		column = newColumn;
		initial = newInitial;
		doorDirection = DoorDirection.NONE;
		roomLabel = false;
		roomCenter = false;
		roomName = newName;
		adjList = new HashSet<>();
		secretPassage = ' ';
	}
	
	/*
	 *  Method to add an adjacent cell to the adjacency list	
	 */
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	/*
	 *  Method to check if this cell is a doorway
	 */
	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE);
	}
	
	/*
	 * method to check if this cell is a walkway 
	 */
	public boolean isWalkway() {
		return initial == 'W';
	}
		
	/* Method to check if this cell is a label
	 * 
	 */
	public boolean isLabel() {
		return roomLabel;
	}

	/*
	 *  Method to check if this cell is a room center
	 */
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public boolean isOccupied() {
		return occupied;
	}	
	

	
	/*
	 * method to draw the rooms, walkways, and unused spaces on the board.
	 */
	public void draw(int width, int height, Graphics newGraphic, boolean target, int walkwayWidth, int walkwayHeight) {
		int x = width * column;
		int y = height * row;
		newGraphic.setColor(Color.BLACK);
		//drawing logic based on cell type
		if (initial == 'W') {
			newGraphic.setColor(Color.BLACK);
			newGraphic.fillRect(x, y, width, height); //draw a big black rectangle, and draw a slightly smaller rectangle inside it
			if(target) {
				newGraphic.setColor(Color.CYAN);
			} else {
				newGraphic.setColor(Color.YELLOW);
			}
	        newGraphic.fillRect(x + width/BORDER_SIZE, y + height/BORDER_SIZE, walkwayWidth, walkwayHeight);
	    } else if (initial == 'X') {
	    	//draw an unused cell in black
	    	newGraphic.setColor(Color.BLACK);
	        newGraphic.fillRect(x, y, width, height);
	    } else { //rooms
	    	if(secretPassage == ' ') { //if this cell is not a secret passage
		    	if(target) {
					newGraphic.setColor(Color.CYAN);
				} else {
					newGraphic.setColor(Color.GRAY);
				}
		        newGraphic.fillRect(x, y, width, height);
	    	} else { //secret passages draw a trapezoid (with an s inside it)
	    		newGraphic.setColor(Color.BLACK);//the black background
				newGraphic.fillRect(x, y, width, height);
				newGraphic.setColor(Color.YELLOW);
	    		int x2 = x + 4*width/5; //the trapezoid
	    		int[] xs = {x, x2, x2, x}; 
	    		int y2 = y + height/5;
	    		int y3 = y + height;
	    		int[] ys = {y, y2, y3, y3}; 
	    		newGraphic.fillPolygon(xs, ys, 4);
	    		newGraphic.setColor(Color.BLUE);
	    		Font font = new Font("Academy Engraved LET", 1, (9*width/10)); //the s
				newGraphic.setFont(font);
				newGraphic.drawString("S", x + height/8, y + 7*height/8);
	    	}
		}
	}
	
	/*method to draw the doors in the board gui
	 * 
	 */
	public void drawDoor(int width, int height, Graphics newGraphic) {
		int x = width * column;
		int y = height * row;
		newGraphic.setColor(Color.BLUE);
		if(doorDirection == DoorDirection.DOWN) { //we calculate where the door goes based on it's direction (down = upper of the cell below, etc.)
			newGraphic.fillRect(x, y + height, width, height/10);
		}
		if(doorDirection == DoorDirection.LEFT) {
			newGraphic.fillRect(x - width/10, y, width/10, height);
		}
		if(doorDirection == DoorDirection.RIGHT) {
			newGraphic.fillRect(x + width, y, width/10, height);
		}
		if(doorDirection == DoorDirection.UP) {
			newGraphic.fillRect(x, y - height/10, width, height/10);
		}
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
	
	public String getRoomName() {
		return roomName;
	}
	
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
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