package rogue.entity.player;

import org.newdawn.slick.Color;

import rogue.entity.Entity;
import rogue.game.state.InputBuffer;
import rogue.map.Position;

public class Player extends Entity {

	public Player() {
		super();
	}

	public Player(Player e) {
		super();
		if (e.getPosition() != null) {
			this.position = new Position(e.getPosition());
		}
		this.ID = e.ID;
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

	public Color getColor() {
		return Color.blue;
	}

}
