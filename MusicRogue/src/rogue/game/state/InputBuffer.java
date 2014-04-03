package rogue.game.state;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class InputBuffer {
	public static enum Input {
		UP, DOWN, LEFT, RIGHT, NOMOVE;
	}

	private Queue<Input> buffer;
	private Object[] calculatedArray; // for reading
	private boolean bufferChanged;
	private Input lastMove;

	public InputBuffer(int size) {
		buffer = new ArrayBlockingQueue<Input>(size);
		bufferChanged = true;
		lastMove = Input.NOMOVE;
	}

	public InputBuffer(InputBuffer inBuff) {
		if (inBuff.buffer.size() != 0) {
			this.buffer = new ArrayBlockingQueue<Input>(inBuff.buffer.size());
		} else {
			this.buffer = new ArrayBlockingQueue<Input>(1);
		}
		bufferChanged = true;
		lastMove = Input.NOMOVE;
		for (Input i : inBuff.buffer) {
			buffer.add(i);
		}
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
				if (calculatedArray.length == 0) {
					calculatedArray = new Object[1];
					calculatedArray[0] = Input.NOMOVE;
				}
			}
			return calculatedArray;
		} else {
			return calculatedArray;
		}
	}
}
