package com.jms.wallpapergridviewexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PreviewPanel extends Activity {
	private AdView adView;
	private String imagePath;
	private Bitmap imageBitmap;
	
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
		
		adView = new AdView(this, AdSize.SMART_BANNER, "a152f84b0f4f9ed");
		adContainer = (LinearLayout)this.findViewById(R.id.adsContainerInPreview2);
		adContainer.addView(adView);
		adRequest = new AdRequest();
		keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adRequest.addTestDevice("1B91DF7A13E674202332C251084C3ADA");
		adView.loadAd(adRequest);
		
		Bundle bundle = this.getIntent().getExtras();
		imagePath = bundle.getString("localpath");

		
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
			File cacheDir = GlobalClass.instance().getCacheFolder(this);
			File cacheFile = new File(cacheDir, imagePath);
			fileInputStream = new FileInputStream(cacheFile);
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
			fileInputStream = new FileInputStream(cacheFile);
			bitmapOptions.inSampleSize = scale;
			bitmapOptions.inJustDecodeBounds = false;
			imageBitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
			ImageView imageView = (ImageView)this.findViewById(R.id.preview);
			imageView.setImageBitmap(imageBitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_setwallpaper:
			setWallPaper();
			return true;
		case R.id.action_back:
			finish();
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}
	
	private void setWallPaper() {
		File cacheDir = GlobalClass.instance().getCacheFolder(this);
		File cacheFile = new File(cacheDir, imagePath);
		
		if (cacheFile.exists()) {
			WallpaperManager wallpaperManager = WallpaperManager
					.getInstance(this);
			try {
				wallpaperManager.setBitmap(imageBitmap);
				Toast toast = Toast.makeText(this, "Set wallpaper successfully!", Toast.LENGTH_LONG);
				toast.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
