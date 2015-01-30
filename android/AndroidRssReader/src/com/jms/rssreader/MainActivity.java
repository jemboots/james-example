package com.jms.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.jms.dragtorefresh.RefreshableInterface;
import com.jms.dragtorefresh.RefreshableListView;
import com.jms.rssreader.MainApplication.TrackerName;
import com.jms.rssreader.adapter.PostItemAdapter;
import com.jms.rssreader.vo.PostData;

public class MainActivity extends Activity implements RefreshableInterface {
	private enum RSSXMLTag {
		TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG;
	}

	private ArrayList<PostData> listData;
	private String urlString = "http://jmsliu.com/feed?paged="; //please set enablePagnation = true
	private RefreshableListView postListView;
	private PostItemAdapter postAdapter;
	private boolean enablePagnation = true;
	private int pagnation = 1; //start from 1
	private boolean isRefreshLoading = true;
	private boolean isLoading = false;
	private ArrayList<String> guidList;
	
	private InterstitialAd interstitialAd;
	private AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postlist);

		// check connectivity state
		/*
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			new RssDataController().execute(urlString + 0);
		} else {
			
		}
		*/
		
		//Google Analytics
		MainApplication application = (MainApplication) getApplication();
		Tracker t = application.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(MainApplication.MAIN_ACTIVITY);
		t.send(new HitBuilders.AppViewBuilder().build());

		guidList = new ArrayList<String>();
		listData = new ArrayList<PostData>();
		postListView = (RefreshableListView) this.findViewById(R.id.postListView);
		postAdapter = new PostItemAdapter(this, R.layout.postitem, listData);
		postListView.setAdapter(postAdapter);
		postListView.setOnRefresh(this);
		postListView.onRefreshStart();
		postListView.setOnItemClickListener(onItemClickListener);
		
		initAds(this);
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			PostData data = listData.get(arg2 - 1);
			
			Bundle postInfo = new Bundle();
			postInfo.putString("content", data.postContent);
			
			Intent postviewIntent = new Intent(MainActivity.this, PostViewActivity.class);
			postviewIntent.putExtras(postInfo);
			startActivity(postviewIntent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initAds(Context c)
	{
		interstitialAd = new InterstitialAd(c);
		interstitialAd.setAdUnitId("ca-app-pub-4347579748027637/1642897863");
		interstitialAd.setAdListener(adListener);
		
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
		//adRequestBuilder.addTestDevice("D9B017A3983BD8CB8B38C927F7C5E330");
		adRequest = adRequestBuilder.build();
		interstitialAd.loadAd(adRequest);
	}
	
	/**
	 * Interstitial ads listener
	 * 
	 */
	private AdListener adListener = new AdListener() {
        public void onAdLoaded() {
        	Log.v("ads", "ads loaded");
        	showInterstitialAds();
        }
        
        public void onAdFailedToLoad(int errorCode) {
        	Log.v("ads", "ads failed: " + String.valueOf(errorCode));
        }
        
        public void onAdOpened() {
        	Log.v("ads", "ads opened");
        }
        
        @Override
        public void onAdLeftApplication() {
        	Log.v("ads", "ads left app");
        }

        @Override
        public void onAdClosed() {
            // Proceed to the next level.
        }
	};
	
	/**
	 * Show interstitial ads when it is ready. Interstitial ads could be null if it is not ready
	 * 
	 */
	private void showInterstitialAds()
	{
		if(interstitialAd != null && interstitialAd.isLoaded())
		{
			interstitialAd.show();
		}
	}

	private class RssDataController extends
			AsyncTask<String, Integer, ArrayList<PostData>> {
		private RSSXMLTag currentTag;

		@Override
		protected ArrayList<PostData> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlStr = params[0];
			InputStream is = null;
			ArrayList<PostData> postDataList = new ArrayList<PostData>();
			try {
				URL url = new URL(urlStr);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setReadTimeout(10 * 1000);
				connection.setConnectTimeout(10 * 1000);
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				connection.connect();
				int response = connection.getResponseCode();
				Log.d("debug", "The response is: " + response);
				is = connection.getInputStream();

				// parse xml
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(is, null);

				int eventType = xpp.getEventType();
				PostData pdData = null;
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"EEE, d MMM yyyy HH:mm:ss", Locale.US);
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_DOCUMENT) {

					} else if (eventType == XmlPullParser.START_TAG) {
						if (xpp.getName().equals("item")) {
							pdData = new PostData();
							currentTag = RSSXMLTag.IGNORETAG;
						} else if (xpp.getName().equals("title")) {
							currentTag = RSSXMLTag.TITLE;
						} else if (xpp.getName().equals("link")) {
							currentTag = RSSXMLTag.LINK;
						} else if (xpp.getName().equals("pubDate")) {
							currentTag = RSSXMLTag.DATE;
						} else if (xpp.getName().equals("encoded")) {
							currentTag = RSSXMLTag.CONTENT;
						} else if (xpp.getName().equals("guid")) {
							currentTag = RSSXMLTag.GUID;
						}
					} else if (eventType == XmlPullParser.END_TAG) {
						if (xpp.getName().equals("item")) {
							try {
								// format the data here, otherwise format data in Adapter
								if(pdData.postDate != null) {
									Date postDate = dateFormat.parse(pdData.postDate);
									pdData.postDate = dateFormat.format(postDate);
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							postDataList.add(pdData);
						} else {
							currentTag = RSSXMLTag.IGNORETAG;
						}
					} else if (eventType == XmlPullParser.TEXT) {
						String content = xpp.getText();
						content = content.trim();
						if (pdData != null) {
							switch (currentTag) {
							case TITLE:
								if (content.length() != 0) {
									if (pdData.postTitle != null) {
										pdData.postTitle += content;
									} else {
										pdData.postTitle = content;
									}
								}
								break;
							case LINK:
								if (content.length() != 0) {
									if (pdData.postLink != null) {
										pdData.postLink += content;
									} else {
										pdData.postLink = content;
									}
								}
								break;
							case DATE:
								if (content.length() != 0) {
									if (pdData.postDate != null) {
										pdData.postDate += content;
									} else {
										pdData.postDate = content;
									}
								}
								break;
							case CONTENT:
								if (content.length() != 0) {
									if (pdData.postContent != null) {
										pdData.postContent += content;
									} else {
										pdData.postContent = content;
									}
								}
								break;
							case GUID:
								if (content.length() != 0) {
									if (pdData.postGuid != null) {
										pdData.postGuid += content;
									} else {
										pdData.postGuid = content;
									}
								}
								break;
							default:
								break;
							}
						}
					}

					eventType = xpp.next();
				}
				Log.v("tst", String.valueOf((postDataList.size())));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return postDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<PostData> result) {
			// TODO Auto-generated method stub
			boolean isupdated = false;
			int j = 0;
			for (int i = 0; i < result.size(); i++) {
				//check if the post is already in the list
				if (guidList.contains(result.get(i).postLink)) {
					continue;
				} else {
					isupdated = true;
					guidList.add(result.get(i).postLink);
				}
				
				if (isRefreshLoading) {
					listData.add(j, result.get(i));
					j++;
				} else {
					listData.add(result.get(i));
				}
			}

			if (isupdated) {
				postAdapter.notifyDataSetChanged();
			}
			
			isLoading = false;
			
			if (isRefreshLoading) {
				postListView.onRefreshComplete();
			} else {
				postListView.onLoadingMoreComplete();
			}
			
			super.onPostExecute(result);
		}
	}

	@Override
	public void startFresh() {
		// TODO Auto-generated method stub
		if (!isLoading) {
			isRefreshLoading = true;
			isLoading = true;
			/*
			 * Pagination: 
			 * 
			 * If your rss feed looks like: 
			 * 
			 * "http://jmsliu.com/feed?paged="
			 * 
			 * You can try follow code for pagination. 
			 * 
			 * new RssDataController().execute(urlString + 1);
			 */
			if(enablePagnation) {
				new RssDataController().execute(urlString + 1);
			} else {
				new RssDataController().execute(urlString);
			}
		} else {
			postListView.onRefreshComplete();
		}
	}

	@Override
	public void startLoadMore() {
		// TODO Auto-generated method stub
		if (!isLoading) {
			isRefreshLoading = false;
			isLoading = true;
			/*
			 * Pagination: 
			 * 
			 * If your rss feed source looks like "http://jmsliu.com/feed?paged=", 
			 * you can try follow code for pagination:
			 * 
			 * new RssDataController().execute(urlString + (++pagnation));
			 * 
			 * Otherwise, please use this:
			 * 
			 * new RssDataController().execute(urlString);
			 */
			if(enablePagnation)	{
				new RssDataController().execute(urlString + (++pagnation));
			} else {
				new RssDataController().execute(urlString);
			}
		} else {
			postListView.onLoadingMoreComplete();
		}
	}
}
