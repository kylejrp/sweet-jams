package rogue.game;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.SoundStore;

import rogue.entity.Entity;
import rogue.entity.EnvironmentEntity;
import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.MapRenderClient;
import rogue.game.state.InputBuffer;
import rogue.map.GameMap;

public class MapRenderer extends BasicGame {
	GameMap map;
	Client client;
	List<Entity> entities;
	float scale = 8f;
	private float fade;

	public MapRenderer(String title) {
		super(title);
		entities = new ArrayList<Entity>();
		fade = 1.0f;
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
		fade = 1.0f;
		rotate += 0.5f;
	}

	public void setMap(GameMap map) {
		this.map = map;
		scale = 512f / (map.getEnvironmentLayer().length);
	}

	float rotate = 0.0f;

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Shape bg = new Rectangle(0, 0, 512, 512);
		g.setColor(new Color(30, 0, 0));
		g.fill(bg);
		// g.rotate(256, 256, rotate);
		if (map != null) {
			Shape square = new Rectangle(1 * scale, 1 * scale, 1f * scale,
					1f * scale);
			EnvironmentEntity[][] environmentLayer = map.getEnvironmentLayer();
			for (EnvironmentEntity[] row : environmentLayer) {
				for (EnvironmentEntity element : row) {
					if (element != null
							&& element.getType() != EnvironmentEntity.EnvironmentType.FLOOR) {
						int x = element.getPosition().getX();
						int y = element.getPosition().getY();
						square.setLocation(x * scale, y * scale);
						g.setColor(element.getColor().scaleCopy(fade));
						g.fill(square);
						g.setColor(element.getColor());
						g.draw(square);
					}
				}
			}

			for (Entity element : entities) {
				if (element != null && element.getPosition() != null) {
					int x = element.getPosition().getX();
					int y = element.getPosition().getY();
					square.setLocation(x * scale, y * scale);
					g.setColor(element.getColor());
					g.fill(square);
				}
			}

			g.resetTransform();

			for (Entity element : entities) {
				if (element instanceof Player) {
					Player player = (Player) element;
					InputBuffer buffer = player.getBuffer();
					Object[] inputs = buffer.toArray();
					float xPos = 0.0f;
					for (Object obj : inputs) {
						InputBuffer.Input in = (InputBuffer.Input) obj;
						square.setLocation(xPos, scale * 15);
						g.setColor(Color.black);
						g.fill(square);
						g.setColor(Color.white);
						g.draw(square);
						g.drawString(in.toString(), xPos, scale * 15);
						xPos += scale;
					}

				}
			}

		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		SoundStore.get().setMaxSources(32);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		fade *= 0.9995;
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case org.newdawn.slick.Input.KEY_UP:
		case org.newdawn.slick.Input.KEY_W:
			client.sendInput(rogue.game.state.InputBuffer.Input.UP);
			break;
		case org.newdawn.slick.Input.KEY_DOWN:
		case org.newdawn.slick.Input.KEY_S:
			client.sendInput(rogue.game.state.InputBuffer.Input.DOWN);
			break;
		case org.newdawn.slick.Input.KEY_LEFT:
		case org.newdawn.slick.Input.KEY_A:
			client.sendInput(rogue.game.state.InputBuffer.Input.LEFT);
			break;
		case org.newdawn.slick.Input.KEY_RIGHT:
		case org.newdawn.slick.Input.KEY_D:
			client.sendInput(rogue.game.state.InputBuffer.Input.RIGHT);
		default:
			break;
		}

	}

	public void keyReleased(int key, char c) {
	}

}
