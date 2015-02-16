package com.minimanimals.slasher;

import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;

public class SlashElementRenderer implements ElementRenderer {

	static final float mPaddingFrac = 0.125f;

	static final int[] mSlashColors = new int[] {
		0xff3959a8,
		0xff07b68e,
		0xffa7cf3b,
		0xffed1976
	};

	public SlashElementRenderer() {}

	public void render(Canvas canvas, int variation, boolean fade, int left, int top, int right, int bottom) {
		boolean direction = (variation % 2) == 0;
		int slashColorIndex = variation / 2;

		int width = right - left;
		int height = bottom - top;

		Paint slashPaint = new Paint();
		slashPaint.setStyle(Paint.Style.STROKE);
		slashPaint.setStrokeWidth(0.1f * width);
		slashPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		slashPaint.setColor(mSlashColors[slashColorIndex]);

		if (direction) {
			canvas.drawLine(
				left + width * mPaddingFrac,
				top + height * mPaddingFrac,
				right - width * mPaddingFrac,
				bottom - height * mPaddingFrac,
				slashPaint
			);
		} else {
			canvas.drawLine(
				left + width * mPaddingFrac,
				bottom - height * mPaddingFrac,
				right - width * mPaddingFrac,
				top + height * mPaddingFrac,
				slashPaint
			);
		}
	}

}