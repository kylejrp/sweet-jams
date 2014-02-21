package rogue.map;

import rogue.entity.Entity;

public class Map {
	Entity[][] entityLayer;
	
	public Map(MapType type, int size){
		entityLayer = MapGenerator.generate(type, size);
	}
	
	public void put(Entity entity){
		// TODO
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
	
}
