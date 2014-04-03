package rogue.game.message;

import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import rogue.entity.Entity;
import rogue.game.client.Client;
import rogue.game.message.Message.MessageDetail;
import rogue.game.message.Message.MessageType;
import rogue.game.state.GameState;
import rogue.game.state.InputBuffer;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;

public class MessageHandler extends Observable implements Observer {
	private final GameState game;

	public MessageHandler(GameState game) {
		this.game = game;
	}

	public void notifyUpdate(Object updatedObject) {
		Message msg;

		if (updatedObject instanceof GameMap) {
			msg = new Message(MessageType.MAP);
		} else if (updatedObject instanceof Entity) {
			msg = new Message(MessageType.ENTITY);
		} else {
			msg = new Message(MessageType.ERROR);
		}

		msg.setDetail(MessageDetail.UPDATE);
		msg.setObject(updatedObject);

		setChanged();
		notifyObservers(msg);
	}

	public void notifyCreation(Object createdObject) {
		Message msg;

		if (createdObject instanceof GameMap) {
			msg = new Message(MessageType.MAP);
		} else if (createdObject instanceof Entry) {
			msg = new Message(MessageType.ENTITY);
		} else {
			msg = new Message(MessageType.ERROR);
		}

		msg.setDetail(MessageDetail.CREATE);
		msg.setObject(createdObject);

		setChanged();
		notifyObservers(msg);

	}
	
	public void notifyDestruction(Object destroyedObject) {
		Message msg;

		if (destroyedObject instanceof GameMap) {
			msg = new Message(MessageType.MAP);
		} else if (destroyedObject instanceof Entry) {
			msg = new Message(MessageType.ENTITY);
		} else {
			msg = new Message(MessageType.ERROR);
		}

		msg.setDetail(MessageDetail.DESTROY);
		msg.setObject(destroyedObject);

		setChanged();
		notifyObservers(msg);

	}

	@Override
	// This is when a client sends input
	public void update(Observable obs, Object obj) {
		Client client = (Client) obs;
		Input input = (Input) obj;

		InputBuffer buffer = game.getInputBuffer(client);
		if(null != buffer) {
			buffer.addInput(input);
		}

	}
}
