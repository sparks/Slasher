package com.minimanimals.slasher;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class ElementState {

	public enum Mode {
		FADED,
		NORMAL,
		HIGHLIGHTED;
	}

	float mActualX, mActualY;
	int mX, mY;
	int mVariation;
	Mode mMode;

	ValueAnimator mAnimatorX, mAnimatorY;

	public ElementState(int x, int y, int variation, Mode mode) {
		mX = x;
		mY = y;

		mActualX = mX;
		mActualY = mY;

		mVariation = variation;

		mMode = mode;
	}

	public float getActualX() {
		return mActualX;
	}

	public float getActualY() {
		return mActualY;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void setXY(int x, int y) {
		if (mAnimatorX != null) mAnimatorX.cancel();
		if (mAnimatorY != null) mAnimatorY.cancel();

		mActualX = mX;
		mActualY = mY;

		mX = x;
		mY = y;

		if (mX != mActualX) {
			mAnimatorX = ValueAnimator.ofFloat(mActualX, mX);
			mAnimatorX.setDuration((int)(Math.abs(mActualX - mX) * 200));
			// mAnimatorX.setInterpolator(new LinearInterpolator());
			mAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					mActualX = ((Number)animation.getAnimatedValue()).floatValue();
				}
			});
			mAnimatorX.start();
		}

		if (mY != mActualY) {
			mAnimatorY = ValueAnimator.ofFloat(mActualY, mY);
			mAnimatorY.setDuration((int)(Math.abs(mActualY - mY) * 200));
			// mAnimatorY.setInterpolator(new LinearInterpolator());
			mAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					mActualY = ((Number)animation.getAnimatedValue()).floatValue();
				}
			});
			mAnimatorY.start();
		}
	}

	public boolean isAnimating() {
		return (mAnimatorX != null && mAnimatorX.isRunning()) || (mAnimatorY != null && mAnimatorY.isRunning());
	}

	public Mode getMode() {
		return mMode;
	}

	public void setMode(Mode mode) {
		mMode = mode;
	}

	public int getVariation() {
		return mVariation;
	}

}