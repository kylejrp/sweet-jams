package rogue.game;

public class Game implements Runnable {
	Screen currentScreen;
	
	public Game() {

	}

	@Override
	public void run() {
		while (true) {
			getInput();
			update();
			render();
		}
	}

	private void render() {
		currentScreen.render();
	}

	private void update() {
		currentScreen.update();
	}

	private void getInput() {
		// TODO Auto-generated method stub

	}

}
