package com.jms.memoenglishwords;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.jms.memoenglishwords.MainApplication.TrackerName;

public class MainActivity extends ActionBarActivity {
	private PlaceholderFragment pFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
        	View decorView = getWindow().getDecorView();
        	// Hide the status bar.
        	int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        	decorView.setSystemUiVisibility(uiOptions);
        }
		
		ActionBar actionBar = getSupportActionBar(); //or getActionBar();
		actionBar.hide();
		
		setContentView(R.layout.main);
		
		pFragment = new PlaceholderFragment(this);
		pFragment.initAds(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, pFragment).commit();
		}
		
		//Google Analytics
		MainApplication application = (MainApplication) getApplication();
		Tracker t = application.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(MainApplication.MAIN_ACTIVITY);
		t.send(new HitBuilders.AppViewBuilder().build());
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public class PlaceholderFragment extends Fragment {
		private InterstitialAd interstitialAd;
		private AdRequest adRequest;
		private Context context;
		
		public PlaceholderFragment(Context c) {
			context = c;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.launchview, container,
					false);
			
			Button learningButton = (Button) rootView.findViewById(R.id.learingButton);
			learningButton.setOnClickListener(onLearningButtonClickListener);
			
			Button memorizeButton = (Button) rootView.findViewById(R.id.memoButton);
			memorizeButton.setOnClickListener(onMemoButtonClickListener);
			
			Button getMoreButton = (Button) rootView.findViewById(R.id.getMoreButton);
			getMoreButton.setOnClickListener(onGetMoreButtonClickListener);
			
			return rootView;
		}
		
		private OnClickListener onLearningButtonClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainApplication application = (MainApplication) getApplication();
				Tracker t = application.getTracker(TrackerName.APP_TRACKER);
				t.send(new HitBuilders.EventBuilder()
	            .setCategory(MainApplication.CLICK_CATEGORY)
	            .setAction(MainApplication.CLICK_LEARNING_BUTTON)
	            .setLabel(null)
	            .build());
				
				Intent postviewIntent = new Intent(context, LearningListActivity.class);
				startActivity(postviewIntent);
			}
		};
		
		private OnClickListener onMemoButtonClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainApplication application = (MainApplication) getApplication();
				Tracker t = application.getTracker(TrackerName.APP_TRACKER);
				t.send(new HitBuilders.EventBuilder()
	            .setCategory(MainApplication.CLICK_CATEGORY)
	            .setAction(MainApplication.CLICK_MEMORIZE_BUTTON)
	            .setLabel(null)
	            .build());
				
				Intent postviewIntent = new Intent(context, MemorizeActivity.class);
				startActivity(postviewIntent);
			}
		};
		
		private OnClickListener onGetMoreButtonClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainApplication application = (MainApplication) getApplication();
				Tracker t = application.getTracker(TrackerName.APP_TRACKER);
				t.send(new HitBuilders.EventBuilder()
	            .setCategory(MainApplication.CLICK_CATEGORY)
	            .setAction(MainApplication.CLICK_MORE_GAME_BUTTON)
	            .setLabel(null)
	            .build());
				
				showInterstitialAds();
			}
		};
		
		public void initAds(Context c)
		{
			interstitialAd = new InterstitialAd(c);
			interstitialAd.setAdUnitId("ca-app-pub-4347579748027637/3030528660");
			interstitialAd.setAdListener(adListener);
			
			AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
			adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
			//adRequestBuilder.addTestDevice("D9B017A3983BD8CB8B38C927F7C5E330");
			adRequest = adRequestBuilder.build();
			interstitialAd.loadAd(adRequest);
		}
		
		/**
		 * Interstitial ads listener
		 * 
		 */
		private AdListener adListener = new AdListener() {
	        public void onAdLoaded() {
	        	Log.v("ads", "ads loaded");
	        }
	        
	        public void onAdFailedToLoad(int errorCode) {
	        	Log.v("ads", "ads failed: " + String.valueOf(errorCode));
	        }
	        
	        public void onAdOpened() {
	        	Log.v("ads", "ads opened");
	        }
	        
	        @Override
	        public void onAdLeftApplication() {
	        	Log.v("ads", "ads left app");
	        }

	        @Override
	        public void onAdClosed() {
	            // Proceed to the next level.
	        }
		};
		
		/**
		 * Show interstitial ads when it is ready. Interstitial ads could be null if it is not ready
		 * 
		 */
		private void showInterstitialAds()
		{
			if(interstitialAd != null && interstitialAd.isLoaded())
			{
				interstitialAd.show();
			}
		}
	}
}
