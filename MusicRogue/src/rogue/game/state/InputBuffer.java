package rogue.game.state;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class InputBuffer {
	public static enum Input {
		UP, DOWN, LEFT, RIGHT, NOMOVE;
	}

	private Queue<Input> buffer;

	public InputBuffer(int size) {
		buffer = new LinkedBlockingDeque<Input>(size);
	}

	public synchronized Input readInput() {
		try {
			return buffer.remove();
		} catch (NoSuchElementException e) {
			return Input.NOMOVE;
		}
	}

	public synchronized boolean addInput(Input input) {
		return buffer.offer(input);
	}
}
