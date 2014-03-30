package rogue.game.message;


import java.util.Observable;
import java.util.Observer;

import rogue.entity.Entity;
import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.EchoClient;
import rogue.game.message.Message.MessageDetail;
import rogue.game.message.Message.MessageType;
import rogue.game.state.Game;
import rogue.game.state.InputBuffer.Input;
import rogue.map.Map;

public class MessageHandler extends Observable implements Observer{
	public static void main(String[] args) {
		MessageHandler handler = new MessageHandler(null);
		handler.addObserver(new EchoClient());
		handler.notifyCreation(new Map(50));
	}
	
	private final Game game;
	
	public MessageHandler(Game game){
		this.game = game;
	}
	
	public void notifyPositions(Entity[][] entityLayer) {
		Message msg = new Message(MessageType.ENTITY);
		msg.setDetail(MessageDetail.UPDATE);
		msg.setObject(entityLayer);
		
		setChanged();
		notifyObservers(msg);
	}
	
	public void notifyUpdate(Object updatedObject){
		Message msg;
		
		if(updatedObject instanceof Map){
			msg = new Message(MessageType.MAP);
		} else if(updatedObject instanceof Entity){
			msg = new Message(MessageType.ENTITY);
		} else {
			msg = new Message(MessageType.ERROR);
		}
		
		msg.setDetail(MessageDetail.CREATE);
		msg.setObject(updatedObject);
		
		setChanged();
		notifyObservers(msg);
	}
	
	public void notifyCreation(Object createdObject) {
		Message msg;
		
		if(createdObject instanceof Map){
			msg = new Message(MessageType.MAP);
		} else if(createdObject instanceof Entity){
			msg = new Message(MessageType.ENTITY);
		} else {
			msg = new Message(MessageType.ERROR);
		}
		
		msg.setDetail(MessageDetail.CREATE);
		msg.setObject(createdObject);
		
		setChanged();
		notifyObservers(msg);
		
	}

	@Override
	// This is when a client sends input
	public void update(Observable obs, Object obj) {
		Client client = (Client) obs;
		Input input = (Input) obj;
	
		game.getInputBuffer(client).addInput();
		
	}
}
