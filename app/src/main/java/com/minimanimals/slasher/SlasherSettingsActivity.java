package com.minimanimals.slasher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.TypedValue;

import com.minimanimals.slasher.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlasherSettingsActivity extends ActionBarActivity {

	@InjectView(R.id.back_button) View mBackButton;

	@InjectView(R.id.settings_container) ViewGroup mSettingsContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_slasher_settings);

		ButterKnife.inject(this);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				overridePendingTransition(0,0);
			}
		});

		for (final SlasherSettings setting : SlasherSettings.values()) {
			if (setting.type() == SlasherSettings.Type.INTEGER && setting.isUserSet()) {
				TextView tv = new TextView(this);
				tv.setText(setting.toString());

				mSettingsContainer.addView(tv);

				ViewGroup.LayoutParams lp = tv.getLayoutParams();
				lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

				NumberPicker picker = new NumberPicker(this);
				picker.setMinValue(0);
				picker.setMaxValue(10);
				picker.setValue(setting.getInt());

				picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
						setting.set(picker.getValue());
					}
				});

				picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

				mSettingsContainer.addView(picker);

				lp = picker.getLayoutParams();
				lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(0,0);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			);
		}
	}

}
