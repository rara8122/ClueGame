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
import java.util.Map;
import java.util.Scanner;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
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
    	roomMap = new HashMap <Character, Room> ();
    	try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //loads setup config
    public void loadSetupConfig () throws BadConfigFormatException, FileNotFoundException {
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
						//THROW BadConfigFormatException
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
				info = setupScanner.nextLine();
				newRoom = new Room(roomName);
				roomMap.put(roomChar, newRoom);
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
		while (layoutScanner.hasNextLine()) {
			file = file + " " + layoutScanner.nextLine();
		}
		lines = file.split(" "); 
		numRows = lines.length;
		String[][] strings = new String[numRows][];
		strings[0] = lines[0].split(",");
		numColumns = strings[0].length;
		for (int l = 1; l < numRows; l++) {
			strings[l] = lines[l].split(",");
			if(strings[l].length != numColumns) {
				//THROW BadConfigFormatException
			}
		}
		grid = new BoardCell[numRows][numColumns];
		String currentString;
		BoardCell currentCell;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j] = new BoardCell(i, j, strings[i][j].charAt(0));
				currentString = strings[i][j];
				currentCell = grid[i][j];
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

    //method to set the grid boardcell
	public void setGrid(BoardCell[][] grid) {
		this.grid = grid;
	}

	//getter for getRooomMap
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}
	//getter for getCell
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	//setter for ConfigFiles
	public void setConfigFiles(String layoutFile, String setupFile) {
		layoutConfigFile = layoutFile;
		setupConfigFile = setupFile;
	}
	//getter for numRows
	public int getNumRows() {
		return numRows;
	}
	//getter for numColums
	public int getNumColumns() {
		return numColumns;
	}
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
}
