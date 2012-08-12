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

public class InterstitialsAdsExampleActivity extends Activity implements AdListener{
	/** Called when the activity is first created. */
	private InterstitialAd interstitialAds = null;
	private TextView textView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.interstitialAds = new InterstitialAd(this, "a150272e162142c");
		this.interstitialAds.setAdListener(this);

		Button loadButton = (Button)this.findViewById(R.id.loadButton);
		loadButton.setOnClickListener(loadButtonOnClick);

		this.textView = (TextView)this.findViewById(R.id.stateTextView);
	}

	private OnClickListener loadButtonOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			textView.setText("Loading Intertitial Ads");

			AdRequest adr = new AdRequest();
			interstitialAds.loadAd(adr);
		}
	};

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
//			    String message = "onFailedToReceiveAd (" + error + ")";
//			    Log.d(LOG_TAG, message);
//			    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//		
//			    // Change the button text and disable the button.
//			    if (ad == interstitialAd) {
//			      showButton.setText("Failed to Receive Ad");
//			      showButton.setEnabled(false);
//			    }
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
		//	    Log.d(LOG_TAG, "onReceiveAd");
		//	    Toast.makeText(this, "onReceiveAd", Toast.LENGTH_SHORT).show();
		//
		//	    // Change the button text and enable the button.
		//	    if (ad == interstitialAd) {
		//	      showButton.setText("Show Interstitial");
		//	      showButton.setEnabled(true);
		//	    }
		
		interstitialAds.show();
//	      if (interstitialAd.isReady()) {
//	          interstitialAd.show();
//	        } else {
//	          Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
//	        }
	}
}