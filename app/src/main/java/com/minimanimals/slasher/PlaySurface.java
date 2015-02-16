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

	float mColSize;
	float mRowSize;

	GamePoint mStartStrokePoint;
	GamePoint mEndStrokePoint;

	GameState mGameState;

	public PlaySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlaySurface(Context context) {
		super(context);
	}

	public void init(GameState gameState, ElementRenderer elementRenderer) {
		mGameState = gameState;

		mElementRenderer = elementRenderer;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
		mColSize = (newWidth - getPaddingLeft() - getPaddingRight()) / mGameState.numCols();
		mRowSize = (newHeight - getPaddingTop() - getPaddingBottom()) / mGameState.numRows();
	}

	private float px(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (getWidth() == 0 || getHeight() == 0) return;

		drawElements(canvas);
		drawStrokeUI(canvas);
	}

	void drawElements(Canvas canvas) {
		if (mElementRenderer != null) {
			for (ElementState state : mGameState.iterElementStates()) {
				GamePoint topLeftPoint = new GamePoint(state.getX(), state.getY());
				GamePoint bottomRightPoint = new GamePoint(state.getX()+1, state.getY()+1);

				mElementRenderer.render(
					canvas,
					state.getVariation(),
					state.isFaded(),
					gamePointToScreenX(topLeftPoint),
					gamePointToScreenY(topLeftPoint),
					gamePointToScreenX(bottomRightPoint),
					gamePointToScreenY(bottomRightPoint)
				);
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
				gamePointToScreenX(mStartStrokePoint),
				gamePointToScreenY(mStartStrokePoint),
				gamePointToScreenX(mEndStrokePoint),
				gamePointToScreenY(mEndStrokePoint),
				linePaint
			);

			canvas.drawCircle(
				gamePointToScreenX(mStartStrokePoint),
				gamePointToScreenY(mStartStrokePoint),
				px(5),
				linePaint
			);

			canvas.drawCircle(
				gamePointToScreenX(mEndStrokePoint),
				gamePointToScreenY(mEndStrokePoint),
				px(5),
				linePaint
			);
			linePaint.setColor(ColorUtil.applyAlpha(baseColor, 0x60));
			for (int i = 0; i < 3; i++) {
				canvas.drawCircle(
					gamePointToScreenX(mEndStrokePoint),
					gamePointToScreenY(mEndStrokePoint),
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

	private int gamePointToScreenX(GamePoint point) {
		return (int)((mColSize * point.getX()) + getPaddingLeft());

	}

	private int gamePointToScreenY(GamePoint point) {
		return (int)((mRowSize * point.getY()) + getPaddingTop());
	}

}
