package com.minimanimals.slasher;

public class ElementState {

	int mX, mY;
	int mVariation;
	boolean mFaded;

	public ElementState(int x, int y, int variation, boolean faded) {
		mX = x;
		mY = y;

		mVariation = variation;

		mFaded = faded;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public boolean isFaded() {
		return mFaded;
	}

	public int getVariation() {
		return mVariation;
	}

}