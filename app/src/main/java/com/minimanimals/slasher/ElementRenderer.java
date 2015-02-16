package com.minimanimals.slasher;

import android.graphics.Canvas;

public interface ElementRenderer {

	public void render(Canvas canvas, int variation, boolean faded, int left, int top, int right, int bottom);

	public int numVariations();

	public boolean isSymmetric(int varA, int varB, boolean vertical);

}