package com.jms.wallpapergridviewexample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

	private Integer[] wallpaperThumbIntegers = { R.drawable.thumb1,
			R.drawable.thumb2, R.drawable.thumb3, R.drawable.thumb4,
			R.drawable.thumb5, R.drawable.thumb6, R.drawable.thumb7,
			R.drawable.thumb8, R.drawable.thumb9, R.drawable.thumb10,
			R.drawable.thumb11, R.drawable.thumb12, R.drawable.thumb13,
			R.drawable.thumb14, R.drawable.thumb15, R.drawable.thumb16,
			R.drawable.thumb17, R.drawable.thumb18, R.drawable.thumb19,
			R.drawable.thumb20, R.drawable.thumb21, R.drawable.thumb22,
			R.drawable.thumb23, R.drawable.thumb24, R.drawable.thumb25,
			R.drawable.thumb26, R.drawable.thumb27, R.drawable.thumb28,
			R.drawable.thumb29};

	private String[] imageURLArray = {
			"http://jmsliu.com/example/androidwallpaperapp/naruto/1.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/2.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/3.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/4.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/5.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/6.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/7.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/8.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/9.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/10.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/11.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/12.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/13.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/14.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/15.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/16.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/17.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/18.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/19.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/20.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/21.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/22.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/23.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/24.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/25.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/26.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/27.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/28.jpg",
			"http://jmsliu.com/example/androidwallpaperapp/naruto/29.jpg" };

	private ProgressDialog downloadProgressDialog;
	private AdView adView;
	private Intent previewPanelIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// admob
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-4347579748027637/1011105061");

		LinearLayout adContainer = (LinearLayout) this
				.findViewById(R.id.adsContainer);
		adContainer.addView(adView);

		AdRequest adRequest = new AdRequest.Builder().addKeyword("game")
				.addKeyword("girl").addKeyword("video")
				.addTestDevice("1B91DF7A13E674202332C251084C3ADA").build();
		adView.loadAd(adRequest);

		// grid view
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(gridViewImageClickListener);

		// progress bar
		downloadProgressDialog = new ProgressDialog(MainActivity.this);
		downloadProgressDialog.setTitle("Downloading Wallpaper");
		downloadProgressDialog.setMessage("Downloading in progress...");
		downloadProgressDialog
				.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downloadProgressDialog.setProgress(0);
	}

	private OnItemClickListener gridViewImageClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			downloadProgressDialog.setProgress(0);
			downloadProgressDialog.setMax(100);
			downloadProgressDialog.show();
			new DownloadWallpaperTask().execute(imageURLArray[arg2]);
		}
	};

	private class DownloadWallpaperTask extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String wallpaperURLStr = params[0];
			String localFileName = Integer.toString(wallpaperURLStr.hashCode());

			try {
				File cacheDir = GlobalClass.instance().getCacheFolder(
						MainActivity.this);
				File cacheFile = new File(cacheDir, localFileName);
				if (!cacheFile.exists()) {
					URL wallpaperURL = new URL(wallpaperURLStr);
					URLConnection connection = wallpaperURL.openConnection();

					// get file length
					int filesize = connection.getContentLength();
					if (filesize < 0) {
						downloadProgressDialog.setMax(1000000);
					} else {
						downloadProgressDialog.setMax(filesize);
					}

					InputStream inputStream = new BufferedInputStream(
							wallpaperURL.openStream(), 10240);

					FileOutputStream outputStream = new FileOutputStream(
							cacheFile);

					byte buffer[] = new byte[1024];
					int dataSize;
					int loadedSize = 0;
					while ((dataSize = inputStream.read(buffer)) != -1) {
						loadedSize += dataSize;
						publishProgress(loadedSize);
						outputStream.write(buffer, 0, dataSize);
					}

					outputStream.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return localFileName;
		}

		protected void onProgressUpdate(Integer... progress) {
			downloadProgressDialog.setProgress(progress[0]);
		}

		protected void onPostExecute(String result) {
			downloadProgressDialog.hide();

			// open preview activity
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
		// getMenuInflater().inflate(R.menu.main, menu);
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

			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(wallpaperThumbIntegers[position]);
			return imageView;
		}
	}
}
