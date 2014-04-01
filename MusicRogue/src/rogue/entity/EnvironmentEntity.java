package rogue.entity;

import org.newdawn.slick.Color;

import rogue.map.Position;

public class EnvironmentEntity extends Entity{

	private char charRep;
	private Color color;
	private EnvironmentType type;
	
	public enum EnvironmentType {
		WALL, HALLWAY, FLOOR
	}

	public EnvironmentEntity(EnvironmentType t, Position position) {
		super(position);
		this.type = t;
		
		switch (t) {
		case WALL:
			charRep = '#';
			color = Color.red;
			break;
		case HALLWAY:
			charRep = '%';
			color = Color.gray;
			break;
		case FLOOR:
			charRep = '.';
			color = Color.black;
			break;
		default:
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

	@Override
	public void draw(float x, float y) {
		// TODO Auto-generated method stub

	}

	public char getCharRep() {
		return charRep;
	}

	public Color getColor() {
		return color;
	}

}
