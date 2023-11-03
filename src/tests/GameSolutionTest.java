package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

import static org.junit.jupiter.api.Assertions.*;

public class GameSolutionTest {

	private Board board;
	private Solution correctSolution;
	private Solution wrongPersonSolution;
	private Solution wrongWeaponSolution;
	private Solution wrongRoomSolution;

	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testCorrectAccusation() {
		correctSolution = new Solution(
				new Card("Atlantica", CardType.ROOM),
				new Card("Bruce Wayne (Batman)", CardType.PERSON),
				new Card("Lazarus Pit Water", CardType.WEAPON)
				);
		// Check if the correct solution is correctly identified as correct
		assertTrue(board.checkAccusation(correctSolution));
	}

	@Test
	public void testWrongPersonAccusation() {
		// Check if a solution with the wrong person is correctly identified as incorrect
		wrongPersonSolution = new Solution(
				new Card("Atlantica", CardType.ROOM),
				new Card("Harry Potter", CardType.PERSON),  // Wrong person
				new Card("Lazarus Pit Water", CardType.WEAPON));
		assertFalse(board.checkAccusation(wrongPersonSolution));
	}

	@Test
	public void testWrongWeaponAccusation() {
		wrongWeaponSolution = new Solution(
				new Card("Atlantica", CardType.ROOM),
				new Card("Bruce Wayne (Batman)", CardType.PERSON),
				new Card("The Elder Wand", CardType.WEAPON)  // Wrong weapon
				);
		// Check if a solution with the wrong weapon is correctly identified as incorrect
		assertFalse(board.checkAccusation(wrongWeaponSolution));
	}

	@Test
	public void testWrongRoomAccusation() {
		wrongRoomSolution = new Solution(
				new Card("Arendelle", CardType.ROOM),  // Wrong room
				new Card("Bruce Wayne (Batman)", CardType.PERSON),
				new Card("Lazarus Pit Water", CardType.WEAPON)
				);
		// Check if a solution with the wrong room is correctly identified as incorrect
		assertFalse(board.checkAccusation(wrongRoomSolution));
	}
}

