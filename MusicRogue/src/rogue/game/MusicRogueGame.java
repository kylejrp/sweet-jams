package rogue.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import rogue.game.state.PlayState;

public class MusicRogueGame extends StateBasedGame {
	public MusicRogueGame(String title) {
		super(title);
	}	

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new PlayState());
	}
}
