package rogue.game;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import rogue.game.client.Client;
import rogue.game.client.MapRenderClient;
import rogue.game.state.GameState;

public class RunGame {
	public static void main(String[] args) throws SlickException {
		MapRenderer renderer = new MapRenderer("SWEET JAMS");
		MapRenderClient client = new MapRenderClient(renderer);
		new Thread(client).start();
		
		List<Client> clients = new LinkedList<Client>();
		clients.add(client);
		new Thread(new GameState(clients)).start();
		// DON"T REMOVE ME
	}
}
