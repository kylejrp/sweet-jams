package rogue.game.state;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;
import rogue.entity.badguys.Minion;
import rogue.entity.badguys.Minion.MinionType;
import rogue.entity.player.Player;
import rogue.game.client.AIClient;
import rogue.game.client.Client;
import rogue.game.client.MapRenderClient;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;
import rogue.map.Position;

public class GameState implements Runnable {
	private MessageHandler handler;
	private boolean running;
	private boolean jamming;
	private int beatCount;
	private long beatTime = 500000000L; // 120bpm
	private GameMap map;
	private List<Entry<Client, Entity>> clientEntityPairs;
	private long timeOfLastUpdate;
	private long delta;

	private final int MAP_SIZE = 20;

	public GameState(List<Client> clients) {
		// Add clients to the MessageHandler, only talk with them through the
		// handler from now on
		handler = new MessageHandler(this);
		clientEntityPairs = new ArrayList<Entry<Client, Entity>>();

		// TODO: send server info
		// clients.add(new AIClient(MinionType.COLOR));

		for (Client c : clients) {
			handler.addObserver(c);
			pairClientToNewEntity(c);
			c.addObserver(handler);
		}

		// Generate map and tell clients
		map = new GameMap(MAP_SIZE);
		GameMap mapCopy = new GameMap(map);
		handler.notifyCreation(mapCopy);

		placePlayers();
	}

	private Entity pairClientToNewEntity(Client c) {		
		Entity e;
		Entity eCopy;
		if (c instanceof MapRenderClient) {
			e = new Player();
			eCopy = new Player((Player) e);
		} else {
			e = new Minion();
			eCopy = new Minion((Minion) e);
			if (c instanceof AIClient) {
				((AIClient) c).setType(((Minion)e).getMinionType());
			}
		}
		Entry<Client, Entity> entry = new AbstractMap.SimpleEntry<>(c, e);
		Entry<Client, Entity> entryCopy = new AbstractMap.SimpleImmutableEntry<>(c, eCopy);
		
		clientEntityPairs.add(entry);
		handler.notifyCreation(new AbstractMap.SimpleEntry<Client, Entity>(entryCopy));
		return e;
	}

	// Place players on map
	// Link players with a client
	private void placePlayers() {
		for (Entry<Client, Entity> entry : clientEntityPairs) {
			Entity e = entry.getValue();
			map.put(e, map.getSpawnSquare());
		}
		//handler.notifyUpdate(map.getEntities());
	}

	@Override
	public void run() {
		timeOfLastUpdate = System.nanoTime();

		running = true;
		while (running) {
			update();
		}
	}

	private void attemptPlaceMinions() {
		if(beatCount % 4 == 0){
			Client c = new AIClient();
			handler.addObserver(c);
			Entity e = pairClientToNewEntity(c);
			map.put(e,map.getSpawnSquare());
			c.addObserver(handler);
		}
	}

	private void update() {
		delta = System.nanoTime() - timeOfLastUpdate;
		if (isDone()) {
			running = false;
		} else if (beatHasElapsed(delta)) {
			if(jamming){
				attemptPlaceMinions();
				beatCount++;
			}
			movePlayers();
			timeOfLastUpdate = System.nanoTime();
		}
	}

	private boolean beatHasElapsed(long delta) {
		return delta > beatTime;
	}

	private void movePlayers() {
		// Read each player's input buffer
		for (Entry<Client, Entity> entry : clientEntityPairs) {
			Entity e = entry.getValue();
			Input input = e.getBuffer().readInput();
			if(e instanceof Player){
				if(!validMovement(e, input)){
					running = false;
					handler.notifyDestruction(this);
				}
			}
			if (validMovement(e, input)) {
				if(beatCount % 2 == 0 && e instanceof Minion && ((Minion) e).getMinionType() == MinionType.FLASH){
					map.move(e, map.getSpawnSquare());
				}
				map.move(e, Position.calcPosition(e.getPosition(), input));
			}
		}

		checkCollision();
		handler.notifyUpdate(new GameMap(map).getEntities());
	}

	private void checkCollision() {
		Player p = null;
		List<Minion> badguys = new ArrayList<Minion>();
		for(Entry<Client, Entity> entry : clientEntityPairs){
			Entity e = entry.getValue();
			if(e instanceof Player){
				p = (Player) e;
			} else if (e instanceof Minion){
				badguys.add((Minion) e);
			}
		}
		
		for(Minion m : badguys){
			if(m.getPosition().equals(p.getPosition())){
				//notifyCollision(m.getMinionType());
				for(Entry<Client, Entity> entry : clientEntityPairs){
					if(entry.getValue() == m){
						handler.notifyDestruction(new Minion(m));
						if(m.getMinionType() == MinionType.SPEED){
							beatTime /= 1.2; // Speed is 20% faster
						} else if (m.getMinionType() == MinionType.DEATH){
							running = false;
							handler.notifyDestruction(this);
						}
						clientEntityPairs.remove(entry);
						map.remove(m);
						break;
					}
				}
			}
		}
		
		
	}

	private boolean validMovement(Entity e, Input input) {
		Position newPos = Position.calcPosition(e.getPosition(), input);
		EnvironmentEntity placeWeWantToMove = map.getEnvironmentLayer()[newPos
				.getY()][newPos.getX()];
		return placeWeWantToMove.getType().equals(
				EnvironmentEntity.EnvironmentType.FLOOR);
	}

	private boolean isDone() {
		return false;
	}

	// Finds the input buffer if it exists, returns null otherwise
	public InputBuffer getInputBuffer(Client client) {
		for (Entry<Client, Entity> entry : clientEntityPairs) {
			if (entry.getKey().equals(client)) {
				return entry.getValue().getBuffer();
			}
		}
		return null;
	}

	public void beginJams() {
		jamming = true;
	}

}
