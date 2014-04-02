package rogue.game.state;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import rogue.entity.EnvironmentEntity;
import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;
import rogue.map.Position;

public class GameState implements Runnable {
	private MessageHandler handler;
	private boolean running;
	private GameMap map;
	private List<Entry<Client, Player>> clientPlayerPairs;
	private long timeOfLastUpdate;
	private long delta;

	private final int MAP_SIZE = 16;

	public GameState(List<Client> clients) {
		// Add clients to the MessageHandler, only talk with them through the
		// handler from now on
		handler = new MessageHandler(this);

		// TODO: send server info

		clientPlayerPairs = new ArrayList<>();
		for (Client c : clients) {
			handler.addObserver(c);
			pairClientToNewPlayer(c);
			c.addObserver(handler);
		}

		// Generate map and tell clients
		map = new GameMap(MAP_SIZE);
		handler.notifyCreation(map);

		placePlayers();
	}

	private void pairClientToNewPlayer(Client c) {
		Player player = new Player(null);
		handler.notifyCreation(player);

		Entry<Client, Player> entry = new AbstractMap.SimpleEntry<>(c, player);
		clientPlayerPairs.add(entry);
	}

	// Place players on map
	// Link players with a client
	private void placePlayers() {
		for (Entry<Client, Player> entry : clientPlayerPairs) {
			Player p = entry.getValue();
			map.put(p, map.getSpawnSquare());
		}
		handler.notifyUpdate(map.getEntities());
	}

	@Override
	public void run() {
		timeOfLastUpdate = System.nanoTime();
		
		running = true;
		while (running) {
			update();
		}
	}

	private void update() {
		delta = System.nanoTime() - timeOfLastUpdate;
		if (isDone()) {
			running = false;
		} else if (beatHasElapsed(delta)) {
			movePlayers();
			timeOfLastUpdate = System.nanoTime();
		}
	}

	private boolean beatHasElapsed(long delta) {
		return delta > 500000000L; //120bpm
	}

	private void movePlayers() {
		// Read each player's input buffer
		for (Entry<Client, Player> entry : clientPlayerPairs) {
			Player p = entry.getValue();
			Input input = p.getBuffer().readInput();
			if (validMovement(p, input)) {
				map.move(p, Position.calcPosition(p.getPosition(), input));
			}
		}
		handler.notifyUpdate(map.getEntities());
	}

	private boolean validMovement(Player player, Input input) {
		Position newPos = Position.calcPosition(player.getPosition(), input);
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
		for (Entry<Client, Player> entry : clientPlayerPairs) {
			if (entry.getKey().equals(client)) {
				return entry.getValue().getBuffer();
			}
		}
		return null;
	}

}
