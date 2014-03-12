package rogue.map;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class Map {
	private EnvironmentEntity[][] environmentLayer;
	private Entity[][] entityLayer;
	
	
	public Map(MapType type, int size){
		environmentLayer = MapGenerator.generate(type, size);
		entityLayer = new Entity[size][size];
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

	public char[][] getEnvironmentLayer() {
		return environmentLayer;
	}

	public Entity[][] getEntityLayer() {
		return entityLayer;
	}
	
}
