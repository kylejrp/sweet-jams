package rogue.map;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class GameMap {
	private EnvironmentEntity[][] environmentLayer;
	private Entity[][] entityLayer;
	
	
	public GameMap(int size){
		environmentLayer = GameMapGenerator.generateBottomLayer(size);
		entityLayer = GameMapGenerator.generateTopLayer(size);
	}
	
	public void put(Entity entity, Position position){
		entityLayer[position.getX()][position.getY()] = entity;
	}
	
	public void move(Entity entity, Position position){
		// TODO
	}
	
	public Entity remove(Position position){
		// TODO
		return null;
	}
	
	public Entity remove(Entity entity){
		// TODO
		return null;
	}

	public EnvironmentEntity[][] getEnvironmentLayer() {
		return environmentLayer;
	}

	public Entity[][] getEntityLayer() {
		return entityLayer;
	}
	
	public Position getSpawnSquare() {
		// TODO: Do some big boy calculations here
		int x = (int) (Math.random()*entityLayer.length);
		int y = (int) (Math.random()*entityLayer[0].length);
		return new Position(x, y);
	}
}
