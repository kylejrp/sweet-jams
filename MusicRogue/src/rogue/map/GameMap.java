package rogue.map;



import java.util.ArrayList;
import java.util.List;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class GameMap{
	private EnvironmentEntity[][] environmentLayer;
	private List<Entity>  entities ;
	
	public GameMap(int size){
		environmentLayer = GameMapGenerator.generateBottomLayer(size);
		entities = new ArrayList<Entity>() ;
	}
	
	public void put(Entity entity, Position position){
		entity.setPosition(position);
		entities.add(entity) ;
	}
	
	public void move(Entity entity, Position position){
		entity.setPosition(position.getX(),position.getY());
	}
	
	public Entity remove(Entity entity){
		Entity removedEntity = null;
		for(Entity e: entities)
		{
			if(e == entity)
			{
				entities.remove(entity);
				entity.setPosition(null);
				break;
			}
		}
		return removedEntity;
	}

	public EnvironmentEntity[][] getEnvironmentLayer() {
		return environmentLayer;
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public Position getSpawnSquare() {
		// TODO: Do some big boy calculations here
		return new Position(4, 4);
	}
}
