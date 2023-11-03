package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

import static org.junit.jupiter.api.Assertions.*;

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
}

