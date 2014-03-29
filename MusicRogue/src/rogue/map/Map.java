package rogue.map;



import java.util.ArrayList;
import java.util.List;
import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;

public class Map{
	private EnvironmentEntity[][] environmentLayer;
	private Entity[][] entityLayer;
	private List<Entity>  entities ;
	
	public Map(int size){
		environmentLayer = MapGenerator.generateBottomLayer(size);
		entityLayer = MapGenerator.generateTopLayer(size);
		entities = new ArrayList<Entity>() ;
	}
	
	public void put(Entity entity, Position position){
		entityLayer[position.getX()][position.getY()] = entity;
		entities.add(entity) ;
	}
	
	public void move(Entity entity, Position position){
		int x = entity.getPosition().getX() ;
		int y = entity.getPosition().getY();
		entityLayer[position.getX()][position.getY()] = entity ;
		entityLayer[x][y] = null ;
		entity.setPosition(x,y);
	}
	
	public Entity remove(Position position){
		Entity removedEntity = entityLayer[position.getX()][position.getY()] ;
		entityLayer[position.getX()][position.getY()] = null ;
		removedEntity.setPosition(null);
		entities.remove(removedEntity);
		return removedEntity ;
	}
	
	public Entity remove(Entity entity){
		Entity removedEntity = null;
		for(Entity e: entities)
		{
			if(e == entity)
			{
				removedEntity = remove(entity.getPosition());
				break;
			}
		}
		return removedEntity;
	}

	public EnvironmentEntity[][] getEnvironmentLayer() {
		return environmentLayer;
	}

	public Entity[][] getEntityLayer() {
		return entityLayer;
	}
	
}
