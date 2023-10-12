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
    	loadSetupConfig();
    	loadLayoutConfig();
    }
    //loads setup config
    public void loadSetupConfig(){
    	FileReader setupFile;
		try {
			setupFile = new FileReader("data/" + setupConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
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
					break;
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
    
    public void loadLayoutConfig(){
    	FileReader layoutFile;
		try {
			layoutFile = new FileReader("data/" + layoutConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Scanner layoutScanner = new Scanner(layoutFile);
    	int row = 0;
		while (layoutScanner.hasNextLine()) {
			String line = layoutScanner.nextLine();
			String[] cells = line.split(","); 
		}
		if(numColumns == 0) {
			numColumns = cells.length;
		}
		
		String[][] strings;
		grid = new BoardCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; i < numColumns; i++) {
				grid[i][j] = new BoardCell(i, j, strings[i][j].charAt(0));
			}
		}
		String currentString;
		BoardCell currentCell;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; i < numColumns; i++) {
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
								currentCell.setRoomLabel(true);
							} else {
								currentCell.setSecretPassage(currentString.charAt(1));
							}
					}
				}
				if(currentString.charAt(0) == 'W') {
					grid[i - 1][j].addAdj(currentCell);
					grid[i + 1][j].addAdj(currentCell);
					grid[i][j - 1].addAdj(currentCell);
					grid[i][j + 1].addAdj(currentCell);
				}
			}
		}
    }
    
    /*
    public void loadLayoutConfig() {
        FileReader layoutFile;
        try {
            layoutFile = new FileReader("data/" + layoutConfigFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Scanner layoutScanner = new Scanner(layoutFile);
        int row = 0;

        // Initialize strings and cells arrays
        String[][] strings = new String[numRows][numColumns];
        String[] cells = null;

        while (layoutScanner.hasNextLine()) {
            String line = layoutScanner.nextLine();
            cells = line.split(",");
            strings[row] = cells;
            row++;
        }

        if (numColumns == 0) {
            numColumns = cells.length;
        }

        grid = new BoardCell[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new BoardCell(i, j, strings[i][j].charAt(0));
            }
        }

        String currentString;
        BoardCell currentCell;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                currentString = strings[i][j];
                currentCell = grid[i][j];

                if (currentString.length() == 2) {
                    if (currentString.charAt(0) == 'W') {
                        if (currentString.charAt(1) == 'v') {
                            currentCell.setDoorDirection(DoorDirection.DOWN);
                        } else if (currentString.charAt(1) == '^') {
                            currentCell.setDoorDirection(DoorDirection.UP);
                        } else if (currentString.charAt(1) == '<') {
                            currentCell.setDoorDirection(DoorDirection.LEFT);
                        } else if (currentString.charAt(1) == '>') {
                            currentCell.setDoorDirection(DoorDirection.RIGHT);
                        }
                    } else {
                        if (currentString.charAt(1) == '#') {
                            roomMap.get(currentString.charAt(0)).setLabelCell(currentCell);
                            currentCell.setRoomLabel(true);
                        } else if (currentString.charAt(1) == '*') {
                            roomMap.get(currentString.charAt(0)).setCenterCell(currentCell);
                            currentCell.setRoomLabel(true);
                        } else {
                            currentCell.setSecretPassage(currentString.charAt(1));
                        }
                    }
                }
                if (currentString.charAt(0) == 'W') {
                    // Make sure you check boundaries to avoid IndexOutOfBoundsException
                    if (i - 1 >= 0) grid[i - 1][j].addAdj(currentCell);
                    if (i + 1 < numRows) grid[i + 1][j].addAdj(currentCell);
                    if (j - 1 >= 0) grid[i][j - 1].addAdj(currentCell);
                    if (j + 1 < numColumns) grid[i][j + 1].addAdj(currentCell);
                }
            }
        }
    }
    */

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
