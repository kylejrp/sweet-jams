package rogue.entity;

import org.newdawn.slick.Renderable;

import rogue.map.Position;

public abstract class Entity implements Renderable{

	protected Position position;
	
	public Position getPosition() {
		return new Position(position.getX(), position.getY());
	}

	public void setPosition(Position position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
	}
	
	public void setPosition(int x, int y){
		this.position = new Position(x, y);
	}
	
	public abstract void onCreate();
	public abstract void onCollide(Entity entity);
	public abstract void onDestroy();
	
}
