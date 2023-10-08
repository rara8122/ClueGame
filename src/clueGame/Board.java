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
    public void loadSetupConfig(){
    }
    public void loadLayoutConfig(){}
    
    
	public void setGrid(BoardCell[][] grid) {
		this.grid = grid;
	}


	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}
	
	public BoardCell getCell(int i, int j) {
		return new BoardCell();
	}
	
	public void setConfigFiles(String string, String string2) {
	}
	
	public int getNumRows() {
		return 0;
	}
	
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
