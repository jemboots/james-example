package com.jms.zoomrotation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity {
	private Animator zoomAnimation = null;
	private ImageView zoomTarget;
	private AdView adview = null;
	
	private OnClickListener myClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			zoomAnimation.start();
			
			/*
			//animation example in Java code
			ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(zoomTarget, "scaleX", 0.5f);
			scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);
			scaleXAnimator.setRepeatCount(1);
			scaleXAnimator.setDuration(1000);
			
			ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(zoomTarget, "scaleY", 0.5f);
			scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);
			scaleYAnimator.setRepeatCount(1);
			scaleYAnimator.setDuration(1000);
			
			ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(zoomTarget, "rotation", 0f, 360f);
			rotationAnimator.setRepeatMode(ValueAnimator.RESTART);
			rotationAnimator.setRepeatCount(1);
			rotationAnimator.setDuration(1000);
			
			AnimatorSet set = new AnimatorSet();
			set.playTogether(scaleXAnimator, scaleYAnimator, rotationAnimator);
			set.start();
			*/
		}
	};
	
	private OnClickListener seqAnimationClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//animation example in Java code
			ObjectAnimator animator1 = ObjectAnimator.ofFloat(zoomTarget, "translationX", -200f);
			animator1.setRepeatCount(0);
			animator1.setDuration(1000);

			ObjectAnimator animator2 = ObjectAnimator.ofFloat(zoomTarget, "translationX", 100f);
			animator2.setRepeatCount(0);
			animator2.setDuration(1000);

			ObjectAnimator animator3 = ObjectAnimator.ofFloat(zoomTarget, "translationX", 0f);
			animator3.setRepeatCount(0);
			animator3.setDuration(1000);

			//sequencial animation
			AnimatorSet set = new AnimatorSet();
			set.play(animator1).before(animator2);
			set.play(animator2).before(animator3);
			set.start();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//admob
		adview = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("D9B017A3983BD8CB8B38C927F7C5E330").build();
		adview.loadAd(adRequest);
		
		zoomTarget = (ImageView) findViewById(R.id.testImage);
		zoomAnimation = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.anim.zoom_rotation_animation);
		zoomAnimation.setTarget(zoomTarget);
		Button testButton = (Button) findViewById(R.id.testButton);
		testButton.setOnClickListener(myClickListener);
		
		Button seqTestButton = (Button) findViewById(R.id.testButton2);
		seqTestButton.setOnClickListener(seqAnimationClickListener);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
