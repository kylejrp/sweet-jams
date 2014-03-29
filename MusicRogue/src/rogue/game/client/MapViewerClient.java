package rogue.game.client;

import rogue.game.message.Message;

public class MapViewerClient extends Client{

	@Override
	public void recieveMessage(Message msg) {
		// TODO Auto-generated method stub
		// Check message type = MAP
		// Check message detail = CREATE
		// render map
		
		msg.getObject() ;//cast to environment layer
	}

}
