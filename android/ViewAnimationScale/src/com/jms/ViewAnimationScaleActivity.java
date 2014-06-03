package com.jms;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ViewAnimationScaleActivity extends Activity {
	private Animator animation = null;
	private ImageView animationTarget = null;
	private Button mybutton = null;

	private GoogleAnalyticsTracker tracker = null;
	private AdView adview = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //google analytics
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("UA-23293636-5", this);
		tracker.trackPageView("/view_animation_ratation_example");
		
		//admob ads
		adview = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("D9B017A3983BD8CB8B38C927F7C5E330").build();
		adview.loadAd(adRequest);
		
		animationTarget = (ImageView) this.findViewById(R.id.testImage);
		animation = AnimatorInflater.loadAnimator(this, R.anim.scale_animation);
		animation.setTarget(animationTarget);
		
		mybutton = (Button) this.findViewById(R.id.testButton);
		mybutton.setOnClickListener(myClickListener);
    }
    
	private OnClickListener myClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			animation.start();
			
			/*
			//animation in java code
			ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(animationTarget, "scaleX", 0.5f);
			scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);
			scaleXAnimator.setRepeatCount(1);
			scaleXAnimator.setDuration(1000);
			scaleXAnimator.start();
			*/
			
			tracker.trackEvent("Clicks", "Button", "clicked", 1);
		}
	};

	protected void onDestroy() {
		if (adview != null) {
			adview.destroy();
		}

		tracker.stopSession();
		super.onDestroy();
	}
}