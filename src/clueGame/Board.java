/*
*Class: This is the board class which contains our board. This is essentially the same as TestBoard class, except it was modified for our game code and test files. It implements a singleton pattern. 
*Authors: Melanie Perez, Rachel Davy
*Date: 10/08/2023
*Collaborators: none
*Sources: none
*/
package clueGame;
import java.util.Map;

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
	public void setConfigFiles(String string, String string2) {
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
		return new Room();//new Room();
	}
	
	public Room getRoom(BoardCell cell) {
		return new Room();
	}
}
