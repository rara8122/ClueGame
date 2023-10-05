/*
 * Class: This class is a Junit test class which comprises of methods to test the creation of adjacency lists for a 4x4 board, and methods to test target creation on a 4x4 board. These tests fail since the other classes contain minimum code.
 * Authors: Melanie Perez, Rachel Davy
 * Date: 10/5/2023
 * Sources: lecture slides 
 */
package tests;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;
import junit.framework.Assert;

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
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertEquals(2, testList.size());
	}
	//bottom right corner (i.e., location [3][3])
	@Test
	public void testBottomRightAdjacency() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
	}
	//a right edge (e.g., location [1][3])
	@Test
	public void testRightEdgeAdjacency() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList.size());
	}
	//a left edge (e.g., location [3][0])
	@Test
	public void testLeftEdgeAdjacency() {
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertEquals(2, testList.size());
	}
	//middle of grid(e.g., location [2][2])
	@Test
	public void testMiddleAdjacency() {
		TestBoardCell cell = board.getCell(2,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(4, testList.size());
	}
	
		//Methods to test target creation on a 4x4 board
	//Test for targets on empty board
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	//Test for targets with the maximum dice roll
		@Test
		public void testTargetsMaxRoll() {
			TestBoardCell cell = board.getCell(2, 3);
			board.calcTargets(cell, 6);
			Set<TestBoardCell> targets = board.getTargets();
			Assert.assertEquals(7, targets.size());
			Assert.assertTrue(targets.contains(board.getCell(0, 1)));
			Assert.assertTrue(targets.contains(board.getCell(1, 0)));
			Assert.assertTrue(targets.contains(board.getCell(3, 0)));
			Assert.assertTrue(targets.contains(board.getCell(0, 3)));
			Assert.assertTrue(targets.contains(board.getCell(2, 1)));
			Assert.assertTrue(targets.contains(board.getCell(1, 2)));
			Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		}
	//Test for targets when a room is involved - 2 test cases
	@Test
	public void testTargetsRoom() {
		board.getCell(2, 1).setIsRoom(true);
		TestBoardCell cell = board.getCell(1, 0);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
	}
	//Test for targets when an interfering occupied space is involved - 2 test cases
	@Test
	public void testTargetsOccupied() {
		board.getCell(0, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
	}
	//Test that includes rooms and occupied cells - 2 test cases 
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
}
