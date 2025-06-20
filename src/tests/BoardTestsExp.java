/*
 * Class: This class is a Junit test class which comprises of methods to test the creation of adjacency lists for a 4x4 board, and methods to test target creation on a 4x4 board. All tests should (and do) pass taking in the code added to the testboardcell. 
 * Authors: Melanie Perez, Rachel Davy
 * Date: 10/6/2023
 * Collaborators: none
 * Sources: lecture slides 
 */
package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
		//Methods to test the creation of adjacency lists for a 4x4 board, including:
	//top left corner (i.e., location [0][0])
	@Test
	public void testTopLeftAdjacency() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(2, testList.size());
	}
	//bottom right corner (i.e., location [3][3])
	@Test
	public void testBottomRightAdjacency() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	//a right edge (e.g., location [1][3])
	@Test
	public void testRightEdgeAdjacency() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(3, testList.size());
	}
	//a left edge (e.g., location [3][0])
	@Test
	public void testLeftEdgeAdjacency() {
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(2, testList.size());
	}
	//middle of grid(e.g., location [2][2])
	@Test
	public void testMiddleAdjacency() {
		TestBoardCell cell = board.getCell(2,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(4, testList.size());
	}
	
		//Methods to test target creation on a 4x4 board
	//Test for targets on empty board
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	//Test for targets with the maximum dice roll
	@Test
	public void testTargetsMaxRoll() {
		TestBoardCell cell = board.getCell(2, 3);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	//Test for targets when a room is involved - 2 test cases
	@Test
	public void testTargetsRoom() {
		board.getCell(2, 1).setIsRoom(true);
		TestBoardCell cell = board.getCell(1, 0);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	//Test for targets when an interfering occupied space is involved - 2 test cases
	@Test
	public void testTargetsOccupied() {
		board.getCell(0, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
	}
	//Test that includes rooms and occupied cells - 2 test cases 
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
}
