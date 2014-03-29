package rogue.game.state;

import java.util.LinkedList;
import java.util.List;

import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.EchoClient;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;

public class Game implements Runnable {
	private MessageHandler handler;
	private boolean running;

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
		}
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			update();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void update() {
		if (isDone()) {
			running = false;
		} else {
			// Do map stuff later, just figure out messages for now
			// movePlayers();
			// handler.notifyPositions(map.getEntityLayer());
			handler.notifyPositions(null);
		}
	}

	/*
	 * This is broken ahhhh implement later private void movePlayers() { for
	 * (int i = 0; i < inputs.length; i++) { // Read each player's input buffer
	 * Input input = inputs[i].readInput();
	 * 
	 * // Check if a movement is valid if (validMovement(players[i], input)) {
	 * // Move the player to the new position Position newPos =
	 * Position.calcPosition( players[i].getPosition(), input);
	 * map.move(players[i], newPos); } } }
	 */

	private boolean validMovement(Player player, Input input) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isDone() {
		return false;
	}

}
