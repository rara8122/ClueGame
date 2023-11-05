/*
 *Class: This class is a test class that comprises of a set of tests that checks the game's card processing. It tests new methods checkAccusation, disproveSuggestion, and handleSuggestions. They tests different scenarios for each method.   
 *Authors: Melanie Perez, Rachel Davy
 *Date: 11/03/2023
 *Collaborators: none
 *Sources: none
 */
package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

public class GameSolutionTest {

	private Board board;
	private Card correctRoom;
	private Card correctWeapon;
	private Card correctPerson;
	public static final int RUN_TIMES = 20;

	//runs before each test; initializes the board and other important things
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		correctRoom = board.getRoomSoln();
		correctWeapon = board.getWeaponSoln();
		correctPerson = board.getPlayerSoln();
	}
	
    // Check if the correct accusation is correctly identified as correct
	@Test
	public void testCorrectAccusation() {
		
		assertTrue(board.checkAccusation(correctRoom, correctWeapon, correctPerson));
	}
	
	// Check if an accusation with the wrong person is correctly identified as incorrect
	@Test
	public void testWrongPersonAccusation() {
				Card wrongPerson = new Card("Harry Potter", CardType.PERSON);
		if(correctPerson.equals(wrongPerson)) {
			wrongPerson = new Card("Bruce Wayne (Batman)", CardType.PERSON);
		}
		assertFalse(board.checkAccusation(correctRoom, correctWeapon, wrongPerson));
	}
	
	// Check if an accusation with the wrong person is correctly identified as incorrect
	@Test
	public void testWrongWeaponAccusation() {

		Card wrongWeapon = new Card("The Elder Wand", CardType.WEAPON);
		if(correctWeapon.equals(wrongWeapon)) {
			wrongWeapon = new Card("The Skywalker Lightsaber", CardType.WEAPON);
		}
		assertFalse(board.checkAccusation(correctRoom, wrongWeapon, correctPerson));
	}
	
	// Check if a suggestion with the wrong person is correctly identified as incorrect
	@Test
	public void testWrongRoomAccusation() {
		Card wrongRoom = new Card("Atlantica", CardType.ROOM);
		if(correctRoom.equals(wrongRoom)) {
			wrongRoom = new Card("Arendelle", CardType.ROOM);
		}
		assertFalse(board.checkAccusation(wrongRoom, correctWeapon, correctPerson));
	}
	
	// Check if the correct suggestion is correctly identified as correct
	public void testCorrectSuggestion() {
		assertNull(board.handleSuggestion(board.getUser(), correctRoom, correctWeapon, correctPerson));
	}
	
	
	@Test
	public void testWrongSuggestion() {
		Set<ComputerPlayer> players = board.getPlayers();
		HumanPlayer user = board.getUser();
		// Iterate through each player
		for(Player player: players) {
			Set<Card> deck = player.getCards();
			// Iterate through the cards in the player's deck
			for (Card card: deck) {
				if (card.getCardType() == CardType.PERSON) {
					// Ensure that the handleSuggestion method returns the expected card
					assertTrue(card.equals(board.handleSuggestion(user, correctRoom, correctWeapon, card)));
				}
				if (card.getCardType() == CardType.WEAPON) {
					assertTrue(card.equals(board.handleSuggestion(user, correctRoom, card, correctPerson)));
				}
				if (card.getCardType() == CardType.ROOM) {
					assertTrue(card.equals(board.handleSuggestion(user, card, correctWeapon, correctPerson)));
				}
			}
		}
	}
	
	//test to ensure suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
	@Test
	public void testSuggestionWeDisprove() {
		HumanPlayer user = board.getUser();
		Set<ComputerPlayer> players = board.getPlayers();
		Set<Card> deck = user.getCards();
		for (Card card: deck) {
			if (card.getCardType() == CardType.PERSON) {
				assertNull((board.handleSuggestion(user, correctRoom, correctWeapon, card)));
			}
			if (card.getCardType() == CardType.WEAPON) {
				assertNull((board.handleSuggestion(user, correctRoom, card, correctPerson)));
			}
			if (card.getCardType() == CardType.ROOM) {
				assertNull((board.handleSuggestion(user, card, correctWeapon, correctPerson)));
			}
		}
		for(Player player: players) {
			deck = player.getCards();
			for (Card card: deck) {
				if (card.getCardType() == CardType.PERSON) {
					assertNull((board.handleSuggestion(player, correctRoom, correctWeapon, card)));
				}
				if (card.getCardType() == CardType.WEAPON) {
					assertNull((board.handleSuggestion(player, correctRoom, card, correctPerson)));
				}
				if (card.getCardType() == CardType.ROOM) {
					assertNull((board.handleSuggestion(player, card, correctWeapon, correctPerson)));
				}
			}
		}
	}
	//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
	@Test
	public void testSuggestionTwoDisprove() {
		HumanPlayer user = board.getUser();
		Set<ComputerPlayer> players = board.getPlayers();
		Set<Card> deck1 = user.getCards();
		Set<Card> deck2 = user.getCards();
		Card roomSuggestion = correctRoom;
		Card weaponSuggestion = correctWeapon;
		Card personSuggestion = correctPerson;
		// Iterate through the first set of players' cards
		for(Player player1: players) {
			deck1 = player1.getCards();
			// Iterate through the second set of players' cards
			for(Player player2: players) {
				deck2 = player2.getCards();
				if(player1 == player2) {
					break;
				}
				// Iterate through the cards of the first player
				for (Card card1: deck1) {
					// Iterate through the cards of the second player
					for (Card card2: deck2) {
						roomSuggestion = correctRoom;
						weaponSuggestion = correctWeapon;
						personSuggestion = correctPerson;
						// Update the suggestion based on the card type
						if (card1.getCardType() == CardType.PERSON) {
							personSuggestion = card1;
						}
						if (card1.getCardType() == CardType.WEAPON) {
							weaponSuggestion = card1;
						}
						if (card1.getCardType() == CardType.ROOM) {
							roomSuggestion = card1;
						}
						if (card2.getCardType() == CardType.PERSON) {
							personSuggestion = card2;
						}
						if (card2.getCardType() == CardType.WEAPON) {
							weaponSuggestion = card2;
						}
						if (card2.getCardType() == CardType.ROOM) {
							roomSuggestion = card2;
						}
						System.out.println();
						System.out.println(player1.getName());
						System.out.println(card1.getCardName());
						System.out.println(player2.getName());
						System.out.println(card2.getCardName());
						
						System.out.println();
						System.out.println(roomSuggestion.getCardName());
						System.out.println(weaponSuggestion.getCardName());
						System.out.println(personSuggestion.getCardName());
						// Print the result of the handleSuggestion method
						System.out.println((board.handleSuggestion(user, roomSuggestion, weaponSuggestion, personSuggestion)).getCardName());
						assertTrue(card2.equals(board.handleSuggestion(user, roomSuggestion, weaponSuggestion, personSuggestion)));
					}
				}
			}
		}
	}

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
    
   
    
    //Disprove a suggestion with one matching card
    @Test
    public void testDisproveSuggestionOneMatchingCard() {
    	Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
    	
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);
        
        humanPlayer.addCard(personCard);
        
        Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);
        
        assertTrue(disprovedCard.equals(personCard));
    }
    
    //Test to check if players has >1 matching card, returned card should be chosen randomly
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
        humanPlayer.addCard(roomCard);
        humanPlayer.addCard(weaponCard);
        humanPlayer.addCard(personCard);

        // Repeat the test multiple times to ensure randomness
        boolean returnWrongCard = false;
        boolean returnRoom = false;
        boolean returnWeapon = false;
        boolean returnPerson = false;
        // Repeat the test multiple times to ensure randomness
        for (int i = 0; i < RUN_TIMES; i++) {
            Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);
            assertTrue(disprovedCard.equals(roomCard) || disprovedCard.equals(weaponCard) || disprovedCard.equals(personCard));
            if(disprovedCard.equals(roomCard)) {
            	returnRoom = true;
            }
            if(disprovedCard.equals(weaponCard)) {
            	returnWeapon = true;
            }
            if(disprovedCard.equals(personCard)) {
            	returnPerson = true;
            }
            // Check if the returned card is one of the matching cards
            if (disprovedCard.equals(matchingCard1) || disprovedCard.equals(matchingCard2) || disprovedCard.equals(matchingCard3)) {
                returnWrongCard = true;
                break;
            }
        }
        // Verify that at least one of the matching cards was chosen randomly
        assertFalse(returnWrongCard);
        assertTrue(returnRoom);
        assertTrue(returnWeapon);
        assertTrue(returnPerson);
    }
    
    
    //checks that suggestion no one can disprove returns null
    @Test
    public void testSuggestionNoOneCanDisprove() {
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);
        Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
        assertNull(humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard));
    }   
}