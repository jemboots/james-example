package com.jms.dragtorefresh;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements RefreshableInterface{
	private ArrayList<PostData> listData;
	private RefreshableListView listView;
	private PostItemAdapter itemAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.generateDummyData();
		listView = (RefreshableListView) this.findViewById(R.id.postListView);
		itemAdapter = new PostItemAdapter(this,
				R.layout.postitem, listData);
		listView.setAdapter(itemAdapter);
		listView.setOnRefresh(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void generateDummyData() {
		PostData data = null;
		listData = new ArrayList<PostData>();
		for (int i = 0; i < 10; i++) {
			data = new PostData();
			data.postDate = "May 20, 2013";
			data.postTitle = "Post " + (i + 1)
					+ " Title: This is the Post Title from RSS Feed";
			data.postThumbUrl = null;
			listData.add(data);
		}
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			return new String[10];
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			for (int i = 0; i < 5; i++) {
				PostData data = new PostData();
				data.postDate = "May 20, 2013";
				data.postTitle = "Post " + (i + 1 + listData.size())
						+ " Title: This is the Post Title from RSS Feed";
				data.postThumbUrl = null;
				listData.add(data);
			}
			
			listView.onRefreshComplete();
			itemAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}

	@Override
	public void startFresh() {
		// TODO Auto-generated method stub
		new GetDataTask().execute();
	}
}
