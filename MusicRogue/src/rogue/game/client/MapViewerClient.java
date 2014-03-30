package rogue.game.client;

import rogue.map.MapGenerator;
import rogue.entity.EnvironmentEntity;
import rogue.game.message.Message;

public class MapViewerClient extends Client {

	@Override
	public void recieveMessage(Message msg) {
		EnvironmentEntity[][] map;

		if (msg.getObject() instanceof EnvironmentEntity[][]
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			map = (EnvironmentEntity[][]) msg.getObject();
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map.length; j++) {
					System.out.print(map[i][j].getCharRep());
				}
				System.out.println();
			}
		}
	}

}
