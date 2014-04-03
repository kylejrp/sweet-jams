package rogue.map;

import rogue.game.state.InputBuffer.Input;

public class Position {
	private final int x;
	private final int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Position calcPosition(Position position, Input input) {
		switch (input) {
		case UP:
			position = new Position(position.getX(), position.getY()-1);
			break;
		case DOWN:
			position = new Position(position.getX(), position.getY()+1);
			break;
		case LEFT:
			position = new Position(position.getX()-1, position.getY());
			break;
		case RIGHT:
			position = new Position(position.getX()+1, position.getY());
			break;
		default:
			break;
		}
		return position;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", "+ y + ")";
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Position){
			Position pos = (Position) o;
			return (pos.getX() == this.getX()) && (pos.getY() == this.getY());
		} else {
			return false;
		}
	}
	
}
