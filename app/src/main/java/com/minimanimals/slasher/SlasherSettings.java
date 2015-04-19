package com.minimanimals.slasher;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Application;

/**
 * Utility class to work with Minuum preferences.
 * Other classes may subscribe to changes in the preferences settings.
 */
public enum SlasherSettings {

	RENDERER("renderer", 0, true),
	VAR_CAP("var_cap", 4, true),
	SYMM_DIST("symm_dist", 1, true),
	CONN_SYMM("conn_symm", 0, true),
	X_SIZE("x_size", 5, true),
	Y_SIZE("y_size", 8, true),
	CURRENT_VERSION_CODE("current_version_code", -1, false);

	private static Application mApplication;

	final String mKey;

	final Type mType;

	final int mDefaultIntValue;
	final String mDefaultStringValue;
	final boolean mDefaultBooleanValue;

	final boolean mUserSet;

	SlasherSettings(String key, String defaultValue, boolean userSet) {
		this(Type.STRING, key, userSet, 0, defaultValue, false);
	}

	SlasherSettings(String key, int defaultValue, boolean userSet) {
		this(Type.INTEGER, key, userSet, defaultValue, null, false);
	}

	SlasherSettings(String key, boolean defaultValue, boolean userSet) {
		this(Type.BOOL, key, userSet, 0, null, defaultValue);
	}

	SlasherSettings(Type type, String key, boolean userSet, int defaultInt, String defaultString, boolean defaultBoolean) {
		mType = type;
		mKey = key;
		mUserSet = userSet;

		mDefaultIntValue = defaultInt;
		mDefaultStringValue = defaultString;
		mDefaultBooleanValue = defaultBoolean;
	}

	public String key() {
		return mKey;
	}

	public Type type() {
		return mType;
	}

	public void set(int value) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mApplication).edit();
		editor.putInt(key(), value);
		editor.apply();
	}

	public void set(String value) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mApplication).edit();
		editor.putString(key(), value);
		editor.apply();
	}

	public void set(boolean value) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mApplication).edit();
		editor.putBoolean(key(), value);
		editor.apply();
	}

	public boolean getBool() {
		return PreferenceManager.getDefaultSharedPreferences(mApplication).getBoolean(key(), mDefaultBooleanValue);
	}

	public String getString() {
		return PreferenceManager.getDefaultSharedPreferences(mApplication).getString(key(), mDefaultStringValue);
	}

	public int getInt() {
		return PreferenceManager.getDefaultSharedPreferences(mApplication).getInt(key(), mDefaultIntValue);
	}

	public boolean isUserSet() {
		return mUserSet;
	}

	public void reset() {
		switch (mType) {
			case INTEGER: {
				set(mDefaultIntValue);
				break;
			}
			case STRING: {
				set(mDefaultStringValue);
				break;
			}
			case BOOL: {
				set(mDefaultBooleanValue);
				break;
			}
		}
	}

	public enum Type {
		BOOL, INTEGER, STRING;
	}

	public static void initialise(Application app) {
		mApplication = app;
	}

	public static void resetUser() {
		for (SlasherSettings setting : SlasherSettings.values()) {
			if (setting.isUserSet()) setting.reset();
		}
	}

	public static void resetAll() {
		for (SlasherSettings setting : SlasherSettings.values()) {
			setting.reset();
		}
	}

}
