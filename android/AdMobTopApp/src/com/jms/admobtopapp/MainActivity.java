package com.jms.admobtopapp;

import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

	private AdView adView;
	private PostData[] listData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		adView = new AdView(this, AdSize.SMART_BANNER, "a151bd35eeb068d");
		LinearLayout adContainer = (LinearLayout)this.findViewById(R.id.adsContainer);
		adContainer.addView(adView);
		
		AdRequest adRequest = new AdRequest();
		Set<String> keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adView.loadAd(adRequest);
		
		this.generateDummyData();
		ListView listView = (ListView) this.findViewById(R.id.postListView);
		PostItemAdapter itemAdapter = new PostItemAdapter(this,
				R.layout.postitem, listData);
		listView.setAdapter(itemAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void generateDummyData() {
		PostData data = null;
		listData = new PostData[20];
		for (int i = 0; i < 20; i++) {
			data = new PostData();
			data.postDate = "May 20, 2013";
			data.postTitle = "Post " + (i + 1)
					+ " Title: This is the Post Title from RSS Feed";
			data.postThumbUrl = null;
			listData[i] = data;
		}
	}
}
