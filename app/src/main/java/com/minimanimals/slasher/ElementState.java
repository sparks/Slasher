package com.minimanimals.slasher;

public class ElementState {

	public enum Mode {
		FADED,
		NORMAL,
		HIGHLIGHTED;
	}

	int mX, mY;
	int mVariation;
	Mode mMode;

	public ElementState(int x, int y, int variation, Mode mode) {
		mX = x;
		mY = y;

		mVariation = variation;

		mMode = mode;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public boolean isFaded() {
		return mMode == Mode.FADED;
	}

	public boolean isHighlighted() {
		return mMode == Mode.HIGHLIGHTED;
	}

	public Mode getMode() {
		return mMode;
	}

	public void setMode(Mode mode) {
		mMode = mode;
	}

	public int getVariation() {
		return mVariation;
	}

}