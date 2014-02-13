package com.jms.progressdialogexample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity {

	private ProgressDialog progressDialog;
	private ImageView imageView;
	private Bitmap bitmap;
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
		
		imageView = (ImageView)this.findViewById(R.id.imageView1);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Download Image");
		progressDialog.setMessage("Downloading in progress...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Show Image", dialogInterfaceOnClickListener);
		
		Button downloadButton = (Button) findViewById(R.id.downloadButton);
		downloadButton.setOnClickListener(downloadButtonOnClickListener);
	}
	
	private android.content.DialogInterface.OnClickListener dialogInterfaceOnClickListener = new android.content.DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			MainActivity.this.imageView.setImageBitmap(bitmap);
		}
	};
	
	private OnClickListener downloadButtonOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			progressDialog.show();
			progressDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.INVISIBLE);
			new DownloadImageTask().execute("http://farm8.staticflickr.com/7315/9046944633_881f24c4fa.jpg");
		}
	};
	
	private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap>
	{
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String cacheFileName = "tempfile.data";
			String fileurlString = params[0];
			Bitmap bitmap = null;
			try {
				URL fileurl = new URL(fileurlString);
				URLConnection connection = fileurl.openConnection();
				int filesize = connection.getContentLength();
				if(filesize < 0) {
					progressDialog.setMax(1000000);
				} else {
					progressDialog.setMax(filesize);
				}
				
				InputStream inputStream = new BufferedInputStream(fileurl.openStream(), 10240);
				OutputStream outputStream = openFileOutput(cacheFileName, Context.MODE_PRIVATE);
				byte[] buffer = new byte[1024];
				int dataLength = 0;
				int loadedDataLength = 0;
				while((dataLength = inputStream.read(buffer)) != -1) {
					loadedDataLength += dataLength;
					outputStream.write(buffer, 0, dataLength);
					publishProgress(loadedDataLength);
				}
				
				bitmap = BitmapFactory.decodeStream(openFileInput(cacheFileName));
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return bitmap;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			progressDialog.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.setMessage("Downloading Complete!");
			progressDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
			bitmap = result;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
