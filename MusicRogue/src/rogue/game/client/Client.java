package rogue.game.client;

import java.util.Observable;
import java.util.Observer;

import rogue.game.message.Message;
import rogue.game.message.MessageHandler;

public abstract class Client implements Observer {
	protected int clientNumber;
	static int currentNumber = 1;

	public Client() {
		this.clientNumber = currentNumber++;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MessageHandler) {
			Message msg = (Message) arg;
			recieveMessage(msg);
		}
	}

	public abstract void recieveMessage(Message msg);

}
