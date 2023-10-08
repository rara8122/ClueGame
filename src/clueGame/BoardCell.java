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
	
	public void addAdj(BoardCell adj) {}


	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}


	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
