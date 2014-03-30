package rogue.game.message;

import java.io.Serializable;

public class Message {
	public static enum MessageType {
		// Messages that the server sends to players
		ENTITY, MAP, SERVER,
		// Messages that the players send to the server
		PLAYER, INPUT,
		// Messages that we create when something went wrong
		ERROR;
	}
	
	public static enum MessageDetail {
		CREATE, UPDATE, DESTROY;
	}

	MessageType type;
	MessageDetail detail;
	Object associatedObject;

	public Message(MessageType type){
		this.type = type;
	}
	
	public void setDetail(MessageDetail detail) {
		this.detail = detail;
	}
	
	public MessageDetail getDetail(){
		return detail;
	}

	public MessageType getType() {
		return type;
	}

	// TODO: Change object to Serializable to be able to send things over the network
	public void setObject(Object o) {
		// TODO: Ensure object matches message type, throw exception otherwise
		associatedObject = o;
	}

	// Returns the object attached when the message was created
	public Object getObject() {
		return associatedObject;
	}
	
	public String toString() {
		return getType() + "_" + getDetail() + (getObject() == null ? getObject().toString() : "");
	}

}
