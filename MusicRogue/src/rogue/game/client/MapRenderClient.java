package rogue.game.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
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
import rogue.game.MapRenderer;
import rogue.game.MusicRogueGame;
import rogue.game.message.Message;
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
		}
	}

	@Override
	public void run() {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(renderer);
			appgc.setDisplayMode(512, 512, false);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}