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
			"http://farm8.staticflickr.com/7315/9046944633_881f24c4fa_s.jpg",
			"http://farm4.staticflickr.com/3777/9049174610_bf51be8a07_s.jpg",
			"http://farm8.staticflickr.com/7324/9046946887_d96a28376c_s.jpg",
			"http://farm3.staticflickr.com/2828/9046946983_923887b17d_s.jpg",
			"http://farm4.staticflickr.com/3810/9046947167_3a51fffa0b_s.jpg",
			"http://farm4.staticflickr.com/3773/9049175264_b0ea30fa75_s.jpg",
			"http://farm4.staticflickr.com/3781/9046945893_f27db35c7e_s.jpg",
			"http://farm6.staticflickr.com/5344/9049177018_4621cb63db_s.jpg",
			"http://farm8.staticflickr.com/7307/9046947621_67e0394f7b_s.jpg",
			"http://farm6.staticflickr.com/5457/9046948185_3be564ac10_s.jpg",
			"http://farm4.staticflickr.com/3752/9046946459_a41fbfe614_s.jpg",
			"http://farm8.staticflickr.com/7403/9046946715_85f13b91e5_s.jpg",
			"http://farm8.staticflickr.com/7315/9046944633_881f24c4fa_s.jpg",
			"http://farm4.staticflickr.com/3777/9049174610_bf51be8a07_s.jpg",
			"http://farm8.staticflickr.com/7324/9046946887_d96a28376c_s.jpg",
			"http://farm3.staticflickr.com/2828/9046946983_923887b17d_s.jpg",
			"http://farm4.staticflickr.com/3810/9046947167_3a51fffa0b_s.jpg",
			"http://farm4.staticflickr.com/3773/9049175264_b0ea30fa75_s.jpg",
			"http://farm4.staticflickr.com/3781/9046945893_f27db35c7e_s.jpg",
			"http://farm6.staticflickr.com/5344/9049177018_4621cb63db_s.jpg",
			"http://farm8.staticflickr.com/7307/9046947621_67e0394f7b_s.jpg",
			"http://farm6.staticflickr.com/5457/9046948185_3be564ac10_s.jpg",
			"http://farm4.staticflickr.com/3752/9046946459_a41fbfe614_s.jpg",
			"http://farm8.staticflickr.com/7403/9046946715_85f13b91e5_s.jpg"};
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
