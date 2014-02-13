package com.jms.wallpapergridviewexample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PreviewPanel extends Activity {
	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.previewpanel);
		
		adView = new AdView(this, AdSize.SMART_BANNER, "a152f84b0f4f9ed");
		LinearLayout adContainer = (LinearLayout)this.findViewById(R.id.adsContainerInPreview);
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
		
		
		Bundle bundle = this.getIntent().getExtras();
		String wallpaperFilePath = bundle.getString("localpath");

		
		//get screen size in pixel, only available after API13
		/*
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		*/

		//get screen density
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		
		//read file from local
		InputStream fileInputStream;
		try {
			fileInputStream = openFileInput(wallpaperFilePath);
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
			
			//reduce the image size
			int imageWidth = bitmapOptions.outWidth;
			int imageHeight = bitmapOptions.outHeight;
			int scale = 1;
			while (imageWidth/scale >= screenWidth && imageHeight/scale >= screenHeight) {
				imageWidth = imageWidth / 2;
				imageHeight = imageHeight / 2;
				scale = scale * 2;
			}
			
			//decode the image with necessary size
			fileInputStream = openFileInput(wallpaperFilePath); //reset the file input
			bitmapOptions.inSampleSize = scale;
			bitmapOptions.inJustDecodeBounds = false;
			Bitmap wallpaperBitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
			ImageView imageView = (ImageView)this.findViewById(R.id.preview);
			imageView.setImageBitmap(wallpaperBitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
