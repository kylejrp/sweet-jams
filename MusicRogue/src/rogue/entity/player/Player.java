package rogue.entity.player;

import org.newdawn.slick.Color;

import rogue.entity.Entity;
import rogue.game.state.InputBuffer;
import rogue.map.Position;

public class Player extends Entity{	
	
	public Player(Position position){
		super(position);
	}

	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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
	
	public Color getColor(){
		return Color.blue;
	}
	
}
