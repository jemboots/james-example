package com.jms.flappybirdanimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends ActionBarActivity {

	private GameFragment gameStage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gameStage = new GameFragment();
		gameStage.initAds(this);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, gameStage).commit();
		}
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
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class GameFragment extends Fragment {
		private InterstitialAd interstitialAd;
		private AdRequest adRequest;
		
		private ImageView bird;
		private ImageView startButton;
		private ObjectAnimator upAnimation = null;
		private ObjectAnimator downAnimation = null;
		private int startPosition = 0;
		private boolean isUpAnimationCancelled = false;
		private boolean isDownEndByClick = false;
		private long animationDuration = 1000;
		
		public GameFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			rootView.setOnClickListener(flyClickListener);
			
			bird = (ImageView) rootView.findViewById(R.id.birdimage);
			startButton = (ImageView) rootView.findViewById(R.id.startButton);
			
			upAnimation = ObjectAnimator.ofFloat(bird, "translationY", -200);
			upAnimation.setInterpolator(new DecelerateInterpolator());
			//upAnimation.setRepeatMode(ValueAnimator.REVERSE);
			upAnimation.setRepeatCount(0);
			upAnimation.setDuration(animationDuration);
			upAnimation.addListener(upListener);
			
			downAnimation = ObjectAnimator.ofFloat(bird, "translationY", 0);
			downAnimation.setInterpolator(new AccelerateInterpolator());
			downAnimation.setRepeatCount(0);
			downAnimation.setDuration(animationDuration);
			downAnimation.addListener(downListener);
			
			return rootView;
		}
		
		public void initAds(Context c)
		{
			interstitialAd = new InterstitialAd(c);
			interstitialAd.setAdUnitId("a15391920065568");
			interstitialAd.setAdListener(adListener);
			
			AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
			adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
			adRequestBuilder.addTestDevice("D9B017A3983BD8CB8B38C927F7C5E330");
			adRequest = adRequestBuilder.build();
		}
		
		private AdListener adListener = new AdListener() {
	        public void onAdLoaded() {
	        	interstitialAd.show();
	        }

	        @Override
	        public void onAdClosed() {
	            // Proceed to the next level.
	        	startButton.setVisibility(View.VISIBLE);
	        }
		};
		
		private Animator.AnimatorListener upListener = new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				Log.v("Debug", "Animation End: " + bird.getY() + ".." + bird.getTop() + ".." + bird.getTranslationY());
				if(!isUpAnimationCancelled) {
					//start to drop
					//downAnimation.setFloatValues(deltaY);
					bird.layout(bird.getLeft(), (int)bird.getY(), bird.getRight(), (int)bird.getY() + bird.getMeasuredHeight());
					bird.setTranslationY(0);
					downAnimation.setFloatValues(startPosition - bird.getTop());
					downAnimation.start();
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				Log.v("Debug", "Animation Cancelled: " + bird.getY() + ".." + bird.getTranslationY());
				isUpAnimationCancelled = true;
			}
		};
		
		private Animator.AnimatorListener downListener = new Animator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				isDownEndByClick = false;
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				if(!isDownEndByClick)
				{
					Log.v("debug", "down finished");
					interstitialAd.loadAd(adRequest);
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				Log.v("debug", "down cancelled");
			}
		};
		
		private OnClickListener flyClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(startButton.getVisibility() == View.VISIBLE)
				{
					startButton.setVisibility(View.INVISIBLE);
				}
				
				if(upAnimation.isStarted())
				{
					upAnimation.cancel();
				}
				
				if(startPosition == 0)
				{
					startPosition = bird.getTop();
				}
				
				isUpAnimationCancelled = false;
				isDownEndByClick = true;
				//bird.layout(bird.getLeft(), 228, bird.getRight(), 228 + bird.getMeasuredHeight());
				bird.layout(bird.getLeft(), (int)bird.getY(), bird.getRight(), (int)bird.getY() + bird.getMeasuredHeight());
				bird.setTranslationY(0);
				upAnimation.start();
			}
		};
	}
}
