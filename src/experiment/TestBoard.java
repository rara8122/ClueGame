/*
 * Class: This class contains the board for the clue game. It contains the methods calcTargets which calculates legal targets for the player to move to. The method TestBoardCell returns the cell from the board at row,col, and has a method that gets the targets created by calcTargets. This is the basic code for this class.  
 * Authors: Melanie Perez, Rachel Davy
 * Date: 10/5/2023
 * Sources: none
 */
package experiment;
import java.util.HashSet;
import java.util.Set;


public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	
//constructor for testBoard class 	
	public TestBoard() {
	}
	
	//method to calculate targets
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	//method to get cell from the board
	public TestBoardCell getCell(int row, int column) {
		return new TestBoardCell(row, column);
	}
	//method to get the targets created by calcTargets()
	public Set<TestBoardCell> getTargets(){
		return new HashSet<>();
	}

}
