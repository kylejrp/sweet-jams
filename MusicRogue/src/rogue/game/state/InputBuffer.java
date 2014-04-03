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
	private Input lastMove;

	public InputBuffer(int size) {
		buffer = new LinkedBlockingDeque<Input>(size);
		bufferChanged = true;
		lastMove = Input.NOMOVE;
	}

	public synchronized Input readInput() {
		try {
			bufferChanged = true;
			lastMove = buffer.remove();
			return lastMove;
		} catch (NoSuchElementException e) {
			return lastMove;
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
				if(calculatedArray.length == 0){
					calculatedArray = new Object[1];
					calculatedArray[0] = lastMove;
				}
			}
			return calculatedArray;
		} else {
			return calculatedArray;
		}
	}
}
