package com.minimanimals.slasher.util;

import android.graphics.Color;
import android.util.Log;

public class ColorUtil {

	private ColorUtil() {}

	public static int applyAlpha(int color, int alpha) {
		int currentAlpha = ((color & 0xFF000000) >> 24) & 0xFF;
		int adjAlpha = (int)(currentAlpha * alpha);
		return (adjAlpha << 24) | (color & 0x00FFFFFF);
	}

	public static int desaturate(int color, float factor) {
		if (factor < 0) factor = 0;
		if (factor > 1) factor = 1;

		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);

		hsv[1] *= factor;

		color = Color.HSVToColor(hsv);

		return color;
	}

}