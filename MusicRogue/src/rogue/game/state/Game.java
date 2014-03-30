package rogue.game.state;

import java.util.LinkedList;
import java.util.List;

import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.EchoClient;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;
import rogue.map.Map;
import rogue.map.MapGenerator;

public class Game implements Runnable {
	private MessageHandler handler;
	private boolean running;
	private Map map;

	private final int MAP_SIZE = 64;

	public static void main(String[] args) {
		List<Client> clients = new LinkedList<Client>();
		for (int i = 0; i < 4; i++) {
			clients.add(new EchoClient());
		}
		new Thread(new Game(clients)).start();
	}

	public Game(List<Client> clients) {
		// Add clients to the MessageHandler, only talk with them through the
		// handler from now on
		handler = new MessageHandler();
		for (Client c : clients) {
			handler.addObserver(c);
			createPlayer(c);
		}

		// TODO: send server info

		// Generate map and tell clients
		map = new Map(MAP_SIZE);
		handler.notifyCreation(map);
		
		// TODO: place players on the map
		placePlayers();
		handler.notifyPositions(map.getEntityLayer());
	}

	private void createPlayer(Client c) {
		// TODO Auto-generated method stub
		
	}

	// Place players on map
	// Link players with a client
	private void placePlayers() {
		// TODO
		
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
			handler.notifyPositions(map.getEntityLayer());
		}
	}

	private boolean beatHasElapsed() {
		// TODO Auto-generated method stub
		return true;
	}

	private void movePlayers() {
		// TODO
		// Read each player's input buffer
		// Check if a movement is valid if (validMovement(players[i], input)) {
		// Move the player to the new position
	}

	private boolean validMovement(Player player, Input input) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isDone() {
		return false;
	}

	public InputBuffer getInputBuffer(Client client) {

		
	}

}
