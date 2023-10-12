/*
*Class: This class is an exception class that extends exception. This is neccessary because our game code will need to trap errors in finding the data files and/or actual errors in the data structures using exceptions.
*Authors: Rachel Davy, Melanie Perez 
*Date: 10/08/2023
*Collaborators: none
*Sources: none
*/
package clueGame;

public class BadConfigFormatException extends Exception {
	
	//default constructor with a default message 
	public BadConfigFormatException() {
		super("Invalid configuration format detected.");
	}
	
	//constructor with a specific message
	public BadConfigFormatException(String message) {
		super(message);
	}
		
}
