package com.jms.wallpapergridviewexample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

public class PreviewPanel extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.previewpanel);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
