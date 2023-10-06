/*
 * Class: This class contains the board for the clue game. It contains the methods calcTargets which calculates legal targets for the player to move to. The method TestBoardCell returns the cell from the board at row,col, and has a method that gets the targets created by calcTargets.
 * Authors: Melanie Perez, Rachel Davy
 * Date: 10/6/2023
 * Collaborators: each other
 * Sources: used java documentation for Hash set. 
 */
package experiment;
import java.util.HashSet;
import java.util.Set;


public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
//constructor for testBoard class 	
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		targets = new HashSet<>();
		visited = new HashSet<>();
		//initializes grid
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++){
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++){
				if(i > 0) {
					grid[i][j].addAdjacency(grid[i - 1][j]);
				}
				if(j > 0) {
					grid[i][j].addAdjacency(grid[i][j - 1]);
				}
				if(i < (ROWS - 1)) {
					grid[i][j].addAdjacency(grid[i + 1][j]);
				}
				if(j < (COLS - 1)) {
					grid[i][j].addAdjacency(grid[i][j + 1]);
				}
			}
		}
		
	}
	
	//method to calculate targets
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	//recursive function to find all targets
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		if (numSteps == 0 || thisCell.isRoom()) {
            targets.add(thisCell);
            return;
        } else {
			Set<TestBoardCell> adjacencyList = thisCell.getAdjList();
			//iterates through the adjacent cells
			for (TestBoardCell adjacentCell : adjacencyList) {
				if (!(visited.contains(adjacentCell) || adjacentCell.getOccupied())) {
		            visited.add(adjacentCell);
		            findAllTargets(adjacentCell, numSteps - 1);	
		            visited.remove(adjacentCell);
	            } 
			}
		}	
	}
	
	//method to get cell from the board
	public TestBoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	//method to get the targets created by calcTargets()
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
}
