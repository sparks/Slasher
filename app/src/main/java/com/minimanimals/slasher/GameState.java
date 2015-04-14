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

	int mScore;

	ScoreChangeListener mScoreChangeListener;

	public GameState(int cols, int rows, ElementRenderer elementRenderer) {
		mRandom = new Random();

		mElementRenderer = elementRenderer;

		mElementStates = new ElementState[cols][rows];
		randomizeState();
	}

	public void setScoreChangeListener(ScoreChangeListener listener) {
		mScoreChangeListener = listener;
	}

	void addPoints(int points) {
		mScore += points;

		if (mScoreChangeListener != null) mScoreChangeListener.scoreChange(mScore);
	}

	public int getScore() {
		return mScore;
	}

	public void resetScore() {
		mScore = 0;

		if (mScoreChangeListener != null) mScoreChangeListener.scoreChange(mScore);
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
		return new Iterable<ElementState>() {
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
						return mElementStates[x][y];
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

	private int constrainX(int x, boolean mode) {
		if (mode) return Math.min(Math.max(0, x), numCols() - 1);
		else return Math.min(Math.max(0, x), numCols());
	}

	private	int constrainY(int y, boolean mode) {
		if (mode) return Math.min(Math.max(0, y), numRows() - 1);
		else return Math.min(Math.max(0, y), numRows());
	}

	private void setElementStateMode(ElementState.Mode mode) {
		for (ElementState state : iterElementStates()) {
			state.setMode(mode);
		}
	}

	public void endStroke() {
		if (mStartStrokePoint == null || mEndStrokePoint == null) return;

		for (int x = 0; x < mElementStates.length; x++) {
			for (int y = 0; y < mElementStates[x].length; y++) {
				if (mElementStates[x][y].getMode() == ElementState.Mode.HIGHLIGHTED) {
					addPoints(1);
					mElementStates[x][y] = null;
				}
			}
		}

		if (mStartStrokePoint.getY() == mEndStrokePoint.getY()) {
			int startX = (int)mEndStrokePoint.getX();
			int endX = (int)mStartStrokePoint.getX();
			if (startX != endX) {
				int incr = (endX-startX)/Math.abs(endX-startX);
				for (int y = 0; y < mElementStates[0].length; y++) {
					int numNewStates = 0;
					for (int x = constrainX(startX, true); x >= 0 && x < mElementStates.length; x += incr) {
						if (mElementStates[x][y] == null) {
							ElementState prevState = null;
							int prevX = x + incr;
							while (prevState == null && prevX >= 0 && prevX < mElementStates.length) {
								if (mElementStates[prevX][y] != null) {
									prevState = mElementStates[prevX][y];
									mElementStates[prevX][y] = null;
								}
								prevX += incr;
							}
							if (prevState == null) {
								prevState = new ElementState(prevX + numNewStates * incr, y, (int)(mRandom.nextDouble() * mElementRenderer.numVariations()), ElementState.Mode.NORMAL);
								numNewStates++;
							}

							prevState.setXY(x, y);
							mElementStates[x][y] = prevState;
						}
					}
				}
			}
		} else if (mStartStrokePoint.getX() == mEndStrokePoint.getX()) {
			int startY = (int)mEndStrokePoint.getY();
			int endY = (int)mStartStrokePoint.getY();
			if (startY != endY) {
				int incr = (endY-startY)/Math.abs(endY-startY);
				for (int x = 0; x < mElementStates.length; x++) {
					int numNewStates = 0;
					for (int y = constrainY(startY, true); y >= 0 && y < mElementStates[0].length; y += incr) {
						if (mElementStates[x][y] == null) {
							ElementState prevState = null;
							int prevY = y + incr;
							while (prevState == null && prevY >= 0 && prevY < mElementStates[x].length) {
								if (mElementStates[x][prevY] != null) {
									prevState = mElementStates[x][prevY];
									mElementStates[x][prevY] = null;
								}
								prevY += incr;
							}
							if (prevState == null) {
								prevState = new ElementState(x, prevY + numNewStates * incr, (int)(mRandom.nextDouble() * mElementRenderer.numVariations()), ElementState.Mode.NORMAL);
								numNewStates++;
							}

							prevState.setXY(x, y);
							mElementStates[x][y] = prevState;
						}
					}
				}
			}
		}

		mStartStrokePoint = null;
		mEndStrokePoint = null;

		setElementStateMode(ElementState.Mode.NORMAL);
	}

	public void setStartStrokePoint(GamePoint point) {
		mStartStrokePoint = point;
	}

	public void setEndStrokePoint(GamePoint point) {
		mEndStrokePoint = point;
		if (mStartStrokePoint == null || mEndStrokePoint == null) return;

		setElementStateMode(ElementState.Mode.FADED);

		final int symmetryMaxDis = 1;

		if (mStartStrokePoint.getY() == mEndStrokePoint.getY()) {
			int yPivot = constrainY((int)mStartStrokePoint.getY(), true);

			int startX = constrainX((int)mStartStrokePoint.getX(), false);
			int endX = constrainX((int)mEndStrokePoint.getX(), false);

			for (int x = Math.min(startX, endX); x < Math.max(startX, endX); x++) {
				for (int y = 0; y < Math.min(yPivot, symmetryMaxDis); y++) {
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
			int xPivot = constrainX((int)mStartStrokePoint.getX(), true);

			int startY = constrainY((int)mStartStrokePoint.getY(), false);
			int endY = constrainY((int)mEndStrokePoint.getY(), false);

			for (int y = Math.min(startY, endY); y < Math.max(startY, endY); y++) {
				for (int x = 0; x < Math.min(xPivot, symmetryMaxDis); x++) {
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

	public interface ScoreChangeListener {
		public void scoreChange(int score);
	}

}