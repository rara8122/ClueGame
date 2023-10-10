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
    	roomMap = new HashMap <Character, Room> ();
    	FileReader layoutFile;
		try {
			layoutFile = new FileReader("data/" + layoutConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Scanner layoutScanner = new Scanner(layoutFile);
		FileReader setupFile;
		try {
			setupFile = new FileReader("data/" + setupConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Scanner setupScanner = new Scanner(setupFile);
		String info;
		String roomName;
		Character roomChar;
		info = setupScanner.nextLine();
		Room newRoom;
		while(true) {
			info = setupScanner.next();
			if (!info.equals("Room,")) {
				break;
			}
			roomName = setupScanner.next();
			roomName = roomName.replace(",", "");
			roomName.trim();
			info = setupScanner.nextLine();
			info = info.trim();
			if(info.length() == 1) {
				roomChar = info.charAt(0);
			} else {
				roomChar = ' ';
			}
			newRoom = new Room(roomName);
			roomMap.put(roomChar, newRoom);
		}
    }
    //loads setup config
    public void loadSetupConfig(){
    }
    //loads layout config
    public void loadLayoutConfig(){}
    
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
		return new BoardCell();
	}
	//setter for ConfigFiles
	public void setConfigFiles(String layoutFile, String setupFile) {
		layoutConfigFile = layoutFile;
		setupConfigFile = setupFile;
	}
	//getter for numRows
	public int getNumRows() {
		return 0;
	}
	//getter for numColums
	public int getNumColumns() {
		return 0;
	}
	public Room getRoom(char c) {
		return roomMap.get(c);//new Room();
	}
	
	public Room getRoom(BoardCell cell) {
		return new Room(" ");
	}
}
