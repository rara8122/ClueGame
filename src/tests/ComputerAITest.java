package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Room;

public class ComputerAITest{
	
	
	private Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	

    @Test
    public void testSelectMoveTargetNoRoomsInList() {
        // Create a computer player with no rooms in the target list
        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.black, 18, 0 );
        List<BoardCell> targets = computerPlayer.selectTarget();

        // Ensure that targets are selected randomly
        assertNotNull(targets);
        assertFalse(targets.isEmpty());
    }

    @Test
    public void testSelectMoveTargetRoomNotSeen() {
        // Create a computer player with a room in the target list that has not been seen
        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.black, 18, 0);
        Room targetRoom = new Room("Atlantica");
        computerPlayer.addTargetRoom(targetRoom);

        List<BoardCell> targets = computerPlayer.selectTarget();

        // Ensure that the target room is selected
        assertNotNull(targets);
        assertTrue(targets.contains(targetRoom));
    }

    @Test
    public void testSelectMoveTargetRoomSeen() {
        // Create a computer player with a room in the target list that has been seen
        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.black, 18, 0);
        Room targetRoom = new Room("Atlantica");
        computerPlayer.addTargetRoom(targetRoom);
        computerPlayer.addTargetRoom(targetRoom);

        List<BoardCell> targets = computerPlayer.selectTarget();

        // Ensure that targets are selected randomly, including the seen room
        assertNotNull(targets);
        assertTrue(targets.contains(targetRoom));
    }

}