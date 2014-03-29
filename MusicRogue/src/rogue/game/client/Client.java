package rogue.game.client;

import java.util.Observable;
import java.util.Observer;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;

import rogue.game.message.Message;

public abstract class Client implements Observer {
	protected int clientNumber;
	static int currentNumber = 1;

	public Client() {
		this.clientNumber = currentNumber++;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO: Could/should make a check here to make sure o is a MessageHandler
		Message msg = (Message) arg;
		recieveMessage(msg);
	}

	public abstract void recieveMessage(Message msg);

}
