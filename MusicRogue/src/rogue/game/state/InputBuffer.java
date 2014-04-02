package rogue.game.state;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class InputBuffer {
	public static enum Input {
		UP, DOWN, LEFT, RIGHT, NOMOVE;
	}

	private Queue<Input> buffer;
	private Object[] calculatedArray; // for reading
	private boolean bufferChanged;

	public InputBuffer(int size) {
		buffer = new LinkedBlockingDeque<Input>(size);
		bufferChanged = true;
	}

	public synchronized Input readInput() {
		try {
			bufferChanged = true;
			Input in = buffer.remove();
			return in;
		} catch (NoSuchElementException e) {
			return Input.NOMOVE;
		}
	}

	public synchronized boolean addInput(Input input) {
		bufferChanged = true;
		return buffer.offer(input);
	}

	public Object[] toArray() {
		if (bufferChanged) {
			bufferChanged = false;
			synchronized (this) {
				calculatedArray = buffer.toArray();
			}
			return calculatedArray;
		} else {
			return calculatedArray;
		}
	}
}
