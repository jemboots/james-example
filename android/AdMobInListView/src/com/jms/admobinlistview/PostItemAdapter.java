/**
 * PostItemAdapter.java
 * 
 * Adapter Class which configs and returns the View for ListView
 * 
 */
package com.jms.admobinlistview;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PostItemAdapter extends ArrayAdapter<PostData> {
	private LayoutInflater inflater;
	private PostData[] datas;
	private Activity mainActivity;
	private AdView adView;

	public PostItemAdapter(Context context, int textViewResourceId,
			PostData[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mainActivity = (Activity) context;
		inflater = mainActivity.getLayoutInflater();
		datas = objects;
	}

	static class ViewHolder {
		TextView postTitleView;
		TextView postDateView;
		ImageView postThumbView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (position == 0) {
			if (convertView == null || !(convertView instanceof AdView)) {
				if (adView == null) {
					adView = new AdView(mainActivity, AdSize.SMART_BANNER,
							"a151bd35eeb068d");
					AdRequest adRequest = new AdRequest();
					Set<String> keywordsSet = new HashSet<String>();
					keywordsSet.add("game");
					keywordsSet.add("dating");
					keywordsSet.add("money");
					keywordsSet.add("girl");
					adRequest.addKeywords(keywordsSet);
					adView.loadAd(adRequest);
				}

				convertView = adView;
			}
		} else {
			if (convertView == null || convertView instanceof AdView) {
				convertView = inflater.inflate(R.layout.postitem, null);

				viewHolder = new ViewHolder();
				viewHolder.postThumbView = (ImageView) convertView
						.findViewById(R.id.postThumb);
				viewHolder.postTitleView = (TextView) convertView
						.findViewById(R.id.postTitleLabel);
				viewHolder.postDateView = (TextView) convertView
						.findViewById(R.id.postDateLabel);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (datas[position].postThumbUrl == null) {
				viewHolder.postThumbView
						.setImageResource(R.drawable.postthumb_loading);
			}

			viewHolder.postTitleView.setText(datas[position].postTitle);
			viewHolder.postDateView.setText(datas[position].postDate);
		}

		return convertView;
	}
}
