/*
 *Class: This is the board class which contains our board. This is essentially the same as TestBoard class, except it was modified for our game code and test files. It implements a singleton pattern. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<Card> deck;
	private Set<Card> rooms;
	private Set<Card> people;
	private Set<Card> weapons;
	private Set<Player> players;
	private HumanPlayer user;
	private Card room;
	private Card weapon;
	private Card player;
	
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
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
	}
	//loads setup config
	public void loadSetupConfig () throws BadConfigFormatException, IOException {
		roomMap = new HashMap <Character, Room> ();
		deck = new HashSet<Card>();
		rooms = new HashSet<Card>();
		people = new HashSet<Card>();
		weapons = new HashSet<Card>();
		players = new HashSet<Player>();
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
				rooms.add(newCard);
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
				newCard = new Card(name, CardType.WEAPON);
				deck.add(newCard); //make a new card and add to deck + weapons
				weapons.add(newCard);
				//checks if there is another line available and reads it 
				if (setupScanner.hasNextLine()) {
					info = setupScanner.nextLine();
				}
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
				people.add(newCard);
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
						players.add(user);
					} else {//all other players are computers
						newPlayer = new ComputerPlayer(playerInfo[0], newColor, Integer.parseInt(playerLocation[0]), Integer.parseInt(playerLocation[1]));
						players.add(newPlayer);
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
	//loads layout config
	public void loadLayoutConfig () throws BadConfigFormatException, IOException {
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
		//va;idates the format of the kine. if format is incorrect, exception is thrown
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
				grid[i][j] = new BoardCell(i, j, strings[i][j].charAt(0));
				currentStr = strings[i][j];
				currentCell = grid[i][j];
				correspondingRoom = roomMap.get(currentStr.charAt(0));
				//check if corresponding room is null
				if (correspondingRoom == null) {
					throw new BadConfigFormatException("Bad room: the layout file referes to a room that is not in the setup. ");
				}
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
	
	public void deal() {
		Card newCard;
		Random choice = new Random();
		Card[] deckArray = (Card[]) deck.toArray();
		int card;
		for (Player thisPlayer : players) {
			while(!thisPlayer.deckFull()) {
				card = choice.nextInt(deckArray.length);
				if(room == null && deckArray[card].getCardType() == CardType.ROOM && deckArray[card] != null) {
					room = new Card(deckArray[card]);
					deckArray[card] = null;
				}
				if(weapon == null && deckArray[card].getCardType() == CardType.WEAPON && deckArray[card] != null) {
					weapon = new Card(deckArray[card]);
					deckArray[card] = null;
				}
				if(player == null && deckArray[card].getCardType() == CardType.PERSON && deckArray[card] != null) {
					player = new Card(deckArray[card]);
					deckArray[card] = null;
				}
				if(deckArray[card] != null){
					newCard = new Card(deckArray[card]);
					thisPlayer.addCard(newCard);
					deckArray[card] = null;
				}
			}
		}
	}
	
	//method to build adjacency list for each cell 
	public void calcAdjacencies() {

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

	//recursive function to find reachable target cells 
	public void findTargets(BoardCell currentCell, int steps) {
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
	//method to calculate targets 
	public void calcTargets(BoardCell cell, int i) {
		visited.clear(); 
		targets.clear();
		findTargets(cell, i);
	}
	//all setters here 
	public void setGrid(BoardCell[][] grid) {
		this.grid = grid;
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		layoutConfigFile = layoutFile;
		setupConfigFile = setupFile;
	}
	//all getters below
	public Set<Player> getPlayers(){
		return players;
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
		// TODO Auto-generated method stub
		return null;
	}
	public Set<Card> getPeople() {
		// TODO Auto-generated method stub
		return null;
	}
	public Set<Card> getWeapons() {
		// TODO Auto-generated method stub
		return null;
	}
}
