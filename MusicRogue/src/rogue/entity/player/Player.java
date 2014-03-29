package rogue.entity.player;

import rogue.entity.Entity;
import rogue.map.Position;

public class Player implements Entity{
	Position position;
	
	public Position getPosition() {
		return new Position(position.getX(), position.getY());
	}

	public void setPosition(Position position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
	}

	public Player(){
		
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(float x, float y) {
		// TODO Auto-generated method stub
		
	}
	
}
