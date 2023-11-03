/*
 *Class: This class is where all of the tests for the game setup goes. There are tests to ensure the players and weapons were loaded correctly from ClueSetup.txt. There are also tests that ensure the deck of all cards was created, and the solution to the game is dealt. All tests pass. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 11/02/2023
 *Collaborators: none
 *Sources: none
 */
package tests;

import java.awt.Color;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.Player;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;


public class GameSetupTests {
	
	private static Board board;
	
	//runs before all tests 
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	//this test ensures that the deck of all cards is created (composed of rooms, weapons, and people.)
	@Test
	public void testDeckCreation() {
		Set<Card> deck = board.getDeck();
		assertEquals(21, deck.size());
		Boolean[] solns = new Boolean[21];
		// Initialize solns array to false
		for(int i = 0; i < 21; i++) {
			solns[i] = false;
		}
		for(Card card : deck) {
			if(card.getCardType() == CardType.ROOM) {
				// Check and mark room cards
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
				// Check and mark weapon cards
				if(card.getCardName().equals("Lazarus Pit Water")) { solns[9] = true; }//
				if(card.getCardName().equals("The Elder Wand")) { solns[10] = true; }
				if(card.getCardName().equals("The One Ring")) { solns[11] = true; }//
				if(card.getCardName().equals("The Skywalker Lightsaber")) { solns[12] = true; }
				if(card.getCardName().equals("The Infinity Gauntlet")) { solns[13] = true; }//
				if(card.getCardName().equals("The Triforce")) { solns[14] = true; }
			}
			if(card.getCardType() == CardType.PERSON) {
				// Check and mark person cards
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
	
	 // This test ensures that the players were loaded correctly	
	@Test
	public void testPlayersLoadedCorrectly() {
	    Set<ComputerPlayer> players = board.getPlayers();
	    assertEquals(5, players.size()); // Assuming you have 6 players

	    // Test individual players
	    for (Player player : players) {
	        if (player.getName().equals("Bruce Wayne (Batman)")) {
	        	// Check player details for Batman
	            assertEquals(Color.BLACK, player.getColor());
	            assertEquals(18, player.getRow());
	            assertEquals(0, player.getColumn());
	        } 
	        else if (player.getName().equals("Harry Potter")) {
	        	// Check player details for Harry Potter
	            assertEquals(new Color(245, 191, 3), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(4, player.getColumn());
	        } 
	        else if (player.getName().equals("Frodo Baggins")) {
	        	// Check player details for Frodo Baggins
	            assertEquals(new Color(153, 101, 21), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(15, player.getColumn());
	        } 
	        else if (player.getName().equals("Luke Skywalker")) {
	        	// Check player details for Luke Skywalker
	            assertEquals(new Color(0, 255, 255), player.getColor());
	            assertEquals(0, player.getRow());
	            assertEquals(21, player.getColumn());
	        } 
	        else if (player.getName().equals("Tony Stark (Iron Man)")) {
	        	// Check player details for Iron Man
	            assertEquals(new Color(225, 0, 0), player.getColor());
	            assertEquals(14, player.getRow());
	            assertEquals(29, player.getColumn());
	        } 
	        else if (player.getName().equals("Link")){
	        	// Check player details for Link
	            assertEquals(new Color(0, 200, 0), player.getColor());
	            assertEquals(19, player.getRow());
	            assertEquals(21, player.getColumn());
	        } 
	        else {
	            fail("Unexpected player: " + player.getName());
	        }
	    }
	    Player player = board.getUser();
	    if (player.getName().equals("Bruce Wayne (Batman)")) {
        	// Check player details for Batman
            assertEquals(Color.BLACK, player.getColor());
            assertEquals(18, player.getRow());
            assertEquals(0, player.getColumn());
        } 
        else if (player.getName().equals("Harry Potter")) {
        	// Check player details for Harry Potter
            assertEquals(new Color(245, 191, 3), player.getColor());
            assertEquals(0, player.getRow());
            assertEquals(4, player.getColumn());
        } 
        else if (player.getName().equals("Frodo Baggins")) {
        	// Check player details for Frodo Baggins
            assertEquals(new Color(153, 101, 21), player.getColor());
            assertEquals(0, player.getRow());
            assertEquals(15, player.getColumn());
        } 
        else if (player.getName().equals("Luke Skywalker")) {
        	// Check player details for Luke Skywalker
            assertEquals(new Color(0, 255, 255), player.getColor());
            assertEquals(0, player.getRow());
            assertEquals(21, player.getColumn());
        } 
        else if (player.getName().equals("Tony Stark (Iron Man)")) {
        	// Check player details for Iron Man
            assertEquals(new Color(225, 0, 0), player.getColor());
            assertEquals(14, player.getRow());
            assertEquals(29, player.getColumn());
        } 
        else if (player.getName().equals("Link")){
        	// Check player details for Link
            assertEquals(new Color(0, 200, 0), player.getColor());
            assertEquals(19, player.getRow());
            assertEquals(21, player.getColumn());
        } 
        else {
            fail("Unexpected player: " + player.getName());
        }
	    assertNotEquals(board.getUser(), null);
	}
	
	 // This test ensures that the deal() method correctly distributes cards to players 
	@Test
	public void testDeal() {
		Set<ComputerPlayer> players = board.getPlayers();
		assertEquals(5, players.size());
		int[] solns = new int[6];
		Set<Card> deck;
		int i = 0;
		//loops through each player
		for (Player player: players) {
			deck = player.getDeck();
			// Count the number of cards in each player's hand
			for (Card card : deck) {
				if(card != null) {
					solns[i]++;
				}
			}
			i++;
		}
		Player player = board.getUser();
		deck = player.getDeck();
		// Count the number of cards in each player's hand
		for (Card card : deck) {
			if(card != null) {
				solns[i]++;
			}
		}
		// Each player should have 3 cards
		for(i = 0; i < 6; i++) {
			assertEquals(3, solns[i]);
		}
		assertNotEquals(board.getRoomSoln(), null);
		assertNotEquals(board.getWeaponSoln(), null);
		assertNotEquals(board.getPlayerSoln(), null);
	}
}