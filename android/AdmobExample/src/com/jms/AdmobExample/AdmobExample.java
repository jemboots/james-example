package com.jms.AdmobExample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.ads.*;

public class AdmobExample extends Activity {
    /** Called when the activity is first created. */
	private AdView myAdView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myAdView = new AdView(this, AdSize.BANNER, "a14f840b733d0dc");
        
        //get layoutView
        LinearLayout rootView = (LinearLayout)this.findViewById(R.id.rootViewGroup);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(480, 75);
        rootView.addView(myAdView, 0, layoutParams);        
        
        AdRequest re = new AdRequest();
        re.setGender(AdRequest.Gender.FEMALE);
        //re.setTestDevices(testDevices);
        //re.setTesting(testing)
        myAdView.loadAd(re);
    }
}