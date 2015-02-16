package com.minimanimals.slasher;

import android.view.View;
import android.app.Activity;
import android.os.Bundle;


public class Slasher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_slasher);

		View container = findViewById(R.id.slasher_container);

		ElementRenderer elementRenderer = new SlashElementRenderer();
		// ElementRenderer elementRenderer = new DotElementRenderer();

		GameState gameState = new GameState(6, 10, 8);

		PlaySurface playSurface = (PlaySurface)findViewById(R.id.play_surface);
		playSurface.init(gameState, elementRenderer);
	}

	@Override
	protected void onResume() {
		super.onResume();
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
