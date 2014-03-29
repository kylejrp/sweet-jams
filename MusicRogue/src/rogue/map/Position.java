package rogue.map;

import rogue.entity.player.Player;
import rogue.game.state.InputBuffer.Input;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
	
}
