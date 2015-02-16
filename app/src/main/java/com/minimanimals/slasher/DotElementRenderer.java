package com.minimanimals.slasher;

import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;

public class DotElementRenderer implements ElementRenderer {

	static final float mDotFrac = 0.2f;

	static final int[] mColors = new int[] {
		0xff3959a8,
		0xff07b68e,
		0xffa7cf3b,
		0xffed1976
	};

	public DotElementRenderer() {}

	public void render(Canvas canvas, int variation, boolean fade, int left, int top, int right, int bottom) {
		int colorIndex = variation;

		int width = right - left;
		int height = bottom - top;

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(mColors[colorIndex]);

		canvas.drawCircle(
			left + width / 2,
			top + height / 2,
			Math.min(width, height) * mDotFrac,
			paint
		);
	}

}