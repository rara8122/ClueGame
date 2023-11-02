package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Player;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

public class GameSetupTests {
	
	private static Board board;
	
	@BeforeAll
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout306.csv", "ClueSetUp306.txt");
		board.initialize();
	}
	
	
	  @Test
	    public void testPeopleLoadedInCorrectly() {
	        Set<Card> people = board.getPeople();
	        // Check the expected number of people
	        assertEquals(6, people.size());
	        // Check if specific people are in the set
	        assertTrue(people.contains(new Card("Bruce Wayne (Batman)", CardType.PERSON)));
	        assertTrue(people.contains(new Card("Harry Potter", CardType.PERSON)));
	        assertTrue(people.contains(new Card("Frodo Baggins", CardType.PERSON)));
	        assertTrue(people.contains(new Card("Luke Skywalker", CardType.PERSON)));
	        assertTrue(people.contains(new Card("Tony Stark (Iron Man)", CardType.PERSON)));
	        assertTrue(people.contains(new Card("Link", CardType.PERSON)));
	  }
	  
	  @Test
	    public void testWeaponsLoadedInDeck() {
	        Set<Card> weapons = board.getWeapons();
	        // Check if the expected number of weapon cards are in the deck
	        assertEquals(6, weapons.size());
	        // Check if specific weapons are in the deck
	        assertTrue(weapons.contains(new Card("Lazarus Pit Water", CardType.WEAPON)));
	        assertTrue(weapons.contains(new Card("The Elder Wand", CardType.WEAPON)));
	        assertTrue(weapons.contains(new Card("The Triforce", CardType.WEAPON)));
	        assertTrue(weapons.contains(new Card("The One Ring", CardType.WEAPON)));
	    }
    
    @Test
    public void testDeckCreation() {
        Board board = Board.getInstance();
        board.initialize();
        Set<Card> deck = board.getDeck();
        assertEquals(6, deck.size());
        assertTrue(deck.contains(new Card("Lazarus Pit Water", CardType.WEAPON)));
        assertTrue(deck.contains(new Card("Bruce Wayne (Batman)", CardType.PERSON)));
        assertTrue(deck.contains(new Card("A", CardType.ROOM)));
        
        // Repeat for other card types
    }

    
    
}
