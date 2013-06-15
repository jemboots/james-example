package com.jms.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.jms.rssreader.adapter.PostItemAdapter;
import com.jms.rssreader.vo.PostData;

public class MainActivity extends Activity {
	private PostData[] listData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postlist);

		this.generateDummyData();
		ListView listView = (ListView) this.findViewById(R.id.postListView);
		PostItemAdapter itemAdapter = new PostItemAdapter(this,
				R.layout.postitem, listData);
		listView.setAdapter(itemAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void generateDummyData() {
		PostData data = null;
		listData = new PostData[10];
		for (int i = 0; i < 10; i++) {
			data = new PostData();
			data.postDate = "May 20, 2013";
			data.postTitle = "Post " + (i + 1)
					+ " Title: This is the Post Title from RSS Feed";
			data.postThumbUrl = null;
			listData[i] = data;
		}
	}
}
