package com.minimanimal.slasher.util;

public class ColorUtil {

	private ColorUtil() {}

	public static int applyAlpha(int color, int alpha) {
		int currentAlpha = ((color & 0xFF000000) >> 24) & 0xFF;
		int adjAlpha = (int)(currentAlpha * alpha);
		return (adjAlpha << 24) | (color & 0x00FFFFFF);
	}

}