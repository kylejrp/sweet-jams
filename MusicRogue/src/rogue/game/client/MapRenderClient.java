package rogue.game.client;

import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import rogue.entity.Entity;
import rogue.entity.badguys.Minion;
import rogue.entity.player.Player;
import rogue.game.MapRenderer;
import rogue.game.message.Message;
import rogue.game.message.Message.MessageDetail;
import rogue.game.state.GameState;
import rogue.map.GameMap;

public class MapRenderClient extends Client {
	private MapRenderer renderer;

	public MapRenderClient(MapRenderer renderer) {
		this.renderer = renderer;
		renderer.setClient(this);
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
			for(Entity e : entLayer){
				if(e.equals(renderer.getPlayer())){
					renderer.setPlayer((Player) e);
				}
			}
			
		} else if (msg.getObject() instanceof Entry<?, ?> && msg.getDetail() == MessageDetail.CREATE){
			Client client = (Client)((Entry) msg.getObject()).getKey();
			if (client.clientNumber == this.clientNumber){
				renderer.setPlayer((Player)(((Entry) msg.getObject()).getValue()));
			}
			
		} else if (msg.getObject() instanceof GameState && msg.getDetail() == MessageDetail.DESTROY){
			renderer.setRunning(false);
		} else if (msg.getObject() instanceof Minion && msg.getDetail() == MessageDetail.DESTROY){
			renderer.handleCollision(((Minion) msg.getObject()).getMinionType());
		}
	}

	@Override
	public void run() {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(renderer);
			appgc.setDisplayMode(640, 640, false);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}