/*
 *Class: represents the solution to a mystery or puzzle in a game, which typically includes the identification of three main components: the room, the person, and the weapon. Each of these components is represented by a Card object.
 *Authors: Melanie Perez, Rachel Davy
 *Date: 10/08/2023
 *Collaborators: none
 *Sources: none
 */
package clueGame; 

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;


	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	public Card getRoom() {
		return room;
	}
	public void setRoom(Card room) {
		this.room = room;
	}
	public Card getWeapon() {
		return weapon;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	public Card getPerson() {
		return person;
	}
	public void setPerson(Card person) {
		this.person = person;
	}

}
