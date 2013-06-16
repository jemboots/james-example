package com.jms.loadimagewithasynctask;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity implements AdListener{

	private AdView adview;
	private String[] imageURLArray = new String[]{
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		adview = new AdView(this, AdSize.SMART_BANNER, "a151bd35eeb068d");
		adview.setAdListener(this);
		
		LinearLayout rootView = (LinearLayout)this.findViewById(R.id.topLayout);
		//RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(480, 75);
		rootView.addView(adview);
		
		AdRequest adRequest = new AdRequest();
		Set<String> keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adview.loadAd(adRequest);
		
		ListView listView = (ListView)this.findViewById(R.id.listView);
		ImageAdapter imageAdapter = new ImageAdapter(this, R.layout.imageitem, imageURLArray);
		listView.setAdapter(imageAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
	}

}
