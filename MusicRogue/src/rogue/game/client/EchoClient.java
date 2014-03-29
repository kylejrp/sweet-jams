package rogue.game.client;

import rogue.game.message.Message;

public class EchoClient extends Client{

	@Override
	public void recieveMessage(Message msg) {
		System.out.println("Client " + clientNumber + ": Recieved a message.");
		System.out.println("\tMessage is: " + msg.getType() + msg.getDetail());
	}

}
