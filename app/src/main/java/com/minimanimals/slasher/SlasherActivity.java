package com.minimanimals.slasher;

import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SlasherActivity extends Activity {

	GameState mGameState;

	@InjectView(R.id.play_surface) PlaySurface mPlaySurface;
	@InjectView(R.id.score) TextView mScore;
	@InjectView(R.id.settings_button) View mBackButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_slasher);

		ButterKnife.inject(this);

		// ElementRenderer elementRenderer = new SlashElementRenderer();
		ElementRenderer elementRenderer = new DotElementRenderer();

		mGameState = new GameState(5, 8, elementRenderer);

		mPlaySurface.init(mGameState, elementRenderer);

		mGameState.setScoreChangeListener(new GameState.ScoreChangeListener() {
			public void scoreChange(int score) {
				mScore.setText("Score " + score);
			}
		});

		mBackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SlasherActivity.this, SlasherSettingsActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGameState.resetScore();
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
