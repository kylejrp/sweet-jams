package rogue.game.client;

import rogue.entity.EnvironmentEntity;
import rogue.game.message.Message;
import rogue.map.GameMap;

public class MapViewerClient extends Client {

	@Override
	public void recieveMessage(Message msg) {
		EnvironmentEntity[][] envLayer;

		if (msg.getObject() instanceof GameMap
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			GameMap map = (GameMap) msg.getObject();
			envLayer = map.getEnvironmentLayer();
			for (int i = 0; i < envLayer.length; i++) {
				for (int j = 0; j < envLayer.length; j++) {
					System.out.print(envLayer[i][j].getCharRep());
				}
				System.out.println();
			}
		}
	}

	@Override
	public void run() {
		System.out.println("MapViewerClient started.");
	}

}
