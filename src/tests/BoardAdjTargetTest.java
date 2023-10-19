/*
 * Class: This class comprises of tests that test adjacency lists for cells in various rooms, test various scenarios involving walkways, tests target calculations, and more. The purpose of this class is to implement TDD in our clue game by writing tests before writing code. Once we are ready to write the code, it should pass these tests.  
 * Author: Melanie Perez, Rachel Davy
 * Date: 10/14/2023
 * Sources: none
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are PINK on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, France that only has a single door (but has a secret passage)
		Set<BoardCell> testList = board.getAdjList(4, 4);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(17, 24)));
		
		// now test China (note not marked since multiple test here) (2 doors)
		testList = board.getAdjList(4, 11);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 10)));
		assertTrue(testList.contains(board.getCell(5, 18)));
		
		// one more room, Corona (2 doors and a secret passage)
		testList = board.getAdjList(16, 13);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(15, 8)));
		assertTrue(testList.contains(board.getCell(14, 16)));
		assertTrue(testList.contains(board.getCell(4, 25)));
		
		//Test Dunbroch (not center)
		testList = board.getAdjList(10, 15);
		assertEquals(0, testList.size());
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are DARK GREEN on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(15, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(16, 13)));
		assertTrue(testList.contains(board.getCell(15, 7)));
		assertTrue(testList.contains(board.getCell(14, 8)));
		assertTrue(testList.contains(board.getCell(16, 8)));

		testList = board.getAdjList(1, 10);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(1, 9)));
		assertTrue(testList.contains(board.getCell(1, 11)));
		assertTrue(testList.contains(board.getCell(4, 11)));
		
		testList = board.getAdjList(19, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(18, 3)));
		assertTrue(testList.contains(board.getCell(19, 2)));
		
	}
	
	// Test a variety of walkway scenarios
	// These tests are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test near (not adj to) door & test left edge
		Set<BoardCell> testList = board.getAdjList(18, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 0)));
		
		// Test adj to door
		testList = board.getAdjList(5, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 18)));
		assertTrue(testList.contains(board.getCell(5, 16)));
		assertTrue(testList.contains(board.getCell(6, 17)));

		// Test adjacent to walkways
		testList = board.getAdjList(7, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 5)));
		assertTrue(testList.contains(board.getCell(7, 7)));
		assertTrue(testList.contains(board.getCell(8, 6)));
		assertTrue(testList.contains(board.getCell(6, 6)));

		// Test next to unused
		testList = board.getAdjList(1,28);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 27)));
		assertTrue(testList.contains(board.getCell(2, 28)));
		
		//test closet
		testList = board.getAdjList(10, 10);
		assertEquals(0, testList.size());
		
		//test bottom edge
		testList = board.getAdjList(19, 10);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(18, 10)));
		assertTrue(testList.contains(board.getCell(19, 9)));
		assertTrue(testList.contains(board.getCell(19, 11)));
		
		//test top edge
		testList = board.getAdjList(0, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 21)));
		
		//test right edge
		testList = board.getAdjList(5, 29);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 28)));
		assertTrue(testList.contains(board.getCell(6, 29)));
		assertTrue(testList.contains(board.getCell(4, 29)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are SKY BLUE on the planning spreadsheet
	@Test
	public void testTargetsDunBroch() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(12, 17)));
		assertTrue(targets.contains(board.getCell(11, 20)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 17), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(13, 16)));
		assertTrue(targets.contains(board.getCell(14, 17)));
		assertTrue(targets.contains(board.getCell(9, 20)));
		assertTrue(targets.contains(board.getCell(10, 21)));
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 17), 4);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(12, 16)));
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertTrue(targets.contains(board.getCell(14, 18)));
		
		assertTrue(targets.contains(board.getCell(8, 20)));
		assertTrue(targets.contains(board.getCell(9, 21)));
		assertTrue(targets.contains(board.getCell(10, 20)));
		assertTrue(targets.contains(board.getCell(11, 21)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(13, 21)));
		assertTrue(targets.contains(board.getCell(14, 20)));
	}
	
	@Test
	public void testTargetsAtlantica() {
		// test a roll of 1
		board.calcTargets(board.getCell(4, 25), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(16, 13)));
		assertTrue(targets.contains(board.getCell(6, 25)));
	}
	
	@Test
	public void testTargetsNewOrleans() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 4), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(10, 1)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 4), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(11, 0)));
		assertTrue(targets.contains(board.getCell(12, 1)));
		assertTrue(targets.contains(board.getCell(9, 0)));
		assertTrue(targets.contains(board.getCell(8, 1)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 4), 4);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(13, 1)));
		assertTrue(targets.contains(board.getCell(12, 0)));
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(10, 0)));
		assertTrue(targets.contains(board.getCell(9, 1)));
		assertTrue(targets.contains(board.getCell(8, 0)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(8, 2)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are SKY BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(12, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(11, 21)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(13, 21)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 21), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(9, 21)));
		assertTrue(targets.contains(board.getCell(10, 20)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(11, 21)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(13, 21)));
		assertTrue(targets.contains(board.getCell(14, 20)));
		assertTrue(targets.contains(board.getCell(15, 21)));
		assertTrue(targets.contains(board.getCell(14, 22)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 21), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(8, 21)));
		assertTrue(targets.contains(board.getCell(9, 20)));
		assertTrue(targets.contains(board.getCell(11, 20)));
		assertTrue(targets.contains(board.getCell(10, 21)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(14, 19)));
		assertTrue(targets.contains(board.getCell(14, 21)));
		assertTrue(targets.contains(board.getCell(16, 21)));
		assertTrue(targets.contains(board.getCell(15, 20)));
		assertTrue(targets.contains(board.getCell(14, 23)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(7, 14)));
		assertTrue(targets.contains(board.getCell(7, 12)));
		assertTrue(targets.contains(board.getCell(8, 13)));
		assertTrue(targets.contains(board.getCell(6, 13)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(7, 13), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(7, 16)));
		assertTrue(targets.contains(board.getCell(6, 15)));
		assertTrue(targets.contains(board.getCell(7, 14)));
		assertTrue(targets.contains(board.getCell(8, 13)));
		assertTrue(targets.contains(board.getCell(9, 14)));
		assertTrue(targets.contains(board.getCell(10, 13)));
		assertTrue(targets.contains(board.getCell(8, 11)));
		assertTrue(targets.contains(board.getCell(7, 12)));
		assertTrue(targets.contains(board.getCell(5, 14)));
		assertTrue(targets.contains(board.getCell(6, 13)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(7, 13), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(6, 16)));
		assertTrue(targets.contains(board.getCell(7, 17)));
		assertTrue(targets.contains(board.getCell(6, 14)));
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(5, 15)));
		assertTrue(targets.contains(board.getCell(8, 14)));
		assertTrue(targets.contains(board.getCell(8, 12)));
		assertTrue(targets.contains(board.getCell(9, 13)));
		assertTrue(targets.contains(board.getCell(10, 14)));
		assertTrue(targets.contains(board.getCell(11, 13)));
		assertTrue(targets.contains(board.getCell(8, 10)));
		assertTrue(targets.contains(board.getCell(6, 12)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(0, 15), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(1, 15)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(0, 15), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 13)));
		assertTrue(targets.contains(board.getCell(2, 14)));
		assertTrue(targets.contains(board.getCell(2, 16)));
		assertTrue(targets.contains(board.getCell(1, 17)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(0, 15), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 12)));
		assertTrue(targets.contains(board.getCell(1, 14)));
		assertTrue(targets.contains(board.getCell(2, 15)));
		assertTrue(targets.contains(board.getCell(1, 16)));
		assertTrue(targets.contains(board.getCell(2, 17)));
		assertTrue(targets.contains(board.getCell(1, 18)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 1 diagonal upper left
		board.getCell(6, 28).setOccupied(true);
		board.calcTargets(board.getCell(7, 27), 4);
		board.getCell(6, 28).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(6, 24)));
		assertTrue(targets.contains(board.getCell(7, 23)));
		assertTrue(targets.contains(board.getCell(4, 25)));	
		assertTrue(targets.contains(board.getCell(7, 25)));
		assertTrue(targets.contains(board.getCell(6, 26)));
		assertTrue(targets.contains(board.getCell(6, 28)));
		assertTrue(targets.contains(board.getCell(5, 29)));
		assertTrue(targets.contains(board.getCell(6, 28)));
		assertTrue(targets.contains(board.getCell(7, 29)));
		assertTrue(targets.contains(board.getCell(9, 29)));
		assertTrue(targets.contains(board.getCell(10, 28)));
		assertFalse( targets.contains( board.getCell(4, 28))) ;
		assertFalse( targets.contains( board.getCell(8, 28))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(4, 25).setOccupied(true);
		board.getCell(7, 25).setOccupied(true);
		board.calcTargets(board.getCell(6, 25), 1);
		board.getCell(4, 25).setOccupied(false);
		board.getCell(7, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(4, 25)));	
		assertTrue(targets.contains(board.getCell(6, 24)));	
		assertTrue(targets.contains(board.getCell(6, 26)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(15, 3).setOccupied(true);
		board.calcTargets(board.getCell(18, 3), 3);
		board.getCell(15, 3).setOccupied(false);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(19, 3)));

	}
}
