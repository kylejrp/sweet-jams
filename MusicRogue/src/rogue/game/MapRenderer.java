package rogue.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import rogue.entity.badguys.Minion.MinionType;
import rogue.entity.player.Player;
import rogue.game.client.Client;
import rogue.game.client.MapRenderClient;
import rogue.game.state.InputBuffer;
import rogue.map.GameMap;

public class MapRenderer extends BasicGame {
	GameMap map;
	Client client;
	List<Entity> entities;
	Player myPlayer;
	private float fade;
	private boolean updated = false;
	private boolean running = true;
	private boolean flash = false;
	private boolean introtext = true;

	public MapRenderer(String title) {
		super(title);
		entities = new ArrayList<Entity>();
		fade = 1.0f;
	}

	public void setClient(MapRenderClient client) {
		this.client = client;
	}

	public void setEntities(List<Entity> entities) {
		synchronized (entities) {
			this.entities = entities;
		}
		updated = true;
		timeSinceLastUpdate = 0;
		fade = 1.0f;
		if (flashValue < 1.0f && flashValue > 0.0f) {
			flashValue = 0.0f;
		}
	}

	public void setMap(GameMap map) {
		synchronized (map) {
			this.map = map;
		}
	}

	float rotate = 0.0f;

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		float width = container.getWidth();

		g.setAntiAlias(true);
		g.scale(Math.max(fade, scaleMin), Math.max(fade, scaleMin));
		g.rotate(width / 2, width / 2, rotateValue);
		Shape bg = new Rectangle(0, 0, width, width);
		g.setColor(new Color(30, 0, 0));
		g.fill(bg);
		
		if (map != null) {
			float scale = width / (map.getEnvironmentLayer().length);
			Shape square = new Rectangle(1 * scale, 1 * scale, 1f * scale,
					1f * scale);
			synchronized (map) {
				EnvironmentEntity[][] environmentLayer = map
						.getEnvironmentLayer();
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
			}
			synchronized (entities) {

				for (Entity element : entities) {
					if (element != null && element.getPosition() != null) {
						int x = element.getPosition().getX();
						int y = element.getPosition().getY();
						square.setLocation(x * scale, y * scale);
						g.setColor(element.getColor());
						if (element instanceof Player) {
							g.fill(square);
						}
						g.draw(square);
					}
				}

				g.resetTransform();
			}

			InputBuffer buffer = getPlayer().getBuffer();
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

				g.drawImage(keyImage, xPos, container.getWidth() - KEYWIDTH);
				xPos += KEYWIDTH;

			}

			g.setColor(new Color(1, 1, 1, flashValue));
			g.fill(bg);

			if (introtext) {
				renderIntroText(container, g);
			}

			if (!running) {
				renderGameOver(container, g);
			}

