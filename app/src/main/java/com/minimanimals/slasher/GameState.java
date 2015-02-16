package com.minimanimals.slasher;

import java.util.Random;
import java.util.Iterator;
import android.util.Log;

public class GameState {

	Random mRandom;

	ElementRenderer mElementRenderer;

	ElementState[][] mElementStates;

	GamePoint mStartStrokePoint;
	GamePoint mEndStrokePoint;

	public GameState(int cols, int rows, ElementRenderer elementRenderer) {
		mRandom = new Random();

		mElementRenderer = elementRenderer;

		mElementStates = new ElementState[cols][rows];
		randomizeState();
	}

	public void randomizeState() {
		for (int x = 0; x < mElementStates.length; x++) {
			for (int y = 0; y < mElementStates[x].length; y++) {
				mElementStates[x][y] = new ElementState(x, y, (int)(mRandom.nextDouble() * mElementRenderer.numVariations()), ElementState.Mode.NORMAL);
			}
		}
	}

	public ElementState getElementState(int x, int y) {
		return mElementStates[x][y];
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

					int x = 0, y = -1;

					public boolean hasNext() {
						return !(x == numCols() - 1 && y == numRows() - 1);
					}

					public ElementState next() {
						y++;
						if (y == numRows()) {
							x++;
							y = 0;
						}
						ElementState nextState = mElementStates[x][y];
						return nextState;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

	private int constrainX(int x) {
		return Math.min(Math.max(0, x), numCols());
	}

	private	int constrainY(int y) {
		return Math.min(Math.max(0, y), numRows());
	}

	private void setElementStateMode(ElementState.Mode mode) {
		for (ElementState state : iterElementStates()) {
			state.setMode(mode);
		}
	}

	public void setStartStrokePoint(GamePoint point) {
		if (mStartStrokePoint != null && point == null) setElementStateMode(ElementState.Mode.NORMAL);
		mStartStrokePoint = point;
	}

	public void setEndStrokePoint(GamePoint point) {
		mEndStrokePoint = point;
		if (mStartStrokePoint == null || mEndStrokePoint == null) return;

		setElementStateMode(ElementState.Mode.FADED);

		if (mStartStrokePoint.getY() == mEndStrokePoint.getY()) {
			int yPivot = constrainY((int)mStartStrokePoint.getY());

			int startX = constrainX((int)mStartStrokePoint.getX());
			int endX = constrainX((int)mEndStrokePoint.getX());

			for (int x = Math.min(startX, endX); x < Math.max(startX, endX); x++) {
				for (int y = 0; y < yPivot; y++) {
					if (yPivot+y >= numRows() || yPivot-y-1 < 0) break;

					ElementState above = mElementStates[x][yPivot+y];
					ElementState below = mElementStates[x][yPivot-y-1];

					if (mElementRenderer.isSymmetric(above.getVariation(), below.getVariation(), true)) {
						above.setMode(ElementState.Mode.HIGHLIGHTED);
						below.setMode(ElementState.Mode.HIGHLIGHTED);
					}
				}
			}
		} else if (mStartStrokePoint.getX() == mEndStrokePoint.getX()) {
			int xPivot = constrainX((int)mStartStrokePoint.getX());

			int startY = constrainY((int)mStartStrokePoint.getY());
			int endY = constrainY((int)mEndStrokePoint.getY());

			for (int y = Math.min(startY, endY); y < Math.max(startY, endY); y++) {
				for (int x = 0; x < xPivot; x++) {
					if (xPivot+x >= numCols() || xPivot-x-1 < 0) break;

					ElementState above = mElementStates[xPivot+x][y];
					ElementState below = mElementStates[xPivot-x-1][y];

					if (mElementRenderer.isSymmetric(above.getVariation(), below.getVariation(), true)) {
						above.setMode(ElementState.Mode.HIGHLIGHTED);
						below.setMode(ElementState.Mode.HIGHLIGHTED);
					}
				}
			}
		}
	}

	public GamePoint getStartStrokePoint() {
		return mStartStrokePoint;
	}

	public GamePoint getEndStrokePoint() {
		return mEndStrokePoint;
	}

}