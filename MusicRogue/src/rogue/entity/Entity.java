package rogue.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Renderable;

import rogue.game.state.InputBuffer;
import rogue.map.Position;

public abstract class Entity{
	private InputBuffer buffer;
	protected Position position;
	protected int ID;
	private static int currID = 0;

	
	public Entity(){
		ID = currID++;
		buffer = new InputBuffer(4);

	}

	public InputBuffer getBuffer() {
		return buffer;
	}
	
	public Position getPosition() {
		if(null == position){
			return null;
		}
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
	
	public boolean equals(Object o){
		if(o instanceof Entity){
			return ((Entity) o).ID == this.ID;
		} else {
			return false;
		}
	}
}
