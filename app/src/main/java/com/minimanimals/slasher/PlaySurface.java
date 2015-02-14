package com.minimanimals.slasher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

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

		int[] colors = new int[] {
			0xff3959a8,
			0xff07b68e,
			0xffa7cf3b,
			0xffed1976
		};

		int numCols = 6;
		int numRows = 10;

		float padding = px(5);
		float colSize = (getWidth() - padding * 2) / numCols;
		float rowSize = (getHeight() - padding * 2) / numRows;

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(px(2));

		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				paint.setColor(colors[(int)(Math.random() * colors.length)]);
				canvas.drawRect(padding + colSize * (i + 0.1f), padding + rowSize * (j + 0.1f), padding + colSize * (i + 0.9f), padding + rowSize * (j + 0.9f), paint);
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

}
