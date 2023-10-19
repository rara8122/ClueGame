/*
 *Class: This is the board class which contains our board. This is essentially the same as TestBoard class, except it was modified for our game code and test files. It implements a singleton pattern. 
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
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
		//Read in files/initialize roomMap
		targets = new HashSet<>();
		visited = new HashSet<>();
		
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		calcAdjacencies();
	}
	//loads setup config
	public void loadSetupConfig () throws BadConfigFormatException, FileNotFoundException {
		roomMap = new HashMap <Character, Room> ();
		FileReader setupFile;
		setupFile = new FileReader("data/" + setupConfigFile);
		Scanner setupScanner = new Scanner(setupFile);
		//read and process the setup file (.txt)
		String info;
		String roomName;
		Character roomChar;
		Room newRoom;
		String comparer = "Room,";
		for (int i = 0; i < 2; i++) {
			info = setupScanner.nextLine();
			while(setupScanner.hasNextLine()) {
				info = setupScanner.next();
				if (!info.equals(comparer)) {
					if (info.equals("//")) {
						break;
					} else {
						throw new BadConfigFormatException("The setup file is improperly formatted.");
					}
				}
				roomName = setupScanner.next();
				roomName = roomName.replace(",", "");
				info = setupScanner.next();
				info = info.trim();
				while(info.length() != 1) {
					roomName = roomName + " " + info;
					roomName = roomName.replace(",", "");
					info = setupScanner.next();
					info = info.trim();
				}
				roomChar = info.charAt(0);
				newRoom = new Room(roomName);
				roomMap.put(roomChar, newRoom);
				if (setupScanner.hasNextLine()) {
					info = setupScanner.nextLine();
				}
			}
			comparer = "Space,";
		}
	}
	//loads layout config
	public void loadLayoutConfig () throws BadConfigFormatException, FileNotFoundException {
		FileReader layoutFile;
		layoutFile = new FileReader("data/" + layoutConfigFile);
		Scanner layoutScanner = new Scanner(layoutFile);
		int row = 0;
		String file = layoutScanner.nextLine();
		String[] lines;
		String line;
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
		for (int l = 1; l < numRows; l++) {
			strings[l] = lines[l].split(",");
			if(strings[l].length != numColumns) {
				//THROW BadConfigForma
				throw new BadConfigFormatException("The layout file has improper formatting.");
			}
		}
		grid = new BoardCell[numRows][numColumns];
		String currentString;
		BoardCell currentCell;
		Room correspondingRoom;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j] = new BoardCell(i, j, strings[i][j].charAt(0));
				currentString = strings[i][j];
				currentCell = grid[i][j];
				correspondingRoom = roomMap.get(currentString.charAt(0));
				if (correspondingRoom == null) {
					throw new BadConfigFormatException("Bad room: the layout file referes to a room that is not in the setup. ");
				}
				if(currentString.length() == 2) {
					if(currentString.charAt(0) == 'W') {
						if(currentString.charAt(1) == 'v') {
							currentCell.setDoorDirection(DoorDirection.DOWN);
						}
						if(currentString.charAt(1) == '^') {
							currentCell.setDoorDirection(DoorDirection.UP);
						}
						if(currentString.charAt(1) == '<') {
							currentCell.setDoorDirection(DoorDirection.LEFT);
						}
						if(currentString.charAt(1) == '>') {
							currentCell.setDoorDirection(DoorDirection.RIGHT);
						}
					} else { 
						if(currentString.charAt(1) == '#') {
							roomMap.get(currentString.charAt(0)).setLabelCell(currentCell);
							currentCell.setRoomLabel(true);
						}else if(currentString.charAt(1) == '*') {
							roomMap.get(currentString.charAt(0)).setCenterCell(currentCell);
							currentCell.setRoomCenter(true);
						} else {
							currentCell.setSecretPassage(currentString.charAt(1));
						}
					}
				}
			}
		}
	}
	
	public void calcAdjacencies() {
		BoardCell currentCell;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				currentCell = grid[i][j];
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
				if(currentCell.isDoorway()) {
					if(currentCell.getDoorDirection() == DoorDirection.DOWN) {
						currentCell.addAdj((roomMap.get(grid[i + 1][j].getInitial())).getCenterCell());
						((roomMap.get(grid[i + 1][j].getInitial())).getCenterCell()).addAdj(currentCell);;
					}
					if(currentCell.getDoorDirection() == DoorDirection.UP) {
						currentCell.addAdj((roomMap.get(grid[i - 1][j].getInitial())).getCenterCell());
						((roomMap.get(grid[i - 1][j].getInitial())).getCenterCell()).addAdj(currentCell);;
					}
					if(currentCell.getDoorDirection() == DoorDirection.LEFT) {
						currentCell.addAdj((roomMap.get(grid[i][j - 1].getInitial())).getCenterCell());
						((roomMap.get(grid[i][j - 1].getInitial())).getCenterCell()).addAdj(currentCell);;
					}
					if(currentCell.getDoorDirection() == DoorDirection.RIGHT) {
						currentCell.addAdj(roomMap.get(grid[i][j + 1].getInitial()).getCenterCell());
						((roomMap.get(grid[i][j + 1].getInitial())).getCenterCell()).addAdj(currentCell);;
					}
				}
				
				if(currentCell.getSecretPassage() != ' ') {
					(roomMap.get(currentCell.getInitial()).getCenterCell()).addAdj((roomMap.get(currentCell.getSecretPassage())).getCenterCell());
					(roomMap.get(currentCell.getSecretPassage()).getCenterCell()).addAdj((roomMap.get(currentCell.getInitial())).getCenterCell());
				}
			}
		}
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
	
	public void calcTargets(BoardCell cell, int i) {
		visited.clear(); 
		targets.clear();
		findTargets(cell, i);
				
	}
	
	
 public void findTargets(BoardCell currentCell, int steps) {
	 
		  visited.add(currentCell);

		    Set<BoardCell> adjacentCells = currentCell.getAdjList();
		    for (BoardCell cell : adjacentCells) {
		        if (!visited.contains(cell)) {
		            if (steps == 1 || cell.isRoomCenter()) {
		            	if(!cell.isOccupied()) {
		            	targets.add(cell);	
		            	} 
		                
		            } else if (cell.isWalkway() || cell.isDoorway()){
		                findTargets(cell, steps - 1);
		            }
		        }
		    }

		    visited.remove(currentCell);
		}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
