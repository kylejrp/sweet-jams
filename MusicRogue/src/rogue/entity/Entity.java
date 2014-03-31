package rogue.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Renderable;

import rogue.map.Position;

public abstract class Entity implements Renderable{
	public Entity(Position position){
		this.position = position;
	}

	protected Position position;
	
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
