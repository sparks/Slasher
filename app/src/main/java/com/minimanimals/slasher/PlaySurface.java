package com.minimanimals.slasher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;

public class PlaySurface extends View {

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

		// Paint bgPaint = new Paint();
		// bgPaint.setStyle(Paint.Style.FILL);
		// bgPaint.setColor(Color.WHITE);

		// canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);

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

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(px(6));
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		float paddingFrac = 0.125f;

		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				boolean direction = Math.random() > 0.5f;
				paint.setColor(colors[(int)(Math.random() * colors.length)]);
				if (direction) {
					canvas.drawLine(
						getPaddingLeft() + colSize * (i + paddingFrac),
						getPaddingTop() + rowSize * (j + paddingFrac),
						getPaddingLeft() + colSize * (i + (1 - paddingFrac)),
						getPaddingTop() + rowSize * (j + (1 - paddingFrac)),
						paint
					);
				} else {
					canvas.drawLine(
						getPaddingLeft() + colSize * (i + paddingFrac),
						getPaddingTop() + rowSize * (j + (1 - paddingFrac)),
						getPaddingLeft() + colSize * (i + (1 - paddingFrac)),
						getPaddingTop() + rowSize * (j + paddingFrac),
						paint
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

		// Draw the shadow
		// canvas.drawOval(
		// 	mShadowBounds,
		// 	mShadowPaint
		// );

		// Draw the label text
		// canvas.drawText(mData.get(mCurrentItem).mLabel, mTextX, mTextY, mTextPaint);

		// Draw the pie slices
		// for (int i = 0; i < mData.size(); ++i) {
		// 	Item it = mData.get(i);
		// 	mPiePaint.setShader(it.mShader);
		// 	canvas.drawArc(
		// 		mBounds,
		// 		360 - it.mEndAngle,
		// 		it.mEndAngle - it.mStartAngle,
		// 		true,
		// 		mPiePaint
		// 	);
		// }

		// Draw the pointer
		// canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
		// canvas.drawCircle(mPointerX, mPointerY, mPointerSize, mTextPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				// drawPath.moveTo(touchX, touchY);
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				// drawPath.lineTo(touchX, touchY);
				break;
			}
			case MotionEvent.ACTION_UP: {
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
