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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.jms.dragtorefresh.RefreshableInterface;
import com.jms.dragtorefresh.RefreshableListView;
import com.jms.rssreader.adapter.PostItemAdapter;
import com.jms.rssreader.vo.PostData;

public class MainActivity extends Activity implements RefreshableInterface {
	private enum RSSXMLTag {
		TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG;
	}

	private ArrayList<PostData> listData;
	private String urlString = "http://jmsliu.com/feed?paged=";
	private RefreshableListView postListView;
	private PostItemAdapter postAdapter;
	private int pagnation = 1; //start from 1
	private boolean isRefreshLoading = true;
	private boolean isLoading = false;
	private ArrayList<String> guidList;

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

		guidList = new ArrayList<String>();
		listData = new ArrayList<PostData>();
		postListView = (RefreshableListView) this.findViewById(R.id.postListView);
		postAdapter = new PostItemAdapter(this, R.layout.postitem, listData);
		postListView.setAdapter(postAdapter);
		postListView.setOnRefresh(this);
		postListView.onRefreshStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
						"EEE, DD MMM yyyy HH:mm:ss");
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_DOCUMENT) {

					} else if (eventType == XmlPullParser.START_TAG) {
						Log.w("warming", xpp.getName());
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
							// format the data here, otherwise format data in
							// Adapter
							Date postDate = dateFormat.parse(pdData.postDate);
							pdData.postDate = dateFormat.format(postDate);
							postDataList.add(pdData);
						} else {
							currentTag = RSSXMLTag.IGNORETAG;
						}
					} else if (eventType == XmlPullParser.TEXT) {
						String content = xpp.getText();
						content = content.trim();
						Log.d("debug", content);
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
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return postDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<PostData> result) {
			// TODO Auto-generated method stub
			boolean isupdated = false;
			for (int i = 0; i < result.size(); i++) {
				//check if the post is already in the list
				if (guidList.contains(result.get(i).postGuid)) {
					continue;
				} else {
					isupdated = true;
					guidList.add(result.get(i).postGuid);
				}
				
				if (isRefreshLoading) {
					listData.add(i, result.get(i));
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
			new RssDataController().execute(urlString + 1);
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
			new RssDataController().execute(urlString + (++pagnation));
		} else {
			postListView.onLoadingMoreComplete();
		}
	}
}
