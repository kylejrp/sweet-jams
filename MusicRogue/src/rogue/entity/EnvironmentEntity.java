package rogue.entity;

import org.newdawn.slick.Color;

import rogue.map.Position;

public class EnvironmentEntity extends Entity{

	final private char charRep;
	final private Color color;
	final private EnvironmentType type;
	
	public enum EnvironmentType {
		WALL, HALLWAY, FLOOR
	}

	public EnvironmentEntity(EnvironmentType t, Position position) {
		super();
		this.type = t;
		this.position = position;
		
		switch (t) {
		case HALLWAY:
			charRep = '%';
			color = Color.gray;
			break;
		case FLOOR:
			charRep = '.';
			color = Color.black;
			break;
		case WALL:
		default:
			charRep = '#';
			color = Color.red;
			break;
		}
	}
	
	public EnvironmentType getType(){
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

	public char getCharRep() {
		return charRep;
	}

	public Color getColor() {
		return color;
	}

}
