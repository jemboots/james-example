package com.jms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;

public class InterstitialsAdsExampleActivity extends Activity implements
		AdListener {
	/** Called when the activity is first created. */
	private InterstitialAd interstitialAds = null;
	private TextView textView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.interstitialAds = new InterstitialAd(this, "a150272e162142c");
		this.interstitialAds.setAdListener(this);

		Button loadButton = (Button) this.findViewById(R.id.loadButton);
		loadButton.setOnClickListener(loadButtonOnClick);

		this.textView = (TextView) this.findViewById(R.id.stateTextView);
	}

	private OnClickListener loadButtonOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			textView.setText("Loading Intertitial Ads");

			AdRequest adr = new AdRequest();
			// add your test device here
			//adr.addTestDevice("8E452640BC83C672B070CDCA8AB9B06B");
			interstitialAds.loadAd(adr);
		}
	};

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveAd(Ad ad, ErrorCode error) {
		String message = "Load Ads Failed: (" + error + ")";
		textView.setText(message);
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Called when an Activity is created in front of the app (e.g. an
	 * interstitial is shown, or an ad is clicked and launches a new Activity).
	 */
	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		if (interstitialAds.isReady()) {
			interstitialAds.show();
		} else {
			textView.setText("Interstitial ad was not ready to be shown.");
		}
	}
}