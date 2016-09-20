package com.yueny.demo.exec.pair;

import com.yueny.demo.annotion.UnSafe;

@UnSafe
public class Pair {
	public class PairValuesNotEqualException extends RuntimeException {

		/**
		 *
		 */
		private static final long serialVersionUID = 654776056782858176L;

		public PairValuesNotEqualException() {
			super("Pair Values Not Equal :" + Pair.this);
		}

	}

	private int x, y;

	public Pair() {
		this(0, 0);
	}

	public Pair(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void checkStatus() {
		if (x != y) {
			throw new PairValuesNotEqualException();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void incrementX() {
		x++;
	}

	public void incrementY() {
		y++;
	}

	@Override
	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}
}
