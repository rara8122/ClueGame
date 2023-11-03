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
		//assertEquals(21, deck.size());
		Boolean[] solns = new Boolean[21];
		for(int i = 0; i < 21; i++) {
			solns[i] = false;
		}
		for(Card card : deck) {
			if(card.getCardType() == CardType.ROOM) {
				// Rooms and room cards		
				if(card.getCardName().equals("Atlantica")) { solns[0] = true; }
				if(card.getCardName().equals("Arendelle")) { solns[1] = true; }
				if(card.getCardName().equals("Corona")) { solns[2] = true; }
				if(card.getCardName().equals("France")) { solns[3] = true; }
				if(card.getCardName().equals("DunBroch")) { solns[4] = true; }
				if(card.getCardName().equals("China")) { solns[5] = true; }
				if(card.getCardName().equals("New Orleans")) { solns[6] = true; }
				if(card.getCardName().equals("Agraban")) { solns[7] = true; }
				if(card.getCardName().equals("Jamestown")) { solns[8] = true; }
			}
			/*Weapon, Lazarus Pit Water
			Weapon, The Elder Wand
			Weapon, The One Ring
			Weapon, The Skywalker Lightsaber
			Weapon, The Infinity Gauntlet
			Weapon, The Triforce*/
			if(card.getCardType() == CardType.WEAPON) {}
			/*Player, Bruce Wayne (Batman), 0 0 0, 0 0
			Player, Harry Potter, 245 191 3, 0 0
			Player, Frodo Baggins, 153 101 21, 0 0
			Player, Luke Skywalker, 0 255 255, 0 0
			Player, Tony Stark (Iron Man), 225 0 0, 0 0
			Player, Link, 0 200 0, 0 0*/
			if(card.getCardType() == CardType.PERSON) {}
		}
		for(int i = 0; i < 9; i++) {
			assertTrue(solns[i]);
		}
	    
	    // Repeat for other card types
	}

    
    
}
