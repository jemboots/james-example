package com.jms.loadimagewithasynctask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private String[] imageURLArray = new String[]{
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/1s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/3s.jpg",
			"http://jmsliu.com/example/androidimagewithasynctask/5s.jpg"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ListView listView = (ListView)this.findViewById(R.id.listView);
		ImageAdapter imageAdapter = new ImageAdapter(this, R.layout.imageitem, imageURLArray);
		listView.setAdapter(imageAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
