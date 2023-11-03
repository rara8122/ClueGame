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
		assertEquals(21, deck.size());
		Boolean[] solns = new Boolean[21];
		for(int i = 0; i < 21; i++) {
			solns[i] = false;
		}
		for(Card card : deck) {
			if(card.getCardType() == CardType.ROOM) {
				if(card.getCardName().equals("Atlantica")) { solns[0] = true; }//
				if(card.getCardName().equals("Arendelle")) { solns[1] = true; }//
				if(card.getCardName().equals("Corona")) { solns[2] = true; }//
				if(card.getCardName().equals("France")) { solns[3] = true; }//
				if(card.getCardName().equals("DunBroch")) { solns[4] = true; }//
				if(card.getCardName().equals("China")) { solns[5] = true; }//
				if(card.getCardName().equals("New Orleans")) { solns[6] = true; }//
				if(card.getCardName().equals("Agrabah")) { solns[7] = true; }//
				if(card.getCardName().equals("Jamestown")) { solns[8] = true; }//
			}
			if(card.getCardType() == CardType.WEAPON) {
				if(card.getCardName().equals("Lazarus Pit Water")) { solns[9] = true; }//
				if(card.getCardName().equals("The Elder Wand")) { solns[10] = true; }
				if(card.getCardName().equals("The One Ring")) { solns[11] = true; }//
				if(card.getCardName().equals("The Skywalker Lightsaber")) { solns[12] = true; }
				if(card.getCardName().equals("The Infinity Gauntlet")) { solns[13] = true; }//
				if(card.getCardName().equals("The Triforce")) { solns[14] = true; }
			}
			if(card.getCardType() == CardType.PERSON) {
				if(card.getCardName().equals("Bruce Wayne (Batman)")) { solns[15] = true; }//
				if(card.getCardName().equals("Harry Potter")) { solns[16] = true; }//
				if(card.getCardName().equals("Frodo Baggins")) { solns[17] = true; }//
				if(card.getCardName().equals("Luke Skywalker")) { solns[18] = true; }//
				if(card.getCardName().equals("Tony Stark (Iron Man)")) { solns[19] = true; }//
				if(card.getCardName().equals("Link")) { solns[20] = true; }//
			}
		}
		for(int i = 0; i < 21; i++) {
			assertTrue(solns[i]);
		}
	} 
}
