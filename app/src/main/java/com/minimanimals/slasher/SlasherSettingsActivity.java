package com.minimanimals.slasher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import com.minimanimals.slasher.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlasherSettingsActivity extends ActionBarActivity {

	@InjectView(R.id.back_button) View mBackButton;
	@InjectView(R.id.var_cap_num_pick) NumberPicker mNumberPicker;
	@InjectView(R.id.slash_renderer) View mSlashRenderer;
	@InjectView(R.id.dot_renderer) View mDoRenderer;

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

		mNumberPicker.setMinValue(1);
		mNumberPicker.setMaxValue(10);
		mNumberPicker.setValue(SlasherSettings.VAR_CAP.getInt());

		mNumberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				SlasherSettings.VAR_CAP.set(mNumberPicker.getValue());
			}
		});

		mSlashRenderer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SlasherSettings.RENDERER.set("slashes");
			}
		});

		mDoRenderer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SlasherSettings.RENDERER.set("dots");
			}
		});
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
