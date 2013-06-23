package com.jms.drawimage;

import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class DrawImage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AdView adView = new AdView(this, AdSize.SMART_BANNER, "a14f62df9aef8af");
        LinearLayout mainLayout = (LinearLayout)this.findViewById(R.id.mainView);
        mainLayout.addView(adView, 0);
        
		AdRequest adRequest = new AdRequest();
		Set<String> keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adView.loadAd(adRequest);
        
		Button mybutton = (Button)findViewById(R.id.drawButton);
		mybutton.setOnClickListener(clickListener);
    }
    
    private OnClickListener clickListener = new OnClickListener() {
		private boolean isDrawn = false;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!isDrawn) {
				DrawCanvas mycanvas = (DrawCanvas)findViewById(R.id.SurfaceView01);
				mycanvas.startDrawImage();
				isDrawn = true;
			}
		}
	};
}