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
		if(correctWeapon.equals(wrongWeapon)) {
			wrongWeapon = new Card("The Skywalker Lightsaber", CardType.WEAPON);
		}
		assertFalse(board.checkAccusation(correctRoom, wrongWeapon, correctPerson));
	}

	@Test
	public void testWrongRoomAccusation() {
		// Check if a solution with the wrong person is correctly identified as incorrect
		Card wrongRoom = new Card("Atlantica", CardType.ROOM);
		if(correctRoom.equals(wrongRoom)) {
			wrongRoom = new Card("Arendelle", CardType.ROOM);
		}
		assertFalse(board.checkAccusation(wrongRoom, correctWeapon, correctPerson));
	}

	public void testCorrectSuggestion() {
		// Check if the correct solution is correctly identified as correct
		assertNull(board.handleSuggestion(board.getUser(), correctRoom, correctWeapon, correctPerson));
	}

	@Test
	public void testWrongSuggestion() {
		Set<ComputerPlayer> players = board.getPlayers();
		HumanPlayer user = board.getUser();
		for(Player player: players) {
			Set<Card> deck = player.getCards();
			for (Card card: deck) {
				if (card.getCardType() == CardType.PERSON) {
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
	
	@Test
	public void testSuggestionTwoDisprove() {
		HumanPlayer user = board.getUser();
		Set<ComputerPlayer> players = board.getPlayers();
		Set<Card> deck1 = user.getCards();
		Set<Card> deck2 = user.getCards();
		Card roomSuggestion = correctRoom;
		Card weaponSuggestion = correctWeapon;
		Card personSuggestion = correctPerson;
		for(Player player1: players) {
			deck1 = player1.getCards();
			for(Player player2: players) {
				deck2 = player2.getCards();
				if(player1 == player2) {
					break;
				}
				for (Card card1: deck1) {
					for (Card card2: deck2) {
						roomSuggestion = correctRoom;
						weaponSuggestion = correctWeapon;
						personSuggestion = correctPerson;
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
						
						System.out.println((board.handleSuggestion(user, roomSuggestion, weaponSuggestion, personSuggestion)).getCardName());
						assertTrue(card2.equals(board.handleSuggestion(user, roomSuggestion, weaponSuggestion, personSuggestion)));
					}
				}
			}
		}
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
    public void testDisproveSuggestionOneMatchingCard() {
    	Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);
    	
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);
        
        humanPlayer.addCard(personCard);
        
        Card disprovedCard = humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard);
        
        assertTrue(disprovedCard.equals(personCard));
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
        humanPlayer.addCard(roomCard);
        humanPlayer.addCard(weaponCard);
        humanPlayer.addCard(personCard);

        // Repeat the test multiple times to ensure randomness
        boolean returnWrongCard = false;
        boolean returnRoom = false;
        boolean returnWeapon = false;
        boolean returnPerson = false;
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
    
    
    //handle suggestions tests
    @Test
    public void testSuggestionNoOneCanDisprove() {

        // Make a suggestion that no one can disprove
        Card roomCard = new Card("RoomA", CardType.ROOM);
        Card weaponCard = new Card("WeaponY", CardType.WEAPON);
        Card personCard = new Card("PersonZ", CardType.PERSON);
        Player humanPlayer = new HumanPlayer("Harry Potter", Color.BLUE, 0, 4);

        assertNull(humanPlayer.disproveSuggestion(roomCard, weaponCard, personCard));
    }   
}

