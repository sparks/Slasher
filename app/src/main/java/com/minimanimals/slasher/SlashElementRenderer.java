package com.minimanimals.slasher;

import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;
import com.minimanimals.slasher.util.ColorUtil;

public class SlashElementRenderer implements ElementRenderer {

	static final float mPaddingFrac = 0.125f;

	static final int[] mColors = new int[] {
		0xff3959a8,
		0xff07b68e,
		0xffa7cf3b,
		0xffed1976,
	};

	int mMaxVariations = mColors.length * 2;

	public SlashElementRenderer() {}

	public void setMaxVariations(int max) {
		mMaxVariations = max;
	}

	public int numVariations() {
		return Math.min(mColors.length * 2, mMaxVariations);
	}

	public boolean isSymmetric(int varA, int varB, boolean vertical) {
		if (varA > varB) {
			int tmp = varB;
			varB = varA;
			varA = tmp;
		}

		return (varA % 2) == 0 && varB == varA + 1;
	}

	public boolean isColorMatch(int varA, int varB, boolean vertical) {
		return varA / 2 == varB / 2;
	}

	public void render(Canvas canvas, int variation, ElementState.Mode mode, int left, int top, int right, int bottom) {
		boolean direction = (variation % 2) == 0;
		int colorIndex = variation / 2;

		int color = mColors[colorIndex];
		if (mode == ElementState.Mode.FADED) color = ColorUtil.desaturate(color, 0.66f);

		float strokeSize = 0.1f;
		if (mode == ElementState.Mode.HIGHLIGHTED) strokeSize = 0.2f;

		int width = right - left;
		int height = bottom - top;

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(strokeSize * width);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);

		if (direction) {
			canvas.drawLine(
				left + width * mPaddingFrac,
				top + height * mPaddingFrac,
				right - width * mPaddingFrac,
				bottom - height * mPaddingFrac,
				paint
			);
		} else {
			canvas.drawLine(
				left + width * mPaddingFrac,
				bottom - height * mPaddingFrac,
				right - width * mPaddingFrac,
				top + height * mPaddingFrac,
				paint
			);
		}
	}

}