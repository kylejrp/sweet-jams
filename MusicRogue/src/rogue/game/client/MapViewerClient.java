package rogue.game.client;

import rogue.map.MapGenerator;
import rogue.entity.EnvironmentEntity;
import rogue.game.message.Message;

public class MapViewerClient extends Client {

	@Override
	public void recieveMessage(Message msg) {
		EnvironmentEntity[][] envLayer;

		if (msg.getObject() instanceof EnvironmentEntity[][]
				&& msg.getDetail() == Message.MessageDetail.CREATE) {
			envLayer = (EnvironmentEntity[][]) msg.getObject();
			for (int i = 0; i < envLayer.length; i++) {
				for (int j = 0; j < envLayer.length; j++) {
					System.out.print(envLayer[i][j].getCharRep());
				}
				System.out.println();
			}
		}
	}

}
