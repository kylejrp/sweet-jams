package rogue.game.client;

import java.util.List;
import java.util.Map.Entry;

import rogue.game.message.Message;
import rogue.game.message.Message.MessageDetail;
import rogue.game.state.InputBuffer.Input;
import rogue.map.GameMap;
import rogue.entity.Entity;
import rogue.entity.badguys.Minion;
import rogue.entity.badguys.Minion.MinionType;

public class AIClient extends Client implements Runnable {
	private MinionType type;
	private Input nextInput;
	private GameMap map; // Only useful for map.getEnvironmentLayer();
	private List<Entity> entities; // List of all entities in the game
	private Minion myEntity;

	public AIClient(MinionType type) {
		this.type = type;
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
		} else if (msg.getObject() instanceof Entry<?, ?>
				&& msg.getDetail() == MessageDetail.CREATE) {
			Client client = (Client) ((Entry) msg.getObject()).getKey();
			if (client.clientNumber == this.clientNumber) {
				myEntity = (Minion) (((Entry) msg.getObject()).getValue());
			}
		}
	}

	@Override
	public void run() {
		// Calculate next move (always)
	}

}
