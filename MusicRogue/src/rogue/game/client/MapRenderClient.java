package rogue.game.client;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

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
			List<Client> clients = new LinkedList<Client>();
			clients.add(new MapRenderClient(renderer));	
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

	public MapRenderClient(MapRenderer renderer){
		this.renderer = renderer;
	}
	
	@Override
	public void recieveMessage(Message msg) {
		if (msg.getObject() instanceof GameMap
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			GameMap map = (GameMap) msg.getObject();
			renderer.setMap(map);
		} else if (msg.getObject() instanceof Entity[][] && msg.getDetail() == Message.MessageDetail.UPDATE){
			Entity[][] entLayer = (Entity[][]) msg.getObject();
			renderer.setEntities(entLayer);
		}
	}
}

class MapRenderer extends BasicGame {
	GameMap map;
	Entity[][] entities;
	float SCALE = 8f;

	public MapRenderer(String title) {
		super(title);
	}

	public void setEntities(Entity[][] entLayer) {
		this.entities = entLayer;
	}

	public void setMap(GameMap map) {
		this.map = map;
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
						Shape square = new Rectangle(x * SCALE, y * SCALE,
								1f * SCALE, 1f * SCALE);
						g.setColor(element.getColor());
						g.fill(square);
					}
				}
			}

			Entity[][] entityLayer = map.getEntityLayer();
			for (Entity[] row : entityLayer) {
				for (Entity element : row) {
					if (element != null) {
						int x = element.getPosition().getX();
						int y = element.getPosition().getY();
						Shape square = new Rectangle(x * SCALE, y * SCALE,
								1f * SCALE, 1f * SCALE);
						g.setColor(element.getColor());
						g.fill(square);
					}
				}
			}
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

}
