package tests; 

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Room;

import java.util.HashSet;
import java.util.Set;
import java.awt.Color;


public class ComputerAITest {
	private static Board board;
	
	//runs before all tests 
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
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
        unseenRoom.setRoomCenter(true);
        Set<BoardCell> targets = new HashSet<>();
        targets.add(new BoardCell(1, 1, 'W', "Walkway"));// Add a walkway cell
        targets.add(unseenRoom);

        // Run the selectTarget method multiple times and ensure it selects the unseen room
        for (int i = 0; i < 10; i++) {
            BoardCell selectedTarget = computerPlayer.selectTarget(targets);
            assertEquals(unseenRoom, selectedTarget);
        }
    }

	@Test
    public void testSelectTargetSeenRoomInList() {
        // Create a computer player
        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLUE, 18, 0);
        computerPlayer.updateSeen(new Card("France", CardType.ROOM));
        
        // Create a set of targets with a room that has been seen
        BoardCell seenRoom = new BoardCell(5, 5, 'B', "France"); // Add a seen room cell
        seenRoom.setRoomCenter(true);
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
	   	 
	 
    @Test
    public void testCreateSuggestionRoomMatchesCurrentLocation() {
    	// Create a ComputerPlayer with a specific current location in a room
        ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLUE, 4, 4);
        Room currentRoom = board.getRoom('E'); // Assuming 'E' corresponds to room Arendelle

        Set<Card> deck = board.getDeck();
        // Add relevant cards to the deck (current room card, unseen weapon, unseen person)
        Card roomCard = new Card("Arendelle", CardType.ROOM);
        Card weaponCard = new Card("The Elder Wand", CardType.WEAPON);
        Card playerCard = new Card("Hermione Granger", CardType.PERSON);
        computerPlayer.updateSeen(roomCard); // Assuming "Arendelle" matches the current room
        computerPlayer.updateSeen(weaponCard); // Unseen weapon
        computerPlayer.addCard(playerCard); // Unseen person
        
        for(int i = 0; i < 100; i++) {
	        computerPlayer.createSolution(deck, currentRoom.getName());
	        // Ensure that the room in the suggestion matches the current room
	        assertTrue(roomCard.equals(computerPlayer.getRoomSuggestion()));
	        assertFalse(weaponCard.equals(computerPlayer.getWeaponSuggestion()));
	        assertFalse(playerCard.equals(computerPlayer.getPersonSuggestion()));
        }
    }
    
    //test if only one weapon not seen, it's selected
    @Test
    public void testCreateSuggestionWithOneUnseenWeapon() {
	    ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLACK, 18, 0);
	    Card unseenWeapon = new Card("The One Ring", CardType.WEAPON);
	    Set<Card> deck = board.getDeck();
		
	    computerPlayer.updateSeen(new Card("Lazarus Pit Water", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Elder Wand", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Skywalker Lightsaber", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Infinity Gauntlet", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Triforce", CardType.WEAPON)); 
	    
	    for(int i = 0; i < 100; i++) {
	        computerPlayer.createSolution(deck, "Arendelle");
	        // Ensure that the room in the suggestion matches the current room
	        assertTrue(computerPlayer.getWeaponSuggestion().equals(unseenWeapon));
        }
	}
    //tests if only one person not seen
    //@Test
    public void testCreateSuggestionWithOneUnseenPerson() {
	    ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLACK, 18, 0);
	    Card unseenPerson = new Card("Link", CardType.PERSON);  
	    Set<Card> deck = board.getDeck();
		
	    computerPlayer.updateSeen(new Card("Bruce Wayne (Batman)", CardType.PERSON)); 
	    computerPlayer.updateSeen(new Card("Harry Potter", CardType.PERSON)); 
	    computerPlayer.updateSeen(new Card("Frodo Baggins", CardType.PERSON)); 
	    computerPlayer.updateSeen(new Card("Luke Skywalker", CardType.PERSON)); 
	    computerPlayer.updateSeen(new Card("Tony Stark (Iron Man)", CardType.PERSON)); 
	    
	    for(int i = 0; i < 100; i++) {
	        computerPlayer.createSolution(deck, "Arendelle");
	        // Ensure that the room in the suggestion matches the current room
	        assertTrue(computerPlayer.getPersonSuggestion().equals(unseenPerson));
        }
    }
    
  
    @Test
    public void testCreateSuggestionWithMultipleUnseenWeapons() {
    	ComputerPlayer computerPlayer = new ComputerPlayer("Bruce Wayne (Batman)", Color.BLACK, 18, 0);
        // Add multiple unseen weapon cards to the deck
        Card unseenWeapon1 = new Card("The One Ring", CardType.WEAPON);
        Card unseenWeapon2 = new Card("The Elder Wand", CardType.WEAPON);
        Card unseenWeapon3 = new Card("The Skywalker Lightsaber", CardType.WEAPON);
        
        Set<Card> deck = board.getDeck();
        Boolean unseen1Picked = false;
        Boolean unseen2Picked = false;
        Boolean unseen3Picked = false;
        
        computerPlayer.updateSeen(new Card("Lazarus Pit Water", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Infinity Gauntlet", CardType.WEAPON)); 
	    computerPlayer.updateSeen(new Card("The Triforce", CardType.WEAPON)); 

        // Run the createSolution method multiple times
        for (int i = 0; i < 100; i++) {
            computerPlayer.createSolution(deck, "");
            if(computerPlayer.getWeaponSuggestion().equals(unseenWeapon1)) {
            	unseen1Picked = true;
            }
            if(computerPlayer.getWeaponSuggestion().equals(unseenWeapon2)) {
            	unseen2Picked = true;
            }
            if(computerPlayer.getWeaponSuggestion().equals(unseenWeapon3)) {
            	unseen3Picked = true;
            }
        }

        // Ensure that all unseen weapons were selected at least once
        assertTrue(unseen1Picked);
        assertTrue(unseen2Picked);
        assertTrue(unseen3Picked);
     
    }
}


