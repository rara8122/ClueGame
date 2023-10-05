package tests;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board; 
	
	@BeforeEach //run before each test
	
	public void setUp() {
		board = new TestBoard();
	}
	
	/*
	 * Test for adjacencies will start here. 
	 */
	@Test
	public void testAdjacency() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
	}
	
}
