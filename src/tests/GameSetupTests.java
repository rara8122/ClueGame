package tests;

import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Player;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

public class GameSetupTests {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testDeckCreation() {
		Set<Card> deck = board.getDeck();
		assertEquals(18, deck.size());
		assertTrue(deck.contains(new Card("Lazarus Pit Water", CardType.WEAPON)));
		assertTrue(deck.contains(new Card("Bruce Wayne (Batman)", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Atlantica", CardType.ROOM)));
		assertTrue(deck.contains(new Card("Lazarus Pit Water", CardType.WEAPON)));
		assertTrue(deck.contains(new Card("The Elder Wand", CardType.WEAPON)));
		assertTrue(deck.contains(new Card("The Triforce", CardType.WEAPON)));
		assertTrue(deck.contains(new Card("The One Ring", CardType.WEAPON)));
		assertTrue(deck.contains(new Card("Bruce Wayne (Batman)", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Harry Potter", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Frodo Baggins", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Luke Skywalker", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Tony Stark (Iron Man)", CardType.PERSON)));
		assertTrue(deck.contains(new Card("Link", CardType.PERSON)));
	    
	    // Repeat for other card types
	}

    
    
}
