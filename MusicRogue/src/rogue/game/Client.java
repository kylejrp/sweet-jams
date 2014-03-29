package rogue.game;

import java.util.Observable;
import java.util.Observer;

public class Client implements Observer{
	int clientNumber;
	static int currentNumber = 1;
	
	public Client(){
		this.clientNumber = currentNumber++;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(clientNumber + ": Recieved message!");
	}
	
}
