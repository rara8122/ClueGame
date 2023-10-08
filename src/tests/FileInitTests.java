package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Room;

public class FileInitTests {
 
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 30;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		}
		
	//test to check if the rooms can be retrieved
		@Test
		public void testRoomLabels() {
	
			assertEquals("Atlantica", board.getRoom('A').getName() );
			assertEquals("Arendelle", board.getRoom('E').getName() );
			assertEquals("Corona", board.getRoom('R').getName() );
			assertEquals("France", board.getRoom('B').getName() );
			assertEquals("DunBroch", board.getRoom('M').getName() );
			assertEquals("China", board.getRoom('F').getName() );
			assertEquals("New Orleans", board.getRoom('T').getName() );
			assertEquals("Agraban", board.getRoom('J').getName() );
			assertEquals("Jamestown", board.getRoom('P').getName() );
		}

		//test to ensure we have appropriate rows/columns
		@Test
		public void testBoardDimensions() {
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());
		}
		/*
		@Test
		public void FourDoorDirections() {
			BoardCell cell = board.getCell(8, 7);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
			cell = board.getCell(7, 12);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.UP, cell.getDoorDirection());
			cell = board.getCell(4, 8);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
			cell = board.getCell(16, 9);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
			// Test that walkways are not doors
			cell = board.getCell(12, 14);
			assertFalse(cell.isDoorway());
		}
		*/
		//test to check we have the correct number of doors
		@Test
		public void testNumberOfDoorways() {
			int numDoors = 0;
			for (int row = 0; row < board.getNumRows(); row++)
				for (int col = 0; col < board.getNumColumns(); col++) {
					BoardCell cell = board.getCell(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(14, numDoors);
		}
		
		// Test a few room cells to ensure the room init is correct.
		@Test
		public void testRooms() {
			//standard room location 
			BoardCell cell = board.getCell( 3,3);
			Room room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "France" ) ;
			assertFalse( cell.isLabel() );
			assertFalse( cell.isRoomCenter() ) ;
			assertFalse( cell.isDoorway()) ;

			// this is a label cell to test
			cell = board.getCell(2, 19);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Lounge" ) ;
			assertTrue( cell.isLabel() );
			assertTrue( room.getLabelCell() == cell );
			
			// this is a room center cell to test
			cell = board.getCell(20, 11);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Ballroom" ) ;
			assertTrue( cell.isRoomCenter() );
			assertTrue( room.getCenterCell() == cell );
			
			// this is a secret passage test
			cell = board.getCell(3, 0);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Study" ) ;
			assertTrue( cell.getSecretPassage() == 'K' );
			
			// test a walkway
			cell = board.getCell(5, 0);
			room = board.getRoom( cell ) ;
			// Note for our purposes, walkways and closets are rooms
			assertTrue( room != null );
			assertEquals( room.getName(), "Walkway" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );
			
			// test a closet
			cell = board.getCell(8, 18);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Unused" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );	
		}
	}