			g.setColor(Color.white);
			g.drawString(Float.toString(totalTime/1000) + "s",10, 42);
		}
	}

	private void renderIntroText(GameContainer container, Graphics g) {
		if (flash) {
			g.setColor(Color.white);
			flash = false;
		} else {
			g.setColor(Color.black);
			flash = true;
		}
		g.drawString("PRESS SPACE TO JAM", container.getWidth() / 2,
				container.getHeight() / 2);
	}

	private void renderGameOver(GameContainer container, Graphics g) {
		if (flash) {
			g.setColor(Color.white);
			flash = false;
		} else {
			g.setColor(Color.black);
			flash = true;
		}
		g.drawString("GAME OVER", container.getWidth() / 2,
				container.getHeight() / 2);
	}

	private Image IMG_KEY_UP;
	private Image IMG_KEY_DOWN;
	private Image IMG_KEY_LEFT;
	private Image IMG_KEY_RIGHT;
	private Image IMG_KEY_BLANK;
	private Sound SOUND_SWEET;
	private Sound SOUND_JAMS;
	private Sound SOUND_GAMEOVER;
	private List<Sound> JAM_LINES;
	final int KEYWIDTH = 50;

	@Override
	public void init(GameContainer arg0) throws SlickException {
		SoundStore.get().setMaxSources(32);
		SOUND_JAMS = new Sound("res/jams.wav");
		SOUND_SWEET = new Sound("res/sweet.wav");
		SOUND_GAMEOVER = new Sound("res/JamOver.wav");
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
		JAM_LINES = new ArrayList<Sound>();
		JAM_LINES.add(new Sound("res/Bane.wav"));
		JAM_LINES.add(new Sound("res/Bigger.wav"));
		JAM_LINES.add(new Sound("res/CantStop.wav"));
		JAM_LINES.add(new Sound("res/Combo.wav"));
		JAM_LINES.add(new Sound("res/Factoring.wav"));
		JAM_LINES.add(new Sound("res/Fus.wav"));
		JAM_LINES.add(new Sound("res/Handle.wav"));
		JAM_LINES.add(new Sound("res/HoldOn.wav"));
		JAM_LINES.add(new Sound("res/Moist.wav"));
		JAM_LINES.add(new Sound("res/PBJam.wav"));
		JAM_LINES.add(new Sound("res/Pity.wav"));
		JAM_LINES.add(new Sound("res/Pump.wav"));
		JAM_LINES.add(new Sound("res/Silence.wav"));
		JAM_LINES.add(new Sound("res/Suculent.wav"));
		JAM_LINES.add(new Sound("res/Ultra.wav"));

	}

	private Sound getRandomLine() {
		Random r = new Random();
		return JAM_LINES.get(r.nextInt(JAM_LINES.size()));
	}

	private boolean sweetbool = true;
	private float rotateVelocity = 0;
	private float rotateValue = 0;
	private float scaleMin = 1.0f;
	private float flashValue;
	private float timeSinceLastUpdate = 0;
	private float countdownToCheezyLine = 10000f;
	private float totalTime = 0f;

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		timeSinceLastUpdate += delta;
		if (introtext == false) {
			countdownToCheezyLine -= delta;
		}
		
		if(!introtext && running){
			totalTime += delta;
		}
		
		if (countdownToCheezyLine <= 0) {
			countdownToCheezyLine = 10000f;
		}
		fade = Math.max((500f - timeSinceLastUpdate / 2) / 500f, 0f);
		if (flashValue > 0f) {
			flashValue = Math.max((500f - timeSinceLastUpdate) / 500f, 0f);
		}

		rotateValue += delta * rotateVelocity;

		if (updated) {
			if (sweetbool) {
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
		float[] harmonics = { 1.0f, 1.059f, 1.122f, 1.189f, 1.260f,/*
																	 * 1.335f,
																	 * 1.414f,
																	 * 1.498f,
																	 * 1.587f,
																	 * 1.682f,
																	 * 1.782f,
																	 * 1.888f,
																	 * 2.0f
																	 */};
		return harmonics[(int) Math.floor(Math.random() * harmonics.length)];
	}

	@Override
	public void keyPressed(int key, char c) {
		InputBuffer.Input i = InputBuffer.Input.NOMOVE;
		switch (key) {
		case org.newdawn.slick.Input.KEY_UP:
		case org.newdawn.slick.Input.KEY_W:
			i = InputBuffer.Input.UP;
			break;
		case org.newdawn.slick.Input.KEY_DOWN:
		case org.newdawn.slick.Input.KEY_S:
			i = InputBuffer.Input.DOWN;
			break;
		case org.newdawn.slick.Input.KEY_LEFT:
		case org.newdawn.slick.Input.KEY_A:
			i = InputBuffer.Input.LEFT;
			break;
		case org.newdawn.slick.Input.KEY_RIGHT:
		case org.newdawn.slick.Input.KEY_D:
			i = InputBuffer.Input.RIGHT;
			break;
		case org.newdawn.slick.Input.KEY_SPACE:
		case org.newdawn.slick.Input.KEY_ENTER:
			client.notifyGameStart();
			try {
				SOUND_SWEET = new Sound("res/boof.ogg");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SOUND_JAMS = SOUND_SWEET;
			introtext = false;
		default:
			break;
		}
		if (i != InputBuffer.Input.NOMOVE) {
			client.sendInput(i);
			getPlayer().getBuffer().addInput(i); // Local copy
		}

	}

	public void keyReleased(int key, char c) {
	}

	public void setPlayer(Player e) {
		myPlayer = e;
	}

	public Player getPlayer() {
		return myPlayer;
	}

	public void setRunning(boolean b) {
		running = b;
		if (b == false){
			SOUND_GAMEOVER.play();
		}
	}

	public void handleCollision(MinionType minionType) {
		if (minionType != MinionType.DEATH) {
			getRandomLine().play();
		}

		switch (minionType) {
		case ROTATE:
			if (rotateVelocity == 0.0f) {
				rotateVelocity = 0.01f;
			}
			rotateVelocity *= 1.1f;
			break;
		case FLASH:
			flashValue = 1.0f;
			break;
		case SCALE:
			scaleMin -= 0.1f;
		default:
			break;
		}
	}

}
