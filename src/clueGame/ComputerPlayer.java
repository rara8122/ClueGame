/*
 *Class: This class is a subclass of the player class. The computer player represents a player controlled by a computer. There will be 5 computer players implemented in the clue game.
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;

import java.awt.Color;
import java.util.List;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}

	public List<BoardCell> selectTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTargetRoom(Room targetRoom) {
		// TODO Auto-generated method stub
		
	}

}
