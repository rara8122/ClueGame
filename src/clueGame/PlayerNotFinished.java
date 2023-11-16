package clueGame;

public class PlayerNotFinished extends Exception{
	//default constructor with a default message 
	public PlayerNotFinished() {
		super("Player is not finished with their turn");
	}
	
	//constructor with a specific message
	public PlayerNotFinished(String message) {
		super(message);
	}
}
