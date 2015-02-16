package com.minimanimals.slasher;

import android.graphics.Canvas;

public interface ElementRenderer {

	public void render(Canvas canvas, int variation, boolean fade, int left, int top, int right, int bottom);

}