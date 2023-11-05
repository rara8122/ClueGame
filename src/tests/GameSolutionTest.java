package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

public class GameSolutionTest {

	private Board board;
	private Card correctRoom;
	private Card correctWeapon;
	private Card correctPerson;

	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		correctRoom = board.getRoomSoln();
		correctWeapon = board.getWeaponSoln();
		correctPerson = board.getPlayerSoln();
	}

	@Test
	public void testCorrectAccusation() {
		// Check if the correct solution is correctly identified as correct
		assertTrue(board.checkAccusation(correctRoom, correctWeapon, correctPerson));
	}

	@Test
	public void testWrongPersonAccusation() {
		// Check if a solution with the wrong person is correctly identified as incorrect
		Card wrongPerson = new Card("Harry Potter", CardType.PERSON);
		if(correctPerson.equals(wrongPerson)) {
			wrongPerson = new Card("Bruce Wayne (Batman)", CardType.PERSON);
		}
		assertFalse(board.checkAccusation(correctRoom, correctWeapon, wrongPerson));
	}

	@Test
	public void testWrongWeaponAccusation() {
		// Check if a solution with the wrong person is correctly identified as incorrect
		Card wrongWeapon = new Card("The Elder Wand", CardType.WEAPON);
		if(correctPerson.equals(wrongWeapon)) {
			wrongWeapon = new Card("The Skywalker Lightsaber", CardType.WEAPON);
		}
		assertFalse(board.checkAccusation(correctRoom, wrongWeapon, correctPerson));
	}

	@Test
	public void testWrongRoomAccusation() {
		// Check if a solution with the wrong person is correctly identified as incorrect
		Card wrongRoom = new Card("Atlantica", CardType.ROOM);
		if(correctPerson.equals(wrongRoom)) {
			wrongRoom = new Card("Arendelle", CardType.ROOM);
		}
		assertFalse(board.checkAccusation(wrongRoom, correctWeapon, correctPerson));
	}

	//disprove suggestions tests
    //disprove suggestion with no matching cards
    @Test
    public void testDisproveSuggestionNoMatchingCards() {
    	Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
    	humanPlayer.addCard(new Card("France", CardType.ROOM));
    	humanPlayer.addCard(new Card("The Elder Wand", CardType.WEAPON));
    	humanPlayer.addCard(new Card("Tony Stark (Iron Man)", CardType.PERSON));
    	
        Card roomCard = new Card("Agraban", CardType.ROOM);
        Card weaponCard = new Card("The Triforce", CardType.WEAPON);
        Card personCard = new Card("Frodo Baggins", CardType.PERSON);

        Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);
        assertNull(disprovedCard);
    }
    
   
    
    //disprove a suggestion with one matching card
    @Test
    public void testDisproveSuggestionMatchingCategories() {
    	Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
    	
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);

        Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);

        boolean isMatchingCard = false;
        if (humanPlayer.getCards().contains(roomCard) ||humanPlayer.getCards().contains(weaponCard) || humanPlayer.getCards().contains(personCard)) {
            isMatchingCard = true;
        }

        assertTrue(isMatchingCard);
    }
    
    @Test
    public void testDisproveSuggestionMultipleMatchingCards() {
        // Create a player with multiple matching cards
    	Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
        Card matchingCard1 = new Card("Jamestown", CardType.ROOM);
        Card matchingCard2 = new Card("France", CardType.ROOM);
        Card matchingCard3 = new Card("Agraban", CardType.ROOM);
        humanPlayer.addCard(matchingCard1);
        humanPlayer.addCard(matchingCard2);
        humanPlayer.addCard(matchingCard3);
        // Make a suggestion
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("Lazarus Pit Water", CardType.WEAPON);
        Card personCard = new Card("Link", CardType.PERSON);

        // Repeat the test multiple times to ensure randomness
        boolean foundMatchingCard = false;
        for (int i = 0; i < 100; i++) {
            Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);

            // Check if the returned card is one of the matching cards
            if (disprovedCard == matchingCard1 || disprovedCard == matchingCard2 || disprovedCard == matchingCard3) {
                foundMatchingCard = true;
                break;
            }
        }
        // Verify that at least one of the matching cards was chosen randomly
        assertTrue(foundMatchingCard);
    }
    
    
    //handle suggestions tests
    @Test
    public void testSuggestionNoOneCanDisprove() {

        // Make a suggestion that no one can disprove
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);
        Player suggestingPlayer = board.getCurrentPlayer();

        assertNull(board.handleSuggestion(suggestingPlayer, roomCard, weaponCard, personCard));
    }   

}

