package com.jms.wallpapergridviewexample;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity {

	private Integer[] wallpaperThumbIntegers = {
			R.drawable.thumb1, R.drawable.thumb2,
			R.drawable.thumb3, R.drawable.thumb4,
			R.drawable.thumb5, R.drawable.thumb6,
			R.drawable.thumb7, R.drawable.thumb8
	};
	
	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		adView = new AdView(this, AdSize.SMART_BANNER, "a152f84b0f4f9ed");
		LinearLayout adContainer = (LinearLayout)this.findViewById(R.id.adsContainer);
		adContainer.addView(adView);
		
		AdRequest adRequest = new AdRequest();
		Set<String> keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adRequest.addTestDevice("1B91DF7A13E674202332C251084C3ADA");
		adView.loadAd(adRequest);
		
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class ImageAdapter extends BaseAdapter {
		private Context mContext;
		
		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return wallpaperThumbIntegers.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageView;
			
			if(convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(216, 162));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}
			
			imageView.setImageResource(wallpaperThumbIntegers[position]);
			return imageView;
		}
	}
}
