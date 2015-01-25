package com.jms.rssreader;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PostViewActivity extends Activity {

	private WebView webView;
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.postview);
		webView = (WebView) this.findViewById(R.id.webview);
		
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

		// google analytics
		adView = new AdView(this, AdSize.SMART_BANNER, "a151cfacdc9a91e");
		LinearLayout adContainer = (LinearLayout) this
				.findViewById(R.id.adsContainerInPostView);
		adContainer.addView(adView);

		AdRequest adRequest = new AdRequest();
		Set<String> keywordsSet = new HashSet<String>();
		keywordsSet.add("game");
		keywordsSet.add("dating");
		keywordsSet.add("money");
		keywordsSet.add("girl");
		adRequest.addKeywords(keywordsSet);
		adView.loadAd(adRequest);
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
			adView.destroy();
		}

		super.onDestroy();
	}
}
