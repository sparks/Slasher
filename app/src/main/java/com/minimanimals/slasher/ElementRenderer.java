package com.minimanimals.slasher;

import android.graphics.Canvas;

public interface ElementRenderer {

	public void render(Canvas canvas, int variation, ElementState.Mode mode, int left, int top, int right, int bottom);

	public int numVariations();

	public void setMaxVariations(int max);

	public boolean isSymmetric(int varA, int varB, boolean vertical);

	public boolean isColorMatch(int varA, int varB, boolean vertical);

}