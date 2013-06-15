package com.jms.AdmobSmartBannerExample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;
import com.jms.AdmobSmartBannerExample.R;

public class AdmobSmartBannerExample extends Activity implements AdListener {
    /** Called when the activity is first created. */
	private AdView myAdView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myAdView = new AdView(this, AdSize.SMART_BANNER, "Your AdMob ID");
        myAdView.setAdListener(this);
        
        //get layoutView
        LinearLayout rootView = (LinearLayout)this.findViewById(R.id.rootViewGroup);
        //LinearLayout.LayoutParams layoutParams = new LayoutParams(480, 75);
        rootView.addView(myAdView, 0);
        
        AdRequest re = new AdRequest();
        re.setGender(AdRequest.Gender.FEMALE);
        //re.setTestDevices(testDevices);
        //re.setTesting(testing)
        //re.addTestDevice("1B91DF7A13E674202332C251084C3ADA");
        myAdView.loadAd(re);
    }
    
    @Override
    public void onReceiveAd(Ad ad) {
		TextView textview = (TextView)this.findViewById(R.id.textView);
		textview.setText("Above is the Ad Banner which is returned by Admob!");
    }

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		TextView textview = (TextView)this.findViewById(R.id.textView);
		textview.setText("Ad request successful, but no ad returned due to lack of ad inventory.");
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
}