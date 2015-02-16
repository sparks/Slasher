package com.minimanimals.slasher;

import java.util.Random;
import java.util.Iterator;

public class GameState {

	Random mRandom;

	ElementRenderer mElementRenderer;

	ElementState[][] mElementStates;

	public GameState(int cols, int rows, ElementRenderer elementRenderer) {
		mRandom = new Random();

		mElementRenderer = elementRenderer;

		mElementStates = new ElementState[cols][rows];
		randomizeState();
	}

	public void randomizeState() {
		for (int i = 0; i < mElementStates.length; i++) {
			for (int j = 0; j < mElementStates[i].length; j++) {
				mElementStates[i][j] = new ElementState(i, j, (int)(mRandom.nextDouble() * mElementRenderer.numVariations()), false);
			}
		}
	}

	public ElementState getElementState(int i, int j) {
		return mElementStates[i][j];
	}

	public int numCols() {
		return mElementStates.length;
	}

	public int numRows() {
		return mElementStates[0].length;
	}

	public Iterable<ElementState> iterElementStates() {
		return new Iterable() {
			public Iterator<ElementState> iterator() {
				return new Iterator<ElementState>() {

					int i = 0, j = -1;

					public boolean hasNext() {
						return !(i == numCols() - 1 && j == numRows() - 1);
					}

					public ElementState next() {
						j++;
						if (j == numRows()) {
							i++;
							j = 0;
						}
						ElementState nextState = mElementStates[i][j];
						return nextState;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

}