package com.minimanimals.slasher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.minimanimals.slasher.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlasherSettingsActivity extends ActionBarActivity {

	@InjectView(R.id.back_button) View mBackButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_slasher_settings);

		ButterKnife.inject(this);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

}
