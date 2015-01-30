package com.jms.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PostViewActivity extends Activity {
	
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.postview);
		Bundle bundle = this.getIntent().getExtras();
		/*
		 * If you have content in your rss feed, you can show it directly.
		 * Otherwise, load the content from link
		 * 
		 */
		/*
		String postContent = bundle.getString("content");
		//webview bug, need to convert this special character
		postContent = postContent.replace("%", "%25");
		postContent = postContent.replace("'", "%27");
		postContent = postContent.replace("#", "%23");
		webView.loadData(postContent, "text/html; charset=utf-8", "utf-8");
		*/
		
		webView.loadUrl(bundle.getString("link")); //for website don't provide content in rss, using this to load the website link
	}
}
