package rogue.game.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.SoundStore;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;
import rogue.game.MusicRogueGame;
import rogue.game.message.Message;
import rogue.game.state.GameState;
import rogue.map.GameMap;

public class MapRenderClient extends Client {
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			MapRenderer renderer = new MapRenderer("Music Rogue");
			MapRenderClient client = new MapRenderClient(renderer);
			renderer.setClient(client);

			List<Client> clients = new LinkedList<Client>();
			clients.add(client);
			new Thread(new GameState(clients)).start();

			appgc = new AppGameContainer(renderer);
			appgc.setDisplayMode(512, 512, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(MusicRogueGame.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private MapRenderer renderer;

	public MapRenderClient(MapRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void recieveMessage(Message msg) {
		if (msg.getObject() instanceof GameMap
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			GameMap map = (GameMap) msg.getObject();
			renderer.setMap(map);
		} else if (msg.getObject() instanceof List<?>
				&& msg.getDetail() == Message.MessageDetail.UPDATE) {
			// BIG SCARY UNCHECKED CAST
			// Kind of checked because this is the only kind of list we ever
			// update with
			// Need a better way to check that it is a List<Entity> though
			List<Entity> entLayer = (List<Entity>) msg.getObject();
			renderer.setEntities(entLayer);
		}
	}
}

class MapRenderer extends BasicGame {
	GameMap map;
	Client client;
	List<Entity> entities;
	float scale = 8f;
	private Sound boof;

	public MapRenderer(String title) {
		super(title);
		entities = new ArrayList<Entity>();
	}

	public void setClient(MapRenderClient client) {
		this.client = client;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
		try {
			new Sound("res/boof.ogg").play();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMap(GameMap map) {
		this.map = map;
		scale = 512f / (map.getEnvironmentLayer().length);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if (map != null) {
			EnvironmentEntity[][] environmentLayer = map.getEnvironmentLayer();
			for (EnvironmentEntity[] row : environmentLayer) {
				for (EnvironmentEntity element : row) {
					if (element != null) {
						int x = element.getPosition().getX();
						int y = element.getPosition().getY();
						Shape square = new Rectangle(x * scale, y * scale,
								1f * scale, 1f * scale);
						g.setColor(element.getColor());
						g.fill(square);
					}
				}
			}

			for (Entity element : entities) {
				if (element != null && element.getPosition() != null) {
					int x = element.getPosition().getX();
					int y = element.getPosition().getY();
					Shape square = new Rectangle(x * scale, y * scale,
							1f * scale, 1f * scale);
					g.setColor(element.getColor());
					g.fill(square);
				}
			}

		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		SoundStore.get().setMaxSources(32);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
		case Input.KEY_W:
			client.sendInput(rogue.game.state.InputBuffer.Input.UP);
			break;
		case Input.KEY_DOWN:
		case Input.KEY_S:
			client.sendInput(rogue.game.state.InputBuffer.Input.DOWN);
			break;
		case Input.KEY_LEFT:
		case Input.KEY_A:
			client.sendInput(rogue.game.state.InputBuffer.Input.LEFT);
			break;
		case Input.KEY_RIGHT:
		case Input.KEY_D:
			client.sendInput(rogue.game.state.InputBuffer.Input.RIGHT);
		default:
			break;
		}

	}

	public void keyReleased(int key, char c) {
	}

}
