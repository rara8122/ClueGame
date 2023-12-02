/*
 *Class: This is the board class which contains our board. This is essentially the same as TestBoard class, except it was modified for our game code and test files. It implements a singleton pattern. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<Card> deck;
	private HumanPlayer user;
	private ArrayList<ComputerPlayer> computers;
	private Card room;
	private Card weapon;
	private Card player;
	private int currentPlayer;
	private boolean playerFinished;
	private int roll;
	private boolean displayTargets;
	private SuggestionDialog suggestion;
	private AccusationDialogue accuse;
	private String lastGuess;
	private Color guessColor;
	private String lastResult;
	private Color resultColor;
	private ClueGame frame;
	private GameControlPanel control;
	private ClueCardsPanel cards;
	private int wins;
	private int compWins;
	private int losses;
	private boolean go;
	
	public static final int DIE_SIDES = 6;
	
	/*  
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	
	/*
	 * constructor is private to ensure only one can be created
	 */
	private Board() {
		super() ;
		
	}
	
	//init Methods Below
	/*
	 *  this method returns the only Board
	 */
	public static Board getInstance() {
		return theInstance;
	}
	
	public void reset() {
		targets.clear();
		visited.clear();
		
		user.reset();
		for (ComputerPlayer computer : computers) {
			computer.reset();
		}
		deal();
		setPlayerLocation(); 
		
		lastGuess = "";
		guessColor = null;
		lastResult = "";
		resultColor = null;
		
		currentPlayer = computers.size() - 1; // so next player is the user (when we call nextPlayer)
		playerFinished = true; //so nextPlayer will run instead of throwing an error
	}
	
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize(){
		
		//Read in files/initialize sets
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();

		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		calcAdjacencies();
		deal();
		setPlayerLocation(); 
		
		lastGuess = "";
		guessColor = null;
		lastResult = "";
		resultColor = null;
		
		currentPlayer = computers.size() - 1; // so next player is the user (when we call nextPlayer)
		playerFinished = true; //so nextPlayer will run instead of throwing an error

		addMouseListener(new BoardListener()); //listens for mouse clicks
		
		cards = new ClueCardsPanel();  // create the cards panel
		control = new GameControlPanel();  // create the control panel
		frame = new ClueGame(this, cards, control);  // create the frame 
		wins = 0;
		compWins = 0;
		losses = 0;
		go = true;
		
		cards.updateDeckCards(user.getDeck(), user.getColor());
		cards.updateSeenCards(user.getSeen());
		control.setTurn(user);
		control.setGuess( "", null);
		control.setGuessResult( "", null);
		control.setRoll(roll);
		
	}
		
	/*
	 * method to load setup config
	 */
	public void loadSetupConfig () throws BadConfigFormatException, IOException {
		roomMap = new HashMap <Character, Room> ();
		deck = new HashSet<Card>();
		computers = new ArrayList<ComputerPlayer>();
		user = null;
		FileReader setupFile;
		try {
			setupFile = new FileReader("data/" + setupConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BadConfigFormatException("The setup file cannot be found.");
		}
		Scanner setupScanner = new Scanner(setupFile);
		//read and process the setup file (.txt)
		String info;
		String name;
		Character roomChar;
		Room newRoom;
		Card newCard;
		String[] playerInfo;
		Color newColor;
		String[] colorInts;
		String[] playerLocation;
		ComputerPlayer newPlayer;

		//this loops reds the next line from setUpScanner and stores it in the info variable 
		while(setupScanner.hasNextLine()) {
			info = setupScanner.next();
			info.trim();
			//ereads the next token from setupscanner and stores it in the info variable
			if (info.equals("Room,")) {
				name = setupScanner.next();
				name = name.replace(",", "");
				info = setupScanner.next();
				info = info.trim();
				//continues as long as the length of info is not equal to one
				while(info.length() != 1) {
					name = name + " " + info;
					name = name.replace(",", "");
					info = setupScanner.next();
					info = info.trim();
				}
				roomChar = info.charAt(0);
				newRoom = new Room(name);
				newCard = new Card(name, CardType.ROOM);
				deck.add(newCard); //add room to deck
				roomMap.put(roomChar, newRoom);
				//checks if there is another line available and reads it 
				if (setupScanner.hasNextLine()) {
					info = setupScanner.nextLine();
				}
			} else if(info.equals("Space,")) {
				name = setupScanner.next();
				name = name.replace(",", "");
				info = setupScanner.next();
				info = info.trim();
				//continues as long as the length of info is not equal to one
				while(info.length() != 1) {
					name = name + " " + info;
					name = name.replace(",", "");
					info = setupScanner.next();
					info = info.trim();
				}
				roomChar = info.charAt(0);
				newRoom = new Room(name); //no card because spaces do not get cards
				roomMap.put(roomChar, newRoom);
				//checks if there is another line available and reads it 
				if (setupScanner.hasNextLine()) {
					info = setupScanner.nextLine();
				}
			} else if (info.equals("Weapon,")) {
				name = setupScanner.nextLine();
				name = name.replace(",", "");
				name = name.trim();
				newCard = new Card(name, CardType.WEAPON);
				deck.add(newCard); //make a new card and add to deck + weapons
				//checks if there is another line available and reads it 
			} else if (info.equals("Player,")) {
				info = setupScanner.nextLine();
				playerInfo = info.split(",");
				if (playerInfo.length != 3) { //playerInfo needs name, color, and location
					setupScanner.close();
					setupFile.close();
					throw new BadConfigFormatException("Improperly formatted player information: not enough player information");
				}
				playerInfo[0] = playerInfo[0].trim();
				playerInfo[1] = playerInfo[1].trim();
				playerInfo[2] = playerInfo[2].trim();
				newCard = new Card(playerInfo[0], CardType.PERSON);//makes a player card
				deck.add(newCard);// deck is the overall deck, people holds all people cards.
				colorInts = playerInfo[1].split(" "); //colorInts holds the R, G, and B values (in that order)
				playerLocation = playerInfo[2].split(" "); //playerLocation holds the row and column values (in that order)
				if(colorInts.length != 3 || playerLocation.length != 2) { //if it does not hold enough info, throw an error
					setupScanner.close();
					setupFile.close();
					throw new BadConfigFormatException("Improperly formatted player information: not enough location or color information");
				}
				try {
					newColor = new Color(Integer.parseInt(colorInts[0]), Integer.parseInt(colorInts[1]), Integer.parseInt(colorInts[2]));
					if(user == null) { //sets the first player to be the user
						user = new HumanPlayer(playerInfo[0], newColor, Integer.parseInt(playerLocation[0]), Integer.parseInt(playerLocation[1]));
					} else {//all other players are computers
						newPlayer = new ComputerPlayer(playerInfo[0], newColor, Integer.parseInt(playerLocation[0]), Integer.parseInt(playerLocation[1]));
						computers.add(newPlayer);
					}
				} catch (NumberFormatException e) { //if parseInt throws an exception, we throw one ourselves 
					setupScanner.close();
					setupFile.close();
					throw new BadConfigFormatException("Improperly formatted player information: player location or color not integers");
				}
			} else {
				//checks if there is another line available and reads it 
				if (!info.equals("//")) {
					setupScanner.close();
					setupFile.close(); //close scanner and file if the code never finishes
					throw new BadConfigFormatException("The setup file is improperly formatted.");
				} else {
					info = setupScanner.nextLine();
				}
			}
		}
		setupScanner.close();
		setupFile.close();
	}
	
	/*
	 * loads layout config
	 */
	public void loadLayoutConfig () throws BadConfigFormatException, IOException {
		numRows = 0;
		numColumns = 0;
		
		FileReader layoutFile;
		
		try {
			layoutFile = new FileReader("data/" + layoutConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BadConfigFormatException("The layout file cannot be found.");
		}
		Scanner layoutScanner = new Scanner(layoutFile);
		String file = layoutScanner.nextLine();
		String[] lines;
		String line;
		//reads from layoutScanner
		while (layoutScanner.hasNextLine()) {
			line = layoutScanner.nextLine();
			line = line.replace(" ", "");
			file = file + " " + line;
		}
		
		lines = file.split(" "); 
		numRows = lines.length;
		String[][] strings = new String[numRows][];
		strings[0] = lines[0].split(",");
		numColumns = strings[0].length;
		//validates the format of the line. if format is incorrect, exception is thrown
		for (int l = 1; l < numRows; l++) {
			strings[l] = lines[l].split(",");
			if(strings[l].length != numColumns) {
				//THROW BadConfigFormatException
				layoutScanner.close();
				layoutFile.close(); //close scanner and file of code never finishes
				throw new BadConfigFormatException("The layout file has improper formatting.");
			}
		}
		layoutScanner.close();
		layoutFile.close();
		grid = new BoardCell[numRows][numColumns];
		String currentStr;
		BoardCell currentCell;
		Room correspondingRoom;
		//nested for loop that loops through grid cells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				currentStr = strings[i][j];
				correspondingRoom = roomMap.get(currentStr.charAt(0));
				//check if corresponding room is null
				if (correspondingRoom == null) {
					throw new BadConfigFormatException("Bad room: the layout file referes to a room that is not in the setup. ");
				}
				grid[i][j] = new BoardCell(i, j, currentStr.charAt(0), correspondingRoom.getName());
				currentCell = grid[i][j];
				//checks if the length of the current string is 2 
				if(currentStr.length() == 2) {
					//checks is the first chracter of the string is a W
					if(currentStr.charAt(0) == 'W') {
						//all the if statements check whether the next character in the string is v,^, <,>,#,* respectively
						switch (currentStr.charAt(1))
						{
						case 'v':
							currentCell.setDoorDirection(DoorDirection.DOWN);
							break;
						case '^':
							currentCell.setDoorDirection(DoorDirection.UP);
							break;
						case '<':
							currentCell.setDoorDirection(DoorDirection.LEFT);
							break;
						case '>':
							currentCell.setDoorDirection(DoorDirection.RIGHT);
							break;
						}

						//checks other conditions
					} else { 
						switch (currentStr.charAt(1))
						{
						case '#':
							roomMap.get(currentStr.charAt(0)).setLabelCell(currentCell);
							currentCell.setRoomLabel(true);
							break;
						case '*':
							roomMap.get(currentStr.charAt(0)).setCenterCell(currentCell);
							currentCell.setRoomCenter(true);
							break;
						default:
							currentCell.setSecretPassage(currentStr.charAt(1));
							break;
						}
					}
				}
			}
		}
	}
	
	/*
	 * sets player locations
	 */
	private void setPlayerLocation() {
		Collection<Room> rooms = roomMap.values();
		for (Room room : rooms) {
			room.emptyPlayers(); //empty players so we start from scratch
		}
		roomMap.get(grid[user.getRow()][user.getColumn()].getInitial()).addPlayer(user); //add the user to the room (separate from the other players)
		if(grid[user.getRow()][user.getColumn()].getInitial() == 'W') {//if the target is a walkway this will be true
			grid[user.getRow()][user.getColumn()].setOccupied(true);
		}
		for(Player player: computers) {
			roomMap.get(grid[player.getRow()][player.getColumn()].getInitial()).addPlayer(player); //for every player, add to the associated room
			if(grid[player.getRow()][player.getColumn()].getInitial() == 'W') {//if the target is a walkway this will be true
				grid[player.getRow()][player.getColumn()].setOccupied(true);
			}
		}
	}
	
	/*
	 * Loop through each player to deal cards
	 */
	private void deal() {
		room = null;
		weapon = null;
		player = null;
		Card newCard;
		Random choice = new Random();
		Object[] deckArray = deck.toArray();
		int cardsRemaining = deckArray.length;
		int card;
		for (Player thisPlayer : computers) {
			while(!thisPlayer.deckFull()) {//deckFull returns true if the deck has 3 cards
				card = choice.nextInt(deckArray.length);//pick a random card
				if(deckArray[card] != null) {
					if(room == null && ((Card) deckArray[card]).getCardType() == CardType.ROOM) { 
						room = new Card((Card) deckArray[card]);//if its the first room picked, its the solution
						deckArray[card] = null;// set the array index to be null so we don't give 2 people the same card (or someone a solution card)
						cardsRemaining--;
					}else if(weapon == null && ((Card) deckArray[card]).getCardType() == CardType.WEAPON) {
						weapon = new Card((Card) deckArray[card]); //does the same for weapons as for room 
						deckArray[card] = null;
						cardsRemaining--;
					} else if(player == null && ((Card) deckArray[card]).getCardType() == CardType.PERSON) {
						player = new Card((Card) deckArray[card]);//does the same for player as for room 
						deckArray[card] = null;
						cardsRemaining--;
					}
				}
				if(deckArray[card] != null){ //if the card is still not picked, add it to this player's deck
					newCard = new Card((Card) deckArray[card]);
					thisPlayer.addCard(newCard);
					deckArray[card] = null;
					cardsRemaining--;
				}
				if(cardsRemaining == 3) {
					break;
				}
			}
		}
		while(cardsRemaining > 0) {
			if(cardsRemaining < 5) {
				for(int i = 0; i < deckArray.length; i++) {
					if(deckArray[i] != null){ //if the card is still not picked, add it to this player's deck
						newCard = new Card((Card) deckArray[i]);
						user.addCard(newCard);
						deckArray[i] = null;
						cardsRemaining--;
					}
				}
			} else {
				for (Player thisPlayer : computers) {
					for(int i = 0; i < deckArray.length; i++) {
						if(cardsRemaining == 4) {
							break;
						}
						if(deckArray[i] != null){ //if the card is still not picked, add it to this player's deck
							newCard = new Card((Card) deckArray[i]);
							thisPlayer.addCard(newCard);
							deckArray[i] = null;
							cardsRemaining--;
						}
					}
				}
			}
		}
	}
	
	/*
	 * method to build adjacency list for each cell 
	 */
	private void calcAdjacencies() {

		BoardCell currentCell;
		//nested for loops that iterate through each cell row and column respectively
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				currentCell = grid[i][j];
				//calculates adjacency relationships between cells when the current cell is a walkway. 
				if(currentCell.isWalkway()) {
					if(j > 0) {
						if(grid[i][j - 1].isWalkway()) {
							grid[i][j - 1].addAdj(currentCell);
							currentCell.addAdj(grid[i][j - 1]);
						}
					}
					if(j < numColumns - 1) {
						if(grid[i][j + 1].isWalkway()) {
							grid[i][j + 1].addAdj(currentCell);
							currentCell.addAdj(grid[i][j + 1]);
						}
					}
					if(i < numRows - 1) {
						if(grid[i + 1][j].isWalkway()) {
							grid[i + 1][j].addAdj(currentCell);
							currentCell.addAdj(grid[i + 1][j]);
						}
					}
					if(i > 0) {
						if(grid[i - 1][j].isWalkway()) {
							grid[i - 1][j].addAdj(currentCell);
							currentCell.addAdj(grid[i - 1][j]);
						}
					}
				}

				//calculates adjacency relationships between cells when the current cell is a doorway.
				if(currentCell.isDoorway()) {
					switch(currentCell.getDoorDirection()){
					case NONE:
						break;
					case DOWN:
						currentCell.addAdj((roomMap.get(grid[i + 1][j].getInitial())).getCenterCell());
						((roomMap.get(grid[i + 1][j].getInitial())).getCenterCell()).addAdj(currentCell);;
						break;
					case UP:
						currentCell.addAdj((roomMap.get(grid[i - 1][j].getInitial())).getCenterCell());
						((roomMap.get(grid[i - 1][j].getInitial())).getCenterCell()).addAdj(currentCell);;
						break;
					case LEFT:
						currentCell.addAdj((roomMap.get(grid[i][j - 1].getInitial())).getCenterCell());
						((roomMap.get(grid[i][j - 1].getInitial())).getCenterCell()).addAdj(currentCell);;
						break;
					case RIGHT:
						currentCell.addAdj(roomMap.get(grid[i][j + 1].getInitial()).getCenterCell());
						((roomMap.get(grid[i][j + 1].getInitial())).getCenterCell()).addAdj(currentCell);;
						break;
					}
				}

				char currentCellPassage = currentCell.getSecretPassage();
				//calculates adjacency relationships between cells when the current cell is a secret passage.
				if (currentCellPassage != ' ') {
					Room currentRoom = roomMap.get(currentCell.getInitial());
					Room passageRoom = roomMap.get(currentCellPassage);

					BoardCell currentRoomCenter = currentRoom.getCenterCell();
					BoardCell passageRoomCenter = passageRoom.getCenterCell();

					currentRoomCenter.addAdj(passageRoomCenter);
					passageRoomCenter.addAdj(currentRoomCenter);
				}
			}
		}
	}

	//Target Methods Below:
	/*
	 * recursive function to find reachable target cells 
	 */
	private void findTargets(BoardCell currentCell, int steps) {
		visited.add(currentCell);
		Set<BoardCell> adjacentCells = currentCell.getAdjList();
		//iterates through adjacent cells 
		for (BoardCell cell : adjacentCells) {
			//checks if cell has been visitied, or is occupied, and that it is the room center
			if (!(visited.contains(cell) || (cell.isOccupied() && !(cell.isRoomCenter())))) {
				if (steps == 1 || cell.isRoomCenter()) {
					targets.add(cell);	
				} else if (cell.isWalkway() || cell.isDoorway()){
					findTargets(cell, steps - 1);
				}
			}
		}
		visited.remove(currentCell);
	} 
	
	/*
	 * method to calculate targets 
	 */
	public void calcTargets(BoardCell cell, int i) {
		visited.clear(); 
		targets.clear();
		findTargets(cell, i);
	}
	
	// Other: 
	public boolean checkAccusation(Card roomAccusation, Card weaponAccusation, Card personAccusation) {
	    // Compare the accusation with the correct solution
	    // Check if each part of the accusation matches the correct solution
	    // Return true if all three parts of the accusation are correct
	    return (roomAccusation.equals(room) && weaponAccusation.equals(weapon) && personAccusation.equals(player));
	}
	
	/*
	 * method that handles the player's suggestion
	 */
	public SeenCard handleSuggestion(Player suggestingPlayer, Card roomCard, Card weaponCard, Card personCard) {
		Card returnCard = user.disproveSuggestion(roomCard, weaponCard, personCard);
		if(returnCard != null && suggestingPlayer != user) {
			return new SeenCard(returnCard, user.getColor());
		}
		for (Player person : computers) {
			returnCard = person.disproveSuggestion(roomCard, weaponCard, personCard);
			if(returnCard != null && suggestingPlayer != person) {
				return new SeenCard(returnCard, person.getColor());
			}
		}
		return null;
	}
	
	/*
	 * method to roll the dice 
	 */
	private int rollDice() {
		Random choice = new Random();
		return choice.nextInt(DIE_SIDES - 1) + 1; //roll a DIE_SIDES sided die from 0 to DIE_SIDES - 1 (and then add 1 so its 1 to DIE_SIDES)
	}
	
	//Draw Methods Below: 
	/*
	 * Handles the click event on the board by a player.
	 * Moves the user's game piece to the clicked target if it's a valid target.
	 * Throws a MisClick exception if the clicked cell is not a target.
	*/
	private void setInfo() {
		control.setGuess(lastGuess, guessColor);
		control.setGuessResult(lastResult, resultColor);
		cards.updateSeenCards(user.getSeen());
	}
	
	private void done() {
		if(go == true) {
			reset();
			try {
				nextPlayer();
			} catch (MisClick e) {
				e.printStackTrace();
			}
			play();
			control.setRoll(roll);
			
			setInfo();
			cards.updateDeckCards(user.getDeck(), user.getColor());
		} else {
			frame.dispose();
		}
	}
	
	public void boardClick(int row, int column) throws MisClick{
		if(currentPlayer != computers.size()) {
			return; //if it isn't the player, we clicked wrong
		}
		BoardCell target = roomMap.get(grid[row][column].getInitial()).getCenterCell();//set to be center of associated room (so we can click anywhere in a room)
		if(target == null) {//if the target is a walkway this will be true
			target = grid[row][column];
			target.setOccupied(true);
		}
		if(!targets.contains(target)) {
			throw new MisClick("Player clicked on a BoardCell that is not a target"); //if we didn't click a target, we clicked wrong
		}
		roomMap.get(grid[user.getRow()][user.getColumn()].getInitial()).removePlayer(user);//remove player from current room and add to new room
		roomMap.get(target.getInitial()).addPlayer(user);
		grid[user.getRow()][user.getColumn()].setOccupied(false);
		user.setRow(target.getRow());//set new location
		user.setColumn(target.getColumn());
		if(target.isRoomCenter()) {
			if(suggestion == null) {
				suggestion = new SuggestionDialog(target.getRoomName());
				suggestion.setVisible(true);
			}
		} else {
			lastGuess = "";
			guessColor = null;
			lastResult = "";
			resultColor = null;
			playerFinished = true;//end the turn and stop displaying target options
		}
		displayTargets = false;
		repaint();//repaint so the targets stop displaying
	}
	
	public void suggestionCancel() {
		suggestion.setVisible(false);
		suggestion = null;
		playerFinished = true;
	}
	
	public void playerSuggestion() {
		suggestion.setVisible(false);
		if(suggestion.getWeapon() == null || suggestion.getPerson() == null) {
			return;
		}
		Card userRoom = null;
		for(Card card : deck) {
			if(card.getCardName() == getCell(user.getRow(), user.getColumn()).getRoomName()) {
				userRoom = card;
			}
		}
		SeenCard suggestedCard = handleSuggestion(user, userRoom, suggestion.getWeapon(), suggestion.getPerson());
		
		lastGuess = userRoom + ", " + suggestion.getWeapon() + ", " + suggestion.getPerson();
		guessColor = user.getColor();
		
		if(suggestedCard != null) {
			user.updateSeen(suggestedCard);
			lastResult = suggestedCard.getCardName();
			resultColor = suggestedCard.getColor();
		} else {
			lastResult = "No new clue";
			resultColor = null;
		}
		setInfo();
		
		Player suggestedPlayer = getSuggestedPlayer(suggestion.getPerson());
		if(suggestedPlayer != null) {
			roomMap.get(grid[suggestedPlayer.getRow()][suggestedPlayer.getColumn()].getInitial()).removePlayer(suggestedPlayer);
			suggestedPlayer.setRow(user.getRow());
			suggestedPlayer.setColumn(user.getColumn());
			roomMap.get(grid[user.getRow()][user.getColumn()].getInitial()).addPlayer(suggestedPlayer);
		}
		
		repaint();
		playerFinished = true;
		suggestion = null;
	}
	
	public void accusationCancel() {
		accuse.setVisible(false);
	}
	
	public void playerAccusation() {
		boolean isCorrect = checkAccusation(accuse.getRoom(), accuse.getWeapon(), accuse.getPerson());
		if(isCorrect) {
			wins++;
			JOptionPane.showMessageDialog(frame, "You Win!", "You win", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame, "Sorry, not correct! You lose!", "You Lose", JOptionPane.INFORMATION_MESSAGE);
	        losses++;
		}
		accuse.setVisible(false);
		Continue cont = new Continue();
	}
	
	private Player getSuggestedPlayer(Card playerCard) {
		if((user.getName()).equals(playerCard.getCardName())){
			return user;
		}
		for (Player computer : computers) {
			if((computer.getName()).equals(playerCard.getCardName())){
				return computer;
			}
		}
		return null;
	}
	
	/*
	 * Moves the game to the next player's turn.
	 * Throws a MisClick exception if a player tries to switch turns before finishing their turn.
	 */
	public void nextPlayer() throws MisClick{
		
		 // Check if it's the last player's turn and their turn is finished
		if(currentPlayer == computers.size()) {
			if(playerFinished) {
				currentPlayer = 0; // Move to the first player if the last player finished their turn
			} else {
				throw new MisClick("Player turn is not finished");
			}
		} else {
			currentPlayer++;
		}
		roll = rollDice(); //roll the dice for current player's turn.
		 // Calculate targets for the next player based on their current location and roll
		if(currentPlayer == computers.size()) {
			calcTargets(grid[user.getRow()][user.getColumn()], roll);
		} else {
			ComputerPlayer player = computers.get(currentPlayer);
			calcTargets(grid[player.getRow()][player.getColumn()], roll);
		}
	}
	
	/**
	 * Controls the gameplay logic, either showing targets for the human player or executing the computer player's turn.
	 * Repaints the board after each turn.
	 */
	public void play() {
		
		if(currentPlayer == computers.size()) {
			displayTargets = true;//make sure the player is not done and we display movement options
			playerFinished = false;
		} else {
			ComputerPlayer player = computers.get(currentPlayer);
			BoardCell location = grid[player.getRow()][player.getColumn()];
	
			if (player.accuse()) {  
				if(checkAccusation(player.getRoomSuggestion(), player.getWeaponSuggestion(), player.getPersonSuggestion())) {
					JOptionPane.showMessageDialog(frame, "The computer just won, answer is " + player + ", " 
							+ room + ", " + weapon,
							"Computer Won", JOptionPane.INFORMATION_MESSAGE);
					compWins++;
					Continue cont = new Continue();
					return;
				}
	        }
	        	// If not making an accusation, proceed with normal turn
	            location = player.selectTarget(targets);

	            if (location.getInitial() == 'W') {
	                location.setOccupied(true);
	            }
	            
			roomMap.get(grid[player.getRow()][player.getColumn()].getInitial()).removePlayer(player);
			roomMap.get(location.getInitial()).addPlayer(player);
			grid[player.getRow()][player.getColumn()].setOccupied(false);
			player.setRow(location.getRow());
			player.setColumn(location.getColumn());
			
			// Check if the player entered a room
			if (location.isRoomCenter()) {
				// Create a suggestion
				Room currentRoom = roomMap.get(location.getInitial());
				player.createSolution(deck, currentRoom.getName());
				Card roomCard = player.getRoomSuggestion();
				Card weaponCard = player.getWeaponSuggestion();
				Card personCard = player.getPersonSuggestion();
				// Move the suggested person to the room
			   
				// Call the method to handle disproving suggestions
				lastGuess = roomCard + ", " + weaponCard + ", " + personCard;
				guessColor = player.getColor();
				SeenCard disprovedCard = handleSuggestion(player, roomCard, weaponCard, personCard);
				// Set a flag if no one can disprove the suggestion
				if (disprovedCard == null) {
					// Set a flag indicating that no one could disprove the suggestion
					player.solutionTrue();
					lastResult = "No new clue";
					resultColor = null;
				} else {
					player.updateSeen(disprovedCard);
					lastResult = "Suggestion disproven!";
					resultColor = disprovedCard.getColor();
				}
				Player suggestedPlayer = getSuggestedPlayer(personCard);
				
				if(suggestedPlayer != null) {
					roomMap.get(grid[suggestedPlayer.getRow()][suggestedPlayer.getColumn()].getInitial()).removePlayer(suggestedPlayer);
					suggestedPlayer.setRow(player.getRow());
					suggestedPlayer.setColumn(player.getColumn());
					currentRoom.addPlayer(suggestedPlayer);
				}
			} else {
				lastGuess = "";
				guessColor = null;
				lastResult = "";
				resultColor = null;
			}
		}
		setInfo();
		super.repaint(); //Redraw the board after the turn	
	}
	
	/*
	 * This method checks if the current player is a human player. 
	 * It then processes the click to determine the column and row clicked
	 * Eventually it calls a boardClick method	
	 */
	public void mouseClicked(MouseEvent event) {
	    if (getCurrentPlayer() instanceof HumanPlayer) {
	        int mouseX = event.getX();
	        int mouseY = event.getY();
	        int clickedColumn = mouseX / (getWidth() / numColumns);
	        int clickedRow = mouseY / (getHeight() / numRows);

	        try {
	            boardClick(clickedRow, clickedColumn); // Implement boardClick method
	        } catch (MisClick e) {
	            // Show error message for invalid click
	            String message = "That is not a valid target.";
	            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	    repaint(); // Refresh the board display
	}

	/*
	 * method to draw the board and players
	 */
	@Override
	public void paintComponent(Graphics newGraphic) {
		super.paintComponent(newGraphic);
		int width = getWidth()/numColumns;//the width of one cell
		int height = getHeight()/numRows;//the height of one cell
		int walkwayWidth = ((BoardCell.BORDER_SIZE - 2) * width)/BoardCell.BORDER_SIZE;//the inner width of the walkway
		int walkwayHeight = ((BoardCell.BORDER_SIZE - 2) * height)/BoardCell.BORDER_SIZE;//the inner height of the walkway
		BoardCell currentCell;
		Boolean isTarget;
		for (int i = 0; i < numRows; i++) {//draw every cell
			for(int j = 0; j < numColumns; j++) {
				isTarget = false;
				currentCell = grid[i][j];
				if(targets.contains(currentCell) && displayTargets) {
					isTarget = true;
				}
				if (targets.contains((roomMap.get(currentCell.getInitial()).getCenterCell())) && displayTargets) {
					isTarget = true;
				}
				currentCell.draw(width, height, newGraphic, isTarget, walkwayWidth, walkwayHeight);
			}
		}
		for (int i = 0; i < numRows; i++) {//draw every door
			for(int j = 0; j < numColumns; j++) {
				grid[i][j].drawDoor(width, height, newGraphic);
			}
		}
		Collection<Room> rooms = roomMap.values();
		for (Room room : rooms) {//draw every room
			room.drawLabel(width, height, newGraphic);
		}
		for (Room room : rooms) {//draw every player
			room.drawPlayers(width, height, walkwayWidth, walkwayHeight, newGraphic);
		}
	}
	
	/*
	 * Inner class implementing MouseListener to handle mouse events on the board.
	 */
	private class BoardListener implements MouseListener {
		// Empty definitions for unused event methods.
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {
			Point point = event.getPoint(); //get the point clicked
			int column = (point.x * numColumns)/getWidth();// Calculate the column and row based on the click position
			int row = (point.y * numRows)/getHeight();

			try {
				boardClick(row, column);
			} catch (MisClick e) {
				String message = "That is not a target";
		        JOptionPane.showMessageDialog(Board.getInstance(), message, "Error", JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
		}
	}
	
	private class Continue  extends JDialog {
		public Continue() {
			setTitle("Continue?");
			setSize(300, 200);
			setLayout(new GridLayout(0, 2));
			
			JLabel winsLabel = new JLabel("Wins: ");
			add(winsLabel);
			JLabel winsNum  = new JLabel(Integer.toString(wins));
			add(winsNum);
			
			JLabel lossesLabel = new JLabel("Losses");
			add(lossesLabel);
			JLabel lossesNum = new JLabel(Integer.toString(losses));
			add(lossesNum);
			
			JLabel comuterWins = new JLabel("Computer's Wins: ");
			add(comuterWins);
			JLabel comuterWinsNum = new JLabel(Integer.toString(compWins));
			add(comuterWinsNum);
			
			
			JButton submit = new JButton("Continue");
			submit.addActionListener(e -> {
				go = true;
				setVisible(false);
				done();
			});
			
			JButton cancel = new JButton("Quit");
			cancel.addActionListener(e -> {
				go = false;
				setVisible(false);
				done();		
			});
			
			add(submit);
			add(cancel);
			
			super.setVisible(true);

		}
	}
	/*
	 * Main method for testing
	 */
	public static void main(String[] args) {
		theInstance.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		theInstance.initialize();
		try {
			theInstance.nextPlayer();
		} catch (MisClick e) {
			e.printStackTrace();
		}
		theInstance.play();
		theInstance.control.setRoll(theInstance.getRoll());
       
	}
	
	/*
	 * all getters and setters below
	 */
	public boolean canAccuse(){
		return displayTargets;
	}
	
	public String getGuess() {
		return lastGuess;
	}
	
	public String getResult() {
		return lastResult;
	}
	
	public Card getRoomSoln() {
		return room;
	}
	
	public Card getWeaponSoln() {
		return weapon;
	}
	
	public Card getPlayerSoln() {
		return player;
	}
	
	public ArrayList<ComputerPlayer> getPlayers(){
		return computers;
	}
	
	public HumanPlayer getUser() {
		return user;
	}
	
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Set<Card> getDeck() {
		return deck;
	}
	
	//all setters here 
	public void setGrid(BoardCell[][] grid) {
		this.grid = grid;
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		layoutConfigFile = layoutFile;
		setupConfigFile = setupFile;
	}
	
	public void setAccuse(AccusationDialogue newAccuse) {
		accuse = newAccuse;
	}
	
	//all getters below
	public int getRoll() {
		return roll;
	} 
	
	public Player getCurrentPlayer() {
		if(currentPlayer == computers.size()) {
			return user;
		} else {
			return computers.get(currentPlayer);
		}
	}

	public Color getGuessColor() {
		return guessColor;
	}

	public Color getResultColor() {
		return resultColor;
	}

}
