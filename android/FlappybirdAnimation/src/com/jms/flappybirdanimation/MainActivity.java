package com.jms.flappybirdanimation;

import android.R.bool;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
	public static class PlaceholderFragment extends Fragment {
		public ImageView bird;
		private ObjectAnimator upAnimation = null;
		private ObjectAnimator downAnimation = null;
		private int startPosition = 0;
		private boolean isCancelled = false;
		private long animationDuration = 1000;
		
		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			bird = (ImageView) rootView.findViewById(R.id.birdimage);
			
			upAnimation = ObjectAnimator.ofFloat(bird, "translationY", -200);
			upAnimation.setInterpolator(new DecelerateInterpolator());
			//upAnimation.setRepeatMode(ValueAnimator.REVERSE);
			upAnimation.setRepeatCount(0);
			upAnimation.setDuration(animationDuration);
			upAnimation.addListener(upListener);
			rootView.setOnClickListener(flyClickListener);
			
			downAnimation = ObjectAnimator.ofFloat(bird, "translationY", 0);
			downAnimation.setInterpolator(new AccelerateInterpolator());
			downAnimation.setRepeatCount(0);
			downAnimation.setDuration(animationDuration);
			return rootView;
		}
		
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
				if(!isCancelled) {
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
				isCancelled = true;
			}
		};
		
		private OnClickListener flyClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(upAnimation.isStarted())
				{
					upAnimation.cancel();
				}
				
				if(startPosition == 0)
				{
					startPosition = bird.getTop();
				}
				
				isCancelled = false;
				//bird.layout(bird.getLeft(), 228, bird.getRight(), 228 + bird.getMeasuredHeight());
				bird.layout(bird.getLeft(), (int)bird.getY(), bird.getRight(), (int)bird.getY() + bird.getMeasuredHeight());
				bird.setTranslationY(0);
				upAnimation.start();
			}
		};
	}
}
