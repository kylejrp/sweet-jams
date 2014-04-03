package rogue.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;
import rogue.entity.badguys.Minion;
import rogue.entity.player.Player;

public class GameMap {
	final private EnvironmentEntity[][] environmentLayer;
	private List<Entity> entities;

	public GameMap(int size) {
		environmentLayer = GameMapGenerator.generateBottomLayer(size);
		entities = new ArrayList<Entity>();
	}

	public GameMap(GameMap map) {
		this.environmentLayer = map.getEnvironmentLayer().clone();
		this.entities = new ArrayList<Entity>();
		if (map.getEntities().size() > 0) {
			for (Entity e : map.getEntities()) {
				if(e instanceof Player){
				this.entities.add(new Player((Player) e));
				} else if (e instanceof Minion){
					this.entities.add(new Minion((Minion) e));
				}
			}
		}
	}

	public void put(Entity entity, Position position) {
		entity.setPosition(position);
		entities.add(entity);
	}

	public void move(Entity entity, Position position) {
		entity.setPosition(position.getX(), position.getY());
	}

	public Entity remove(Entity entity) {
		Entity removedEntity = null;
		for (Entity e : entities) {
			if (e == entity) {
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
		Random randomInt = new Random();

		while (true) {
			boolean validPosition = true;
			int x = randomInt.nextInt(environmentLayer.length);
			int y = randomInt.nextInt(environmentLayer.length);
			if (environmentLayer[y][x].getType() != EnvironmentEntity.EnvironmentType.FLOOR) {
				validPosition = false;
			}
			Position pos = new Position(x, y);
			for (Entity e : entities) {
				if (pos == e.getPosition()) {
					validPosition = false;
				}
			}
			if (validPosition) {
				return pos;
			}
		}

	}
}
