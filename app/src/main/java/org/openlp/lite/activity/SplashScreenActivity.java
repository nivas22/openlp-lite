package org.openlp.lite.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.openlp.lite.R;


public class SplashScreenActivity extends Activity {

	private static final int TIME = 4 * 1000;// 4 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(SplashScreenActivity.this,
						SongsListActivity.class);
				startActivity(intent);

				SplashScreenActivity.this.finish();

				overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);

			}
		}, TIME);
		
		new Handler().postDelayed(new Runnable() {
			  @Override
			  public void run() {
			         } 
			    }, TIME);
	}

	
	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}
}
