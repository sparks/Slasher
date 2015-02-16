package com.minimanimals.slasher;

public class GamePoint {

	float mX, mY;

	public GamePoint(GamePoint point) {
		mX = point.getX();
		mY = point.getY();
	}

	public GamePoint(float x, float y) {
		mX = x;
		mY = y;
	}

	public float getX() {
		return mX;
	}

	public float getY() {
		return mY;
	}

	public void snapToGrid() {
		mX = Math.round(mX);
		mY = Math.round(mY);
	}

	public void snapOrtho(GamePoint reference) {
		float xDiff = Math.abs(reference.getX() - mX);
		float yDiff = Math.abs(reference.getY() - mY);

		if (xDiff > yDiff) mY = reference.getY();
		else mX = reference.getX();
	}

	public boolean equals(Object o) {
		if (o instanceof GamePoint) {
			GamePoint p = (GamePoint)o;
			if (mX == p.getX() && mY == p.getY()) return true;
		}
		return false;
	}

}