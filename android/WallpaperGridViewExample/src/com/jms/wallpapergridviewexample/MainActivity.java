package com.jms.wallpapergridviewexample;

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
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	
	private ProgressDialog downloadProgressDialog;
	private AdView adView;
	private Intent previewPanelIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// admob
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
		
		//grid view
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(gridViewImageClickListener);
		
		//progress bar
		downloadProgressDialog = new ProgressDialog(MainActivity.this);
		downloadProgressDialog.setTitle("Downloading Wallpaper");
		downloadProgressDialog.setMessage("Downloading in progress...");
		downloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downloadProgressDialog.setProgress(0);
		//downloadProgressDialog.setMax(20);
		//downloadProgressDialog.show();
	}
	
	private OnItemClickListener gridViewImageClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			downloadProgressDialog.show();
			new DownloadWallpaperTask().execute("http://www.hdwallpapers.org/wallpapers/white-winter-snow-leopard-1920x1200.jpg");
		}
	};
	
	private class DownloadWallpaperTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String wallpaperURLStr = params[0];
			String localPath = Integer.toString(wallpaperURLStr.hashCode());
			try {
				URL wallpaperURL = new URL(wallpaperURLStr);
				URLConnection connection = wallpaperURL.openConnection();
				
				//get file length
				int filesize = connection.getContentLength();
				if(filesize < 0) {
					downloadProgressDialog.setMax(1000000);
				} else {
					downloadProgressDialog.setMax(filesize);
				}
				
				InputStream inputStream = new BufferedInputStream(wallpaperURL.openStream(), 10240);
				String appName = getResources().getString(R.string.app_name);
				OutputStream outputStream = openFileOutput(localPath, Context.MODE_PRIVATE);
				byte buffer[] = new byte[1024];
				int dataSize;
				int loadedSize = 0;
	            while ((dataSize = inputStream.read(buffer)) != -1) {
	            	loadedSize += dataSize;
	            	publishProgress(loadedSize);
	            	outputStream.write(buffer, 0, dataSize);
	            }
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return localPath;
		}
		
		
		protected void onProgressUpdate(Integer... progress) {
			downloadProgressDialog.setProgress(progress[0]);
		}
		
		protected void onPostExecute(String result) {
			downloadProgressDialog.hide();
			
			//open preview activity
			Bundle postInfo = new Bundle();
			postInfo.putString("localpath", result);

			if (previewPanelIntent == null) {
				previewPanelIntent = new Intent(MainActivity.this,
						PreviewPanel.class);
			}

			previewPanelIntent.putExtras(postInfo);
			startActivity(previewPanelIntent);
		}
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
