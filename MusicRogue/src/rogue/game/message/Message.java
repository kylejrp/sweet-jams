package rogue.game.message;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * Not sure if this is correct, if serialization is broken it's probably this
	 */
	private static final long serialVersionUID = 907554197612152985L;

	public static enum MessageType {
		// Messages that the server sends to players
		ENTITY, MAP, SERVER,
		// Messages that the players send to the server
		PLAYER, INPUT;
	}
	
	public static enum MessageDetail {
		CREATE, UPDATE, DESTROY;
	}

	MessageType type;
	MessageDetail detail;
	Serializable associatedObject;

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

	// Attatch a serializable object so that message can also be serializable
	// and sent over a network
	public void setObject(Serializable o) {
		// TODO: Ensure object matches message type, throw exception otherwise
		associatedObject = o;
	}

	// Returns the object attatched when the message was created
	public Object getObject() {
		return associatedObject;
	}

}
