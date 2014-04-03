package rogue.game.client;

import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import rogue.entity.Entity;
import rogue.game.MapRenderer;
import rogue.game.message.Message;
import rogue.game.message.Message.MessageDetail;
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
		} else if (msg.getObject() instanceof Entry<?, ?> && msg.getDetail() == MessageDetail.CREATE){
			Client client = (Client)((Entry) msg.getObject()).getKey();
			if (client.clientNumber == this.clientNumber){
				renderer.setEntity((Entity)(((Entry) msg.getObject()).getValue()));
			}
			
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