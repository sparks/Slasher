package com.minimanimals.slasher;

import android.graphics.Paint;
import android.graphics.Canvas;
import com.minimanimals.slasher.util.ColorUtil;

public class DotElementRenderer implements ElementRenderer {

	static final float mDotFrac = 0.2f;

	static final int[] mColors = new int[] {
		0xff3959a8,
		0xff07b68e,
		0xffa7cf3b,
		0xffed1976
	};

	public DotElementRenderer() {}

	public int numVariations() {
		return mColors.length;
	}

	public boolean isSymmetric(int varA, int varB, boolean vertical) {
		return varA == varB;
	}

	public void render(Canvas canvas, int variation, ElementState.Mode mode, int left, int top, int right, int bottom) {
		int colorIndex = variation;

		int color = mColors[colorIndex];
		if (mode == ElementState.Mode.FADED) color = ColorUtil.desaturate(color, 0.1f);

		int width = right - left;
		int height = bottom - top;

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);

		canvas.drawCircle(
			left + width / 2,
			top + height / 2,
			Math.min(width, height) * mDotFrac,
			paint
		);
	}

}