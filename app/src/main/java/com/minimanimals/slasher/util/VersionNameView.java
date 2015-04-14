package com.minimanimals.util;

import android.content.Context;
import android.widget.TextView;
import android.content.pm.PackageInfo;
import android.util.AttributeSet;
import android.content.pm.PackageManager;
import android.util.Log;

public class VersionNameView extends TextView {

	public VersionNameView(Context context) {
		this(context, null);
	}

	public VersionNameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VersionNameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		try {
			PackageInfo info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
			setText("v"+info.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("slasher-util", "Couldn't get package info for VersionNameView", e);
		}
	}
}
