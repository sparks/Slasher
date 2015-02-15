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
import java.util.Random;

public class PlaySurface extends View {

	int mStartX = -1;
	int mStartY = -1;

	int mEndX;
	int mEndY;

	public PlaySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlaySurface(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Try for a width based on our minimum
		// int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
		// int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

		// Whatever the width ends up being, ask for a height that would let the pie
		// get as big as it can
		// int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
		// int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	private float px(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (getWidth() == 0 || getHeight() == 0) return;

		int[] colors = new int[] {
			0xff3959a8,
			0xff07b68e,
			0xffa7cf3b,
			0xffed1976
		};

		int numCols = 6;
		int numRows = 10;

		float colSize = (getWidth() - getPaddingLeft() - getPaddingRight()) / numCols;
		float rowSize = (getHeight() - getPaddingTop() - getPaddingBottom()) / numRows;

		Paint slashPaint = new Paint();
		slashPaint.setStyle(Paint.Style.STROKE);
		slashPaint.setStrokeWidth(px(6));
		slashPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		float paddingFrac = 0.125f;

		Random random = new Random(123);

		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				boolean direction = random.nextDouble() > 0.5f;
				slashPaint.setColor(colors[(int)(random.nextDouble() * colors.length)]);
				if (direction) {
					canvas.drawLine(
						getPaddingLeft() + colSize * (i + paddingFrac),
						getPaddingTop() + rowSize * (j + paddingFrac),
						getPaddingLeft() + colSize * (i + (1 - paddingFrac)),
						getPaddingTop() + rowSize * (j + (1 - paddingFrac)),
						slashPaint
					);
				} else {
					canvas.drawLine(
						getPaddingLeft() + colSize * (i + paddingFrac),
						getPaddingTop() + rowSize * (j + (1 - paddingFrac)),
						getPaddingLeft() + colSize * (i + (1 - paddingFrac)),
						getPaddingTop() + rowSize * (j + paddingFrac),
						slashPaint
					);
				}
				// canvas.drawRect(
				// 	getPaddingLeft() + colSize * (i + 0.1f),
				// 	getPaddingTop() + rowSize * (j + 0.1f),
				// 	getPaddingLeft() + colSize * (i + 0.9f),
				// 	getPaddingTop() + rowSize * (j + 0.9f),
				// 	paint
				// );
			}
		}

		if (mStartX != -1 && mStartY != -1) {
			Paint linePaint = new Paint();
			linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
			linePaint.setStrokeWidth(px(3));
			linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			linePaint.setColor(0xff414141);

			canvas.drawLine(
				getPaddingLeft() + colSize * mStartX,
				getPaddingTop() + rowSize * mStartY,
				getPaddingLeft() + colSize * mEndX,
				getPaddingTop() + rowSize * mEndY,
				linePaint
			);
			canvas.drawCircle(
				getPaddingLeft() + colSize * mStartX,
				getPaddingTop() + rowSize * mStartY,
				px(5),
				linePaint
			);

			int baseColor = 0xff414141;

			linePaint.setColor(applyAlpha(baseColor, 0x60));
			canvas.drawCircle(
				getPaddingLeft() + colSize * mEndX,
				getPaddingTop() + rowSize * mEndY,
				px(15),
				linePaint
			);

			linePaint.setColor(applyAlpha(baseColor, 0x60));
			canvas.drawCircle(
				getPaddingLeft() + colSize * mEndX,
				getPaddingTop() + rowSize * mEndY,
				px(10),
				linePaint
			);


			linePaint.setColor(applyAlpha(baseColor, 0x60));
			canvas.drawCircle(
				getPaddingLeft() + colSize * mEndX,
				getPaddingTop() + rowSize * mEndY,
				px(5),
				linePaint
			);
		}
	}

	int applyAlpha(int color, int alpha) {
		int currentAlpha = ((color & 0xFF000000) >> 24) & 0xFF;
		int adjAlpha = (int)(currentAlpha * alpha);
		return (adjAlpha << 24) | (color & 0x00FFFFFF);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int touchX = (int)event.getX();
		int touchY = (int)event.getY();

		int numCols = 6;
		int numRows = 10;

		float colSize = (getWidth() - getPaddingLeft() - getPaddingRight()) / numCols;
		float rowSize = (getHeight() - getPaddingTop() - getPaddingBottom()) / numRows;

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mStartX = Math.round((float)(touchX - getPaddingLeft()) / colSize);
				mStartY = Math.round((float)(touchY - getPaddingTop()) / rowSize);
				mEndX = mStartX;
				mEndY = mStartY;
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				mEndX = Math.round((float)(touchX - getPaddingLeft()) / colSize);
				mEndY = Math.round((float)(touchY - getPaddingTop()) / rowSize);
				break;
			}
			case MotionEvent.ACTION_UP: {
				mStartX = -1;
				mStartY = -1;

				// drawCanvas.drawPath(drawPath, drawPaint);
				// drawPath.reset();
				break;
			}
			case MotionEvent.ACTION_CANCEL: {
				// drawPath.lineTo(touchX, touchY);
				break;
			}

			default: {
				return false;
			}
		}

		invalidate();
		return true;
	}


}
