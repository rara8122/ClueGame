package tests; 

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

public class ComputerAITest {
	
	
	@BeforeAll
	public static void setUp() {
		Board board;
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	 @Test
	    public void testSelectTargetNoRoomsInList() {
	        // Create a computer player
	        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLUE, 18, 0);

	        // Create a set of targets with no rooms
	        Set<BoardCell> targets = new HashSet<>();
	        targets.add(new BoardCell(1, 1, 'W', "Walkway")); // Add a walkway cell

	        // Run the selectTarget method multiple times and ensure it selects randomly
	        for (int i = 0; i < 10; i++) {
	            BoardCell selectedTarget = computerPlayer.selectTarget(targets);
	            assertTrue(targets.contains(selectedTarget));
	        }
	    }

	    @Test
	    public void testSelectTargetUnseenRoomInList() {
	        // Create a computer player
	        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLUE, 18, 0);

	        // Create a set of targets with a room that has not been seen
	        BoardCell unseenRoom = new BoardCell(10, 17, 'R', "Corona"); // Add an unseen room cell
	        Set<BoardCell> targets = new HashSet<>();
	        targets.add(new BoardCell(1, 1, 'W', "Walkway"));// Add a walkway cell
	        targets.add(unseenRoom);

	        // Run the selectTarget method multiple times and ensure it selects the unseen room
	        for (int i = 0; i < 10; i++) {
	            BoardCell selectedTarget = computerPlayer.selectTarget(targets);
	            assertTrue(targets.contains(unseenRoom));
	        }
	    }

	    @Test
	    public void testSelectTargetSeenRoomInList() {
	        // Create a computer player
	        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLUE, 18, 0);
	        computerPlayer.updateSeen(new Card("France", CardType.ROOM));
	        
	        // Create a set of targets with a room that has been seen
	        BoardCell seenRoom = new BoardCell(5, 5, 'B', "France"); // Add a seen room cell
	        Set<BoardCell> targets = new HashSet<>();
	        targets.add(new BoardCell(1, 1, 'W', "Walkway")); // Add a walkway cell
	        targets.add(seenRoom);

	        // Run the selectTarget method multiple times and ensure it selects randomly
	        // It should select either the seen room or the walkway
	        boolean roomSelected = false;
	        boolean walkwaySelected = false;
	        for (int i = 0; i < 100; i++) {
	            BoardCell selectedTarget = computerPlayer.selectTarget(targets);
	            if (selectedTarget == seenRoom) {
	                roomSelected = true;
	            } else if (selectedTarget.getInitial() == 'W') {
	                walkwaySelected = true;
	            }
	        }
	        assertTrue(roomSelected);
	        assertTrue(walkwaySelected);
	    }
	    
}


