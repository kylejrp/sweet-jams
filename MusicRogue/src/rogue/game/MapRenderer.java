package rogue.game;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	Entity myEntity;
	float scale = 8f;
	private float fade;
	private boolean updated = false;

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
		updated = true;
		fade = 1.0f;
		rotate += 0.5f;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	float rotate = 0.0f;

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		float width = container.getScreenWidth();
		scale = width / (map.getEnvironmentLayer().length);

		
		g.setAntiAlias(true);
		Shape bg = new Rectangle(0, 0, width, width);
		g.setColor(new Color(30, 0, 0));
		g.fill(bg);
		g.rotate(width/2, width/2, rotate);
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
					g.draw(square);
				}
			}

			g.resetTransform();

			for (Entity element : entities) {
				if (element instanceof Player) {
					Player player = (Player) element;
					InputBuffer buffer = player.getBuffer();
					Object[] inputs = buffer.toArray();
					float xPos = 0.0f;
					Image keyImage;
					for (Object obj : inputs) {
						InputBuffer.Input in = (InputBuffer.Input) obj;
						switch (in) {
						case UP:
							keyImage = IMG_KEY_UP;
							break;
						case DOWN:
							keyImage = IMG_KEY_DOWN;
							break;
						case LEFT:
							keyImage = IMG_KEY_LEFT;
							break;
						case RIGHT:
							keyImage = IMG_KEY_RIGHT;
							break;
						default:
							keyImage = IMG_KEY_BLANK;
							break;
						}

						g.drawImage(keyImage, xPos, container.getScreenWidth() - KEYWIDTH);
						xPos += KEYWIDTH;
					}

				}
			}

		}
	}

	private Image IMG_KEY_UP;
	private Image IMG_KEY_DOWN;
	private Image IMG_KEY_LEFT;
	private Image IMG_KEY_RIGHT;
	private Image IMG_KEY_BLANK;
	private Sound SOUND_SWEET;
	private Sound SOUND_JAMS;
	final int KEYWIDTH = 50;

	@Override
	public void init(GameContainer arg0) throws SlickException {
		SoundStore.get().setMaxSources(32);
		SOUND_JAMS = new Sound("res/jams.wav");
		SOUND_SWEET = new Sound("res/sweet.wav");
		IMG_KEY_UP = new Image("res/Keyboard_White_Arrow_Up.png")
				.getScaledCopy(KEYWIDTH, KEYWIDTH);
		IMG_KEY_DOWN = new Image("res/Keyboard_White_Arrow_Down.png")
				.getScaledCopy(KEYWIDTH, KEYWIDTH);
		IMG_KEY_LEFT = new Image("res/Keyboard_White_Arrow_Left.png")
				.getScaledCopy(KEYWIDTH, KEYWIDTH);
		IMG_KEY_RIGHT = new Image("res/Keyboard_White_Arrow_Right.png")
				.getScaledCopy(KEYWIDTH, KEYWIDTH);
		IMG_KEY_BLANK = new Image("res/Blank_White_Normal.png").getScaledCopy(
				KEYWIDTH, KEYWIDTH);

	}
	
	private boolean sweetbool = true;

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		fade *= 0.9995;
		if (updated) {
			if(sweetbool){
				sweetbool = false;
				SOUND_SWEET.play(getHarmonicFrequency(), 1.0f);
			} else {
				sweetbool = true;
				SOUND_JAMS.play(getHarmonicFrequency(), 1.0f);
			}
			updated = false;
		}
	}

	private float getHarmonicFrequency() {
		float[] harmonics = { 1.0f, 1.059f, 1.122f, 1.189f, 1.260f,/* 1.335f,
				1.414f, 1.498f, 1.587f, 1.682f, 1.782f, 1.888f, 2.0f*/ };
		return harmonics[(int) Math.floor(Math.random() * harmonics.length)];
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

	public void setEntity(Entity entity) {
		myEntity = entity;
	}

}
