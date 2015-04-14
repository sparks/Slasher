package com.minimanimals.slasher;

import android.content.pm.PackageInfo;
import android.app.Application;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class SlasherApplication extends Application {


	@Override
	public void onCreate() {
		super.onCreate();

		SlasherSettings.initialise(this);

		// Now that everything is setup we do upgrades

		int oldVersion = SlasherSettings.CURRENT_VERSION_CODE.getInt();
		int newVersion = 0;

		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			newVersion = info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("slasher-app", "Couldn't get package name", e);
		}

		if (oldVersion < 0) onApplicationInstall();
		else onApplicationUpdate(oldVersion, newVersion);

		SlasherSettings.CURRENT_VERSION_CODE.set(newVersion);
	}


	void onApplicationInstall() {
	}

	void onApplicationUpdate(int oldVersion, int newVersion) {
		if (oldVersion >= newVersion) return;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
