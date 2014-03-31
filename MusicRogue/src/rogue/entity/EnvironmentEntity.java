package rogue.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Renderable;

import rogue.map.Position;

public class EnvironmentEntity extends Entity{

	private char charRep;
	private Color color;

	public enum environmentType {
		WALL, HALLWAY, FLOOR
	}

	public EnvironmentEntity(environmentType t, Position position) {
		super(position);
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
