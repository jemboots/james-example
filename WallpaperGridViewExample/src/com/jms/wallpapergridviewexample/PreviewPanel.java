package com.jms.wallpapergridviewexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class PreviewPanel extends Activity {
	private AdView adView;
	private String imagePath;
	private Bitmap imageBitmap;
	private CropImageView cropImageView;
	private int scale = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.previewpanel);
		
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-4347579748027637/1011105061");
		LinearLayout adContainer = (LinearLayout)this.findViewById(R.id.adsContainerInPreview);
		adContainer.addView(adView);
		
		AdRequest adRequest = new AdRequest.Builder().addKeyword("game")
				.addKeyword("girl").addKeyword("money").addKeyword("dating")
				.addTestDevice("1B91DF7A13E674202332C251084C3ADA").build();
		adView.loadAd(adRequest);
		
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-4347579748027637/1011105061");
		adContainer = (LinearLayout)this.findViewById(R.id.adsContainerInPreview2);
		adContainer.addView(adView);
		
		adRequest = new AdRequest.Builder().addKeyword("game")
				.addKeyword("girl").addKeyword("money").addKeyword("dating")
				.addTestDevice("1B91DF7A13E674202332C251084C3ADA").build();
		adView.loadAd(adRequest);
		
		Bundle bundle = this.getIntent().getExtras();
		imagePath = bundle.getString("localpath");

		//get screen size
		int screenWidth = 1;
		int screenHeight = 1;
        if (Build.VERSION.SDK_INT < 13) {
    		DisplayMetrics metrics = getResources().getDisplayMetrics();
    		Display display = getWindowManager().getDefaultDisplay();
    		display.getMetrics(metrics);
    		screenWidth = metrics.widthPixels;
    		screenHeight = metrics.heightPixels;
        } else {
    		Display display = getWindowManager().getDefaultDisplay();
    		Point size = new Point();
    		display.getSize(size);
    		screenWidth = size.x;
    		screenHeight = size.y;
        }
		Log.v("Debug", "Screen Size: " + screenWidth + "x" + screenHeight);
		
		//read file from local
		InputStream fileInputStream;
		try {
			File cacheDir = GlobalClass.instance().getCacheFolder(this);
			File cacheFile = new File(cacheDir, imagePath);
			fileInputStream = new FileInputStream(cacheFile);
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
			
			//Loading Large Bitmaps Efficiently
			int imageWidth = bitmapOptions.outWidth;
			int imageHeight = bitmapOptions.outHeight;
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
			
			cropImageView = (CropImageView)this.findViewById(R.id.CropImageView);
			cropImageView.setImageBitmap(imageBitmap);
            cropImageView.setFixedAspectRatio(true);
			cropImageView.setAspectRatio(screenWidth * 2, screenHeight);
			
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
				
				InputStream fileInputStream = new FileInputStream(cacheFile);
				
				RectF rect = cropImageView.getActualCropRect();
				int cropx = (int) rect.left * scale;
				int cropy = (int) rect.top * scale;
				int cropw = (int) rect.width() * scale;
				int croph = (int) rect.height() * scale;
				Bitmap bitmapSource = BitmapFactory.decodeStream(fileInputStream);
		        final Bitmap croppedBitmap = Bitmap.createBitmap(bitmapSource, cropx, cropy, cropw, croph);
				
				wallpaperManager.setBitmap(croppedBitmap);
				Toast toast = Toast.makeText(this, "Set wallpaper successfully!", Toast.LENGTH_LONG);
				toast.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
