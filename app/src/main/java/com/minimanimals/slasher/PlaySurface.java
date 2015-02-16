package com.minimanimals.slasher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;
import android.util.Log;
import com.minimanimal.slasher.util.ColorUtil;
import java.util.Random;

public class PlaySurface extends View {

	ElementRenderer mElementRenderer;

	int mNumCols;
	int mNumRows;

	int mVariations;

	float mColSize;
	float mRowSize;

	GamePoint mStartStrokePoint;
	GamePoint mEndStrokePoint;

	Random mRandom;

	public PlaySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlaySurface(Context context) {
		super(context);
	}

	public void init(int cols, int rows, int variations, ElementRenderer elementRenderer) {
		mNumCols = cols;
		mNumRows = rows;

		mVariations = variations;

		mElementRenderer = elementRenderer;

		mRandom = new Random();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
		mColSize = (newWidth - getPaddingLeft() - getPaddingRight()) / mNumCols;
		mRowSize = (newHeight - getPaddingTop() - getPaddingBottom()) / mNumRows;
	}

	private float px(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (getWidth() == 0 || getHeight() == 0) return;

		drawElements(canvas);
		drawStrokeUI(canvas);

	}

	void drawElements(Canvas canvas) {
		if (mElementRenderer != null) {
			for (int i = 0; i < mNumCols; i++) {
				for (int j = 0; j < mNumRows; j++) {
					GamePoint topLeftPoint = new GamePoint(i, j);
					GamePoint bottomRightPoint = new GamePoint(i+1, j+1);

					mElementRenderer.render(
						canvas,
						(int)(mRandom.nextDouble() * mVariations),
						false,
						topLeftPoint.getScreenX(),
						topLeftPoint.getScreenY(),
						bottomRightPoint.getScreenX(),
						bottomRightPoint.getScreenY()
					);
				}
			}
		}
	}

	void drawStrokeUI(Canvas canvas) {
		if (mStartStrokePoint != null) {
			int baseColor = 0xff414141;

			Paint linePaint = new Paint();
			linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
			linePaint.setStrokeWidth(px(3));
			linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			linePaint.setColor(baseColor);

			canvas.drawLine(
				mStartStrokePoint.getScreenX(),
				mStartStrokePoint.getScreenY(),
				mEndStrokePoint.getScreenX(),
				mEndStrokePoint.getScreenY(),
				linePaint
			);
			canvas.drawCircle(
				mStartStrokePoint.getScreenX(),
				mStartStrokePoint.getScreenY(),
				px(5),
				linePaint
			);

			linePaint.setColor(ColorUtil.applyAlpha(baseColor, 0x60));
			for (int i = 0; i < 3; i++) {
				canvas.drawCircle(
					mEndStrokePoint.getScreenX(),
					mEndStrokePoint.getScreenY(),
					px(5 * (3 - i)),
					linePaint
				);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		GamePoint point = gamePointfromMotionEvent(event);
		point.snapToGrid();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mStartStrokePoint = point;
				mEndStrokePoint = point;
				invalidate();
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				point.snapOrtho(mStartStrokePoint);
				if (!point.equals(mEndStrokePoint)) {
					mEndStrokePoint = point;
					invalidate();
				}
				break;
			}
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP: {
				mStartStrokePoint = null;
				invalidate();
				break;
			}
			default: {
				return false;
			}
		}

		return true;
	}

	private GamePoint gamePointfromScreenCoord(float x, float y) {
		x = (x - getPaddingLeft()) / mColSize;
		y = (y - getPaddingTop()) / mRowSize;

		return new GamePoint(x, y);
	}

	private GamePoint gamePointfromMotionEvent(MotionEvent event) {
		return gamePointfromScreenCoord(event.getX(), event.getY());
	}

	class GamePoint {
		float x, y;

		GamePoint(GamePoint point) {
			this.x = point.x;
			this.y = point.y;
		}

		private GamePoint(float x, float y) {
			this.x = x;
			this.y = y;
		}

		float getGameX() {
			return x;
		}

		float getGameY() {
			return y;
		}

		int getScreenX() {
			return (int)((mColSize * x) + getPaddingLeft());
		}

		int getScreenY() {
			return (int)((mRowSize * y) + getPaddingTop());
		}

		void snapToGrid() {
			x = Math.round(x);
			y = Math.round(y);
		}

		void snapOrtho(GamePoint reference) {
			float xDiff = Math.abs(reference.x - this.x);
			float yDiff = Math.abs(reference.y - this.y);

			if (xDiff > yDiff) this.y = reference.y;
			else this.x = reference.x;
		}

		public boolean equals(Object o) {
			if (o instanceof GamePoint) {
				GamePoint p = (GamePoint)o;
				if (this.x == p.x && this.y == p.y) return true;
			}
			return false;
		}

	}

}
