package experiment;
import java.util.Set;
import java.util.HashSet;

public class TestBoardCell {
    private int row;
    private int column;
    private boolean isRoom;
    private boolean isOccupied;
    private Set<TestBoardCell> adjacencyList;
    
    //Constructor for class
    public TestBoardCell(int row, int column) {
    	this.row = row;
    	this.column = column;
    	this.isRoom = false;
    	this.isOccupied = false;
    	this.adjacencyList = new HashSet<>();
    }
   
    //This method adds a cell to the adjacency list 
    public void addAdjacency(TestBoardCell cell) {
    	adjacencyList.add(cell);
    }
    
    // This is the method to get the adjacency list for the cell
    public Set<TestBoardCell> getAdjList() {
        return adjacencyList;
    }

    // This method is used to set if the cell is part of a room
    public void setRoom(boolean isRoom) {
        this.isRoom = isRoom;
    }
    
    // Method to check if the cell is part of a room
    public boolean isRoom() {
        return isRoom;
    }

    // This is the method to set if the cell is occupied by another player
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    // This method is to check if the cell is occupied by another player
    public boolean getOccupied() {
        return isOccupied;
    }
}
