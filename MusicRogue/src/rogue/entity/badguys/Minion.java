package rogue.entity.badguys;

import org.newdawn.slick.Color;

import rogue.entity.Entity;
import rogue.entity.badguys.Minion.MinionType;
import rogue.map.Position;

public class Minion extends Entity {
	private final MinionType type;
	
	
	public Minion(Position position) {
		super(position);
		type = MinionType.getRandomType();
	}

	public MinionType getMinionType() {
		return type;
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
	public Color getColor(){
		return Color.green;
	}

	public static enum MinionType{
		ROTATE, COLOR, SCALE, FLASH, DEATH;
		
		public static MinionType getRandomType(){
			int i = (int) Math.floor(Math.random()*MinionType.class.getEnumConstants().length);
			return MinionType.class.getEnumConstants()[i];
		}
	}
}
