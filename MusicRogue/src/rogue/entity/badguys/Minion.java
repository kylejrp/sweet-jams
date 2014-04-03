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
		return type.color;
	}

	public static enum MinionType{
		ROTATE(Color.cyan), COLOR(Color.white), SCALE(Color.orange), FLASH(Color.pink), DEATH(new Color(0xFFDD00FF)), SPEED(Color.yellow);
		
		private Color color;
		
		MinionType(Color c){
			this.color = c;
		}
		
		public static MinionType getRandomType(){
			int i = (int) Math.floor(Math.random()*MinionType.class.getEnumConstants().length);
			return MinionType.class.getEnumConstants()[i];
		}
	}
}
