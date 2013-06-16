package com.jms.loadimagewithasynctask;

import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

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
		//adview.setAdListener(this);
		
		RelativeLayout rootView = (RelativeLayout)this.findViewById(R.id.topLayout);
		rootView.addView(adview, 0);
		
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

}
