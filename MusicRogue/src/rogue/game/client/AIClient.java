package rogue.game.client;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import rogue.entity.Entity;
import rogue.entity.badguys.Minion;
import rogue.entity.badguys.Minion.MinionType;
import rogue.entity.player.Player;
import rogue.game.message.Message;
import rogue.game.message.Message.MessageDetail;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;
import rogue.map.Position;

public class AIClient extends Client implements Runnable {
	private Random randomInt = new Random();
	private int inputDigit;
	private MinionType type;
	private Input nextInput;
	private GameMap map; // Only useful for map.getEnvironmentLayer();
	private List<Entity> entities; // List of all entities in the game
	private Minion myEntity;
	private boolean needToSendInput;
	private boolean running;

	public AIClient(MinionType type) {
		this.type = type;
		inputDigit = 0;
		running = true;
		new Thread(this).start();
	}

	@Override
	public void recieveMessage(Message msg) {
		nextInput = Input.NOMOVE;

		if (msg.getObject() instanceof GameMap
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			GameMap map = (GameMap) msg.getObject();
			this.map = map;
		} else if (msg.getObject() instanceof Entity
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			myEntity = (Minion) msg.getObject();
			type = myEntity.getMinionType();
		} else if (msg.getObject() instanceof List<?>
				&& msg.getDetail() == Message.MessageDetail.UPDATE) {
			entities = (List<Entity>) msg.getObject();
			needToSendInput = true;
		} else if (msg.getObject() instanceof Entry<?, ?>
		&& msg.getDetail() == MessageDetail.CREATE) {
			Client client = (Client) ((Entry) msg.getObject()).getKey();
			if (client.clientNumber == this.clientNumber) {
				myEntity = (Minion) (((Entry) msg.getObject()).getValue());
			}
		} 
		
		// TODO: Check for entity death

	}

	@Override
	public void run() {
		// Calculate next move (always)
		while (running) {
			if (needToSendInput) {
				nextInput = genMovement();
				sendInput(nextInput);
			}
		}

	}

	private Input genMovement() {
		Input[] inputSequence = { Input.UP, Input.RIGHT, Input.DOWN, Input.LEFT };
		switch (type) {
		case COLOR:
			// It's coming right for us! (runs right at player)
			for (int i = 0; i < inputSequence.length; i++) {
				if (findEuclideanDistance(myEntity.getPosition()) > findEuclideanDistance(Position
						.calcPosition(myEntity.getPosition(), inputSequence[i]))) {
					return inputSequence[i];
				}
			}
			break;
		case FLASH:
			// Handled elsewhere.
			return Input.NOMOVE;
		case ROTATE:
			// Walks in a nice little square path.
			return inputSequence[inputDigit++%inputSequence.length];
		case SCALE:
			// Handled elsewhere.
			return Input.NOMOVE;
		case DEATH:
			// Stumbles around randomly.
			int check = randomInt.nextInt(3);
			return inputSequence[check];
		default:
			break;
		}
		return Input.NOMOVE;
	}

	private double findEuclideanDistance(Position p) {
		Position playerPos = null;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Player) {
				playerPos = entities.get(i).getPosition();
			}
		}
		int x = (playerPos.getX() - p.getX()) * (playerPos.getX() - p.getX());
		int y = (playerPos.getY() - p.getY()) * (playerPos.getY() - p.getY());
		double distance = Math.sqrt(x + y);
		return distance;
	}
}
