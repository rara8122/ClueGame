package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;

public class ComputerAITest{
	
	
	private Board board;
	Set<Card> deck;
	
	@BeforeAll
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		deck = board.getDeck();
	}
	

    //@Test
    public void testSelectMoveTargetNoRoomsInList() {
        // Create a computer player with no rooms in the target list
    	Set<ComputerPlayer> players = board.getPlayers();
        List<BoardCell> targets;
        for (ComputerPlayer player : players) {
        	targets = player.selectTarget();
            // Ensure that targets are selected randomly
            assertNotNull(targets);
            assertFalse(targets.isEmpty());
        }
    }

    //@Test
    public void testSelectMoveTargetRoomNotSeen() {
        // Create a computer player with a room in the target list that has not been seen
        Card targetRoom = new Card("Atlantica", CardType.ROOM);
        Set<ComputerPlayer> players = board.getPlayers();
        List<BoardCell> targets;
        boolean contains;
        for (ComputerPlayer player : players) {
        	targets = player.selectTarget();
        	// Ensure that the target room is selected
            assertNotNull(targets);
            contains = false;
            assertTrue(targets.contains(targetRoom));
        }
    }

    //@Test
    public void testSelectMoveTargetRoomSeen() {
        // Create a computer player with a room in the target list that has been seen
        Card targetRoom = new Card("Atlantica", CardType.ROOM);
        Set<ComputerPlayer> players = board.getPlayers();
        List<BoardCell> targets;
        for (ComputerPlayer player : players) {
        	// Ensure that targets are selected randomly, including the seen room
        	targets = player.selectTarget();
        	assertNotNull(targets);
            assertTrue(targets.contains(targetRoom));
        }
  
    }

}