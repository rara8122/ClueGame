/*
 * Class: This is a custom exception class for invalid player clicks during their turn. 
 * Authors: Rachel Davy
 * Collaborators: none
 * Sources: none
 */
package clueGame;

public class MisClick extends Exception{
	//default constructor with a default message 
	public MisClick() {
		super("Player is not finished with their turn");
	}	
	//constructor with a specific message
	public MisClick(String message) {
		super(message);
	}
}
