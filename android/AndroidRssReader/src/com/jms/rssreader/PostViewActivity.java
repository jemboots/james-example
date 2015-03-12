package com.jms.rssreader;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class PostViewActivity extends Activity {
	
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.postview);
		this.webView = (WebView) this.findViewById(R.id.webview);
		
		Bundle bundle = this.getIntent().getExtras();
		/*
		 * If you have content in your rss feed, you can show it directly.
		 * Otherwise, load the content from link
		 * 
		 */
		
		String postContent = bundle.getString("content");
		//webview bug, need to convert this special character
		postContent = postContent.replace("%", "%25");
		postContent = postContent.replace("'", "%27");
		postContent = postContent.replace("#", "%23");
		webView.loadData(postContent, "text/html; charset=utf-8", "utf-8");
		
		
		//webView.loadUrl(bundle.getString("link")); //for website don't provide content in rss, using this to load the website link
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
        // TODO Auto-generated method stub
        switch (id) {
            case R.id.action_about:
				try {
					PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
					Toast.makeText(this, "Version: " + pInfo.versionName, Toast.LENGTH_LONG).show();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(this, "Version: 2.9x", Toast.LENGTH_LONG).show();
				}
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
	}
}
