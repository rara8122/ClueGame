package tests;

import java.awt.Color;
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
<<<<<<< HEAD
		
	}
		
	@Test
	public void testPlayersLoadedCorrectly() {
	    Set<Player> players = board.getPlayers();
	    assertEquals(6, players.size()); // Assuming you have 6 players

	    // Test individual players
	    for (Player player : players) {
	        if (player.getName().equals("Bruce Wayne (Batman)")) {
	            assertEquals(Color.BLACK, player.getColor());
	            assertEquals(18, player.getRow());
	            assertEquals(0, player.getColumn());
	        } 
	        else if (player.getName().equals("Harry Potter")) {
	            assertEquals(new Color(245, 191, 3), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(4, player.getColumn());
	        } 
	        else if (player.getName().equals("Frodo Baggins")) {
	            assertEquals(new Color(153, 101, 21), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(15, player.getColumn());
	        } 
	        else if (player.getName().equals("Luke Skywalker")) {
	            assertEquals(new Color(0, 255, 255), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(21, player.getColumn());
	        } 
	        else if (player.getName().equals("Tony Stark (Iron Man)")) {
	            assertEquals(new Color(225, 0, 0), player.getColor());
	            assertEquals(14, player.getRow());
	            assertEquals(29, player.getColumn());
	        } 
	        else if (player.getName().equals("Link")) {
	            assertEquals(new Color(0, 200, 0), player.getColor());
	            assertEquals(19, player.getRow());
	            assertEquals(21, player.getColumn());
	        } 
	        else {
	            fail("Unexpected player: " + player.getName());
	        }
	    }
	}

	
=======
	}
	@Test
	public void testDeal() {
		Set<Player> players = board.getPlayers();
		assertEquals(6, players.size());
		int[] solns = new int[6];
		Set<Card> deck;
		int i = 0;
		for (Player player: players) {
			deck = player.getDeck();
			for (Card card : deck) {
				if(card != null) {
					solns[i]++;
				}
			}
			i++;
		}
		for(i = 0; i < 6; i++) {
			assertEquals(3, solns[i]);
		}
		assertNotEquals(board.getRoomSoln(), null);
		assertNotEquals(board.getWeaponSoln(), null);
		assertNotEquals(board.getPlayerSoln(), null);
	}
>>>>>>> fd7a7d0965131c46a691f5b8c1cfdcc7c61a7560
}



