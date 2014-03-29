package rogue.game.message;


import java.util.Observable;

import rogue.entity.Entity;
import rogue.entity.player.Player;
import rogue.game.message.Message.MessageDetail;
import rogue.game.message.Message.MessageType;

public class MessageHandler extends Observable{
	public void notifyPositions(Entity[][] entityLayer) {
		Message msg = new Message(MessageType.ENTITY);
		msg.setDetail(MessageDetail.UPDATE);
		msg.setObject(entityLayer);
		
		setChanged();
		notifyObservers(msg);
	}
}
