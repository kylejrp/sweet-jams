package rogue.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Renderable;

import rogue.game.state.InputBuffer;
import rogue.map.Position;

public abstract class Entity{
	private InputBuffer buffer;
	protected Position position;

	
	public Entity(Position position){
		this.position = position;
		buffer = new InputBuffer(4);

	}

	public InputBuffer getBuffer() {
		return buffer;
	}
	
	public Position getPosition() {
		return new Position(position.getX(), position.getY());
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setPosition(int x, int y){
		this.position = new Position(x, y);
	}
	
	public abstract void onCreate();
	public abstract void onCollide(Entity entity);
	public abstract void onDestroy();
	
	public abstract Color getColor();
	
}
