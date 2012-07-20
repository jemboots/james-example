package com.jms;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ViewAnimationScaleActivity extends Activity {
	private Animation animation = null;
	// private Animation moveAnimation = null;
	// private RelativeLayout imageContainer = null;
	private ImageView animationTarget = null;
	private Button mybutton = null;

	private GoogleAnalyticsTracker tracker = null;
	private AdView adview = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("UA-23293636-5", this);
		tracker.trackPageView("/view_animation_ratation_example");

		adview = (AdView) findViewById(R.id.adView);
		AdRequest re = new AdRequest();
		adview.loadAd(re);
		
		animationTarget = (ImageView) this.findViewById(R.id.testImage);
		mybutton = (Button) this.findViewById(R.id.testButton);

		mybutton.setOnClickListener(myClickListener);
		this.animation = AnimationUtils.loadAnimation(this,
				R.anim.scale_animation);
    }
    
	private OnClickListener myClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			animationTarget.startAnimation(animation);
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