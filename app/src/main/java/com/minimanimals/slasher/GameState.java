package com.minimanimals.slasher;

import java.util.Random;

public class GameState {

	Random mRandom;

	int mVariations;
	int[][] mElementStates;

	public GameState(int cols, int rows, int variations) {
		mVariations = variations;

		mRandom = new Random();

		mElementStates = new int[cols][rows];
		randomizeState();
	}

	public void randomizeState() {
		for (int i = 0; i < mElementStates.length; i++) {
			for (int j = 0; j < mElementStates[i].length; j++) {
				mElementStates[i][j] = (int)(mRandom.nextDouble() * mVariations);
			}
		}
	}

	public int getElementState(int i, int j) {
		return mElementStates[i][j];
	}

	public int numCols() {
		return mElementStates.length;
	}

	public int numRows() {
		return mElementStates[0].length;
	}

}