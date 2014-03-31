package rogue.game.state;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

import rogue.entity.EnvironmentEntity;
import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.EchoClient;
import rogue.game.client.MapViewerClient;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;
import rogue.map.Position;

public class GameState implements Runnable {
	private MessageHandler handler;
	private boolean running;
	private GameMap map;
	private List<Entry<Client, Player>> clientPlayerPairs;

	private final int MAP_SIZE = 64;

	public static void main(String[] args) {
		List<Client> clients = new LinkedList<Client>();
		for (int i = 0; i < 2; i++) {
			clients.add(new MapViewerClient());
		}
		new Thread(new GameState(clients)).start();
	}

	public GameState(List<Client> clients) {
		// Add clients to the MessageHandler, only talk with them through the
		// handler from now on
		handler = new MessageHandler(this);

		// TODO: send server info

		clientPlayerPairs = new ArrayList<>();
		for (Client c : clients) {
			handler.addObserver(c);
			pairClientToNewPlayer(c);
		}

		// Generate map and tell clients
		map = new GameMap(MAP_SIZE);
		handler.notifyCreation(map);

		placePlayers();
	}

	private void pairClientToNewPlayer(Client c) {
		Player player = new Player(new Position(0, 0));
		handler.notifyCreation(player);

		Entry<Client, Player> entry = new AbstractMap.SimpleEntry<>(c, player);
		clientPlayerPairs.add(entry);
	}

	// Place players on map
	// Link players with a client
	private void placePlayers() {
		for (Entry<Client, Player> entry : clientPlayerPairs) {
			Player p = entry.getValue();
			p.setPosition(map.getSpawnSquare());
		}
		handler.notifyPositions(map.getEntityLayer());
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			update();
		}
	}

	private void update() {
		if (isDone()) {
			running = false;
		} else if (beatHasElapsed()) {
			movePlayers();
		}
	}

	private boolean beatHasElapsed() {
		// TODO Auto-generated method stub
		return true;
	}

	private void movePlayers() {
		// Read each player's input buffer
		for (Entry<Client, Player> entry : clientPlayerPairs) {
			Player p = entry.getValue();
			Input input = p.getBuffer().readInput();
			if (validMovement(p, input)) {
				p.setPosition(Position.calcPosition(p.getPosition(), input));
			}
		}
		handler.notifyPositions(map.getEntityLayer());

		// Check if a movement is valid if (validMovement(players[i], input)) {
		// Move the player to the new position
	}

	private boolean validMovement(Player player, Input input) {
		Position newPos = Position.calcPosition(player.getPosition(), input);
		EnvironmentEntity placeWeWantToMove = map.getEnvironmentLayer()[newPos
				.getX()][newPos.getY()];
		return placeWeWantToMove
				.equals(EnvironmentEntity.environmentType.FLOOR);
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
